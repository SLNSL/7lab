package serverClasses;

import commandManager.*;
import creators.DataBase;

import org.slf4j.Logger;

import wrappers.*;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.LinkedHashMap;
import java.util.Map;


public class ServerReceiverTask extends ServerReceiverAbstract {

    public static final Logger logger = Server.logger;

    private byte[] receiveBuf = new byte[8192];
    private DatagramSocket socket;
    private ServerSenderPool serverSender;
    private Server server;

    private DatagramPacket previousDatagramPacket;
    private DatagramPacket thisDatagramPacket;
    private Packet thisPacket;

    private DataBase dataBase;


    private Map<Integer, Boolean> connectedClients = new LinkedHashMap<>();

    public ServerReceiverTask(DatagramSocket socket, ServerSenderPool serverSender, Server server, DataBase dataBase) {
        this.socket = socket;
        this.serverSender = serverSender;
        this.server = server;
        this.dataBase = dataBase;

    }

    @Override
    public void run() {
        Manager commandManager = new CommandManager(this,  dataBase, server.getCollectionManager(), server.getFieldChecker());
        AbstractInterpreter interpreter = new CommandInterpreter(commandManager, serverSender, this);
        interpreter.start();
        while (!Thread.currentThread().isInterrupted()) {

            if (thisPacket!= null && connectedClients.get(thisDatagramPacket.getPort()) != null && connectedClients.get(thisDatagramPacket.getPort())==true) {
                connectedClients.put(thisDatagramPacket.getPort(), false);
                logger.info("Получено новое подключение: " + thisDatagramPacket.getAddress() + ":" + thisDatagramPacket.getPort());
            }


            DatagramPacket datagramPacket = receive();

            interpreter.putCommand(datagramPacket, dataBase.getUserMessenger(thisPacket.getLogin()), commandManager, this);


        }

    }

    public DatagramPacket receive() {
        try {
            receiveBuf = new byte[8192];

            DatagramPacket packet
                    = new DatagramPacket(receiveBuf, receiveBuf.length);

            socket.receive(packet);
            this.previousDatagramPacket = thisDatagramPacket;
            this.thisDatagramPacket = packet;

            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(receiveBuf);
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            Packet receivedPacket = (CommandPacket) objectInputStream.readObject();
            this.thisPacket = receivedPacket;
            String command = receivedPacket.getCommand();
            Object[] arguments = receivedPacket.getArguments() == null ? new String[0] : receivedPacket.getArguments();
            String argumentsString = "";
            for (Object obj : arguments) {
                argumentsString += obj.toString() + " ";
            }

            logger.info("Получен пакет от " + packet.getAddress() + ":" + packet.getPort() + " {" + command + " " + argumentsString + "}");
            byteArrayInputStream.close();
            objectInputStream.close();

            return packet;
        } catch (IOException | ClassNotFoundException e) {
            logger.error("Произошла ошибка ", e);
        }
        return null;
    }


    public void setMessengerToClient(int port, Boolean b) {
        connectedClients.put(port, b);
    }

    public DatagramPacket getThisDatagramPacket() {
        return thisDatagramPacket;
    }

    public void setThisDatagramPacket(DatagramPacket thisDatagramPacket) {
        this.thisDatagramPacket = thisDatagramPacket;
    }

    public Packet getThisPacket() {
        return thisPacket;
    }

    public void setThisPacket(Packet thisPacket) {
        this.thisPacket = thisPacket;
    }

    public DataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(DataBase dataBase) {
        this.dataBase = dataBase;
    }
}
