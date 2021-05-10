package commands;

import creators.DataBase;
import exception.IncorrectNumberOfArgumentsException;
import messenger.Messenger;
import serverClasses.ServerReceiverInterface;
import wrappers.FieldResult;
import wrappers.Result;


public class SignInCommandServ implements Command{
    private DataBase dataBase;
    private Messenger messenger;
    private ServerReceiverInterface serverReceiver;

    public SignInCommandServ(DataBase dataBase, ServerReceiverInterface serverReceiver){
        this.dataBase = dataBase;
        this.serverReceiver = serverReceiver;
    }

    @Override
    public Result<Object> execute(int port, String login, Object... args) throws IncorrectNumberOfArgumentsException {
        String password = args[1].toString();

        this.messenger = messenger;

        Result<Object> signInResult = dataBase.signInUser(login, password, messenger.getString());
        if (signInResult.hasError()){
            return signInResult;
        }
        serverReceiver.setMessengerToClient(port, true);
        return new FieldResult<>(messenger.sayHello(login + " " + password));

    }

    @Override
    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    @Override
    public Messenger getMessenger() {
        return messenger;
    }
}
