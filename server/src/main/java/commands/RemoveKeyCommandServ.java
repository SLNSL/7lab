package commands;

import creators.DataBase;
import exception.IncorrectNumberOfArgumentsException;
import messenger.Messenger;
import checker.ServerDataChecker;
import pattern.Collection;
import wrappers.FieldResult;
import wrappers.Result;

public class RemoveKeyCommandServ implements Command {
    private ServerDataChecker fieldsChecker;
    private Collection collectionManager;
    private Messenger messenger;
    private DataBase dataBase;

    public RemoveKeyCommandServ(Collection collectionManager, ServerDataChecker serverDataChecker, DataBase dataBase) {
        this.fieldsChecker = serverDataChecker;
        this.collectionManager = collectionManager;
        this.dataBase = dataBase;
    }


    @Override
    public Result<Object> execute(int port, String login, Object... args) throws IncorrectNumberOfArgumentsException {
        if (login.equals("guest")) return new FieldResult<>(messenger.youDontHaveRights(),1);
        Integer key = (Integer) args[0];
        collectionManager.setMessenger(messenger);

        Result<Object> result = collectionManager.canBeDeleted(key, login);
        if (result.hasError()) {
            return new FieldResult<>(result.getError());
        } else {
            dataBase.removeProduct(key);
            collectionManager.updateCollection();
            return new FieldResult<>(messenger.commandIsFinished("remove_key"));
        }
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
