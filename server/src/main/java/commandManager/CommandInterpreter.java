package commandManager;


import exception.IncorrectNumberOfArgumentsException;
import commands.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import serverClasses.ServerReceiverInterface;
import serverClasses.ServerSenderPool;
import wrappers.*;

import messenger.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;

public class CommandInterpreter extends AbstractInterpreter {
    public static final Logger logger = LoggerFactory.getLogger("serverInterfaces.Server");

    private Manager commandManager;
    private ServerSenderPool serverSender;
    private ServerReceiverInterface serverReceiver;

    private DatagramPacket datagramPacket;
    private Messenger messenger;
    private boolean hasNewPacket = false;

    public CommandInterpreter(Manager commandManager, ServerSenderPool serverSender, ServerReceiverInterface serverReceiver){
        this.commandManager = commandManager;
        this.serverSender = serverSender;
        this.serverReceiver = serverReceiver;
    }

    @Override
    public void run() {

        while (!isInterrupted()){

            if (hasNewPacket){
                hasNewPacket = false;
                treat(datagramPacket);
            }

        }
    }

    public void putCommand(DatagramPacket datagramPacket, Messenger messenger, Manager commandManager, ServerReceiverInterface serverReceiver){
        this.datagramPacket = datagramPacket;
        this.messenger = messenger;
        this.serverReceiver = serverReceiver;
        this.commandManager = commandManager;
        this.hasNewPacket = true;
    }



    public Answer treat(DatagramPacket datagramPacket) {


        Answer answer = new AnswerPacket();
        try {
            byte[] buf = datagramPacket.getData();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Packet commandPacket = (CommandPacket) objectInputStream.readObject();

            String login = commandPacket.getLogin();
            Messenger messenger = commandPacket.getMessenger();

            String stringCommand = commandPacket.getCommand();


            Command command = commandManager.getCommand(stringCommand);


            if (messenger != null) command.setMessenger(messenger);

            Object[] args = commandPacket.getArguments();

            Object result;


            try {
                Result<Object> executeResult = command.execute(datagramPacket.getPort(), login, args);

                if (executeResult.hasError() && executeResult.getErrorType() == 2 && !stringCommand.equals("sign_in") && !stringCommand.equals("sign_out")) {
                    answer = new AnswerPacket(executeResult.getError(), 2);
                    sendAnswer(answer);
                } else {
                    result = executeResult.getResult();

                    switch (stringCommand){
                        case "exit":
                            answer = new AnswerPacket(result.toString(), 2);
                            sendAnswer(answer);
                            break;
                        case "sign_in":
                        case "sign_up":
                            if (!executeResult.hasError()) answer = new AnswerPacket(result); else answer = new AnswerPacket(executeResult.getError(), 1);
                            answer.setAnswerType(AnswerType.AUTHORIZATION);
                            sendAnswer(answer);
                            break;
                        case "update_data":
                            answer = new AnswerPacket("data_request");
                            answer.setObject(result);
                            answer.setAnswerType(AnswerType.DATA);
                            sendAnswer(answer);
                            break;
                        default:
                            if (!executeResult.hasError()) answer = new AnswerPacket(result); else answer = new AnswerPacket(executeResult.getError(), executeResult.getErrorType());
                            if (stringCommand.equals("set_language")) answer.setObject(Translator.getAvailableLanguage(args[0].toString()));
                            sendAnswer(answer);
                            break;
                    }


                }


            } catch (IncorrectNumberOfArgumentsException e) {
                answer = new AnswerPacket(e.getMessage(), 1);
                sendAnswer(answer);
            }
        } catch (IOException | ClassCastException | ClassNotFoundException e) {
            logger.error("Произошла ошибка ", e);
        }
        return null;
    }

    public void sendAnswer(Answer answer){
        if (answer.hasError() && answer.getErrorType() == 2)
            serverReceiver.setMessengerToClient(serverReceiver.getThisDatagramPacket().getPort(), null);
//        answer.setObject(serverReceiver.getDataBase().getUserMessenger(serverReceiver.getThisPacket().getLogin()));
        serverSender.send(answer, datagramPacket.getAddress(), datagramPacket.getPort());
    }

    public void setCommandManager(Manager commandManager){
        this.commandManager = commandManager;
    }

    public void setReceiver(ServerReceiverInterface serverReceiver){
        this.serverReceiver = serverReceiver;
    }

}
