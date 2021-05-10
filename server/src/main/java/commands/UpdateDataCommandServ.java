package commands;

import exception.EmptyCollectionException;
import exception.IncorrectNumberOfArgumentsException;
import messenger.Messenger;
import pattern.Collection;
import pattern.CollectionManager;
import wrappers.FieldResult;
import wrappers.Result;

import java.util.Vector;

public class UpdateDataCommandServ implements Command{
    private Messenger messenger;
    private Collection collectionManager;

    public UpdateDataCommandServ(Collection collectionManager){
        this.collectionManager = collectionManager;
    }



    @Override
    public Result<Object> execute(int port, String login, Object... args) throws IncorrectNumberOfArgumentsException {
        Vector<Vector<String>> data = collectionManager.updateRequest();
        return new FieldResult<>(data);

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
