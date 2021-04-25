package commands;

import creators.DataBase;
import data.Product;
import exception.IncorrectNumberOfArgumentsException;
import messenger.Messenger;
import checker.ServerDataChecker;
import pattern.Collection;
import wrappers.FieldResult;
import wrappers.Result;

import javax.xml.crypto.Data;

public class ReplaceIfLowerCommandServ implements Command {
    private Collection collectionManager;
    private Messenger messenger;
    private ServerDataChecker fieldsChecker;
    private DataBase dataBase;

    public ReplaceIfLowerCommandServ(Collection collectionManager, ServerDataChecker serverDataChecker, DataBase dataBase) {
        this.collectionManager = collectionManager;
        this.fieldsChecker = serverDataChecker;
        this.dataBase = dataBase;
    }


    @Override
    public Result<String> execute(int port, String login, Object... args) throws IncorrectNumberOfArgumentsException {
        if (login.equals("guest")) return new FieldResult<>(messenger.youDontHaveRights());
        Integer key = (Integer) args[0];
        Product product = (Product) args[1];

        if (collectionManager.canBeDeleted(key, login).hasError()){
            return new FieldResult<>(collectionManager.canBeDeleted(key, login).getError());
        }

        boolean replaceOrNot = dataBase.replaceIfLower(key, product);

        String result = messenger.elementReplaced(replaceOrNot);

        collectionManager.updateCollection();
        return new FieldResult<>(result + "\n" + messenger.commandIsFinished("remove_if_lower"));

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
