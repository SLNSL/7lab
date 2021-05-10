package commands;

import creators.DataBase;
import data.Product;
import exception.IncorrectNumberOfArgumentsException;
import messenger.Messenger;
import pattern.Collection;
import wrappers.FieldResult;
import wrappers.Result;

import javax.xml.crypto.Data;

public class RemoveLowerCommandServ implements Command {
    private Messenger messenger;
    private Collection collectionManager;
    private DataBase dataBase;

    public RemoveLowerCommandServ(Collection collectionManager, DataBase dataBase) {
        this.collectionManager = collectionManager;
        this.dataBase = dataBase;
    }

    @Override
    public Result<Object> execute(int port, String login,  Object... args) throws IncorrectNumberOfArgumentsException {
        if (login.equals("guest")) return new FieldResult<>(messenger.youDontHaveRights(),1);
        Product product = (Product) args[0];

        collectionManager.setMessenger(messenger);
        dataBase.removeLower(product, login);
        collectionManager.updateCollection();

        return new FieldResult<>(messenger.commandIsFinished("remove_lower"));

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
