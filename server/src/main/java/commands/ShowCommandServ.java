package commands;

import exception.EmptyCollectionException;
import exception.IncorrectNumberOfArgumentsException;
import messenger.Messenger;
import pattern.Collection;
import wrappers.FieldResult;
import wrappers.Result;

public class ShowCommandServ implements Command {
    private Collection collectionManager;
    private Messenger messenger;


    public ShowCommandServ(Collection collectionManager) {
        this.collectionManager = collectionManager;
    }


    @Override
    public Result<Object> execute(int port, String login, Object... args) throws IncorrectNumberOfArgumentsException {
        if (login.equals("guest")) return new FieldResult<>(messenger.youDontHaveRights(),1);
        collectionManager.setMessenger(messenger);

        try {
            String collectionInfo = collectionManager.writeCollection();
            return new FieldResult<>(collectionInfo + "\n" + messenger.commandIsFinished("show") + "\n");
        } catch (EmptyCollectionException e) {
            return new FieldResult<>(e.getMessage() + "\n");
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
