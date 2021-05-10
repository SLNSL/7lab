package commands;

import creators.DataBase;
import exception.IncorrectNumberOfArgumentsException;
import messenger.Messenger;
import pattern.Collection;
import wrappers.FieldResult;
import wrappers.Result;

import javax.xml.crypto.Data;

public class ClearCommandServ implements Command {
    private Collection collectionManager;
    private Messenger messenger;
    private DataBase dataBase;


    public ClearCommandServ(Collection collectionManager, DataBase dataBase) {
        this.collectionManager = collectionManager;
        this.dataBase = dataBase;
    }


    @Override
    public Result<Object> execute(int port, String login, Object... args) throws IncorrectNumberOfArgumentsException {
        if (login.equals("guest")) return new FieldResult<>(messenger.youDontHaveRights(),1);
        collectionManager.setMessenger(messenger);

        for (Integer i : dataBase.getData().keySet()){
            if (!collectionManager.canBeDeleted(i, login).hasError()){
                dataBase.removeProduct(i);
            }
        }
        collectionManager.updateCollection();

        return new FieldResult<>(messenger.commandIsFinished("clear"));
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
