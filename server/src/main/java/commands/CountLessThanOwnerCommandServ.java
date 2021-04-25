package commands;

import data.Person;
import exception.IncorrectNumberOfArgumentsException;
import messenger.Messenger;
import pattern.Collection;
import wrappers.FieldResult;
import wrappers.Result;

public class CountLessThanOwnerCommandServ implements Command {

    private Collection collectionManager;
    private Messenger messenger;


    public CountLessThanOwnerCommandServ(Collection collectionManager) {
        this.collectionManager = collectionManager;
    }

    @Override
    public Result<String> execute(int port, String login, Object... args) throws IncorrectNumberOfArgumentsException {
        if (login.equals("guest")) return new FieldResult<>(messenger.youDontHaveRights());

        collectionManager.setMessenger(messenger);

        Person owner = (Person) args[0];

        long count = collectionManager.countLessThanOwner(owner);

        return new FieldResult<>(messenger.lessThanOwner(count) + "\n" + messenger.commandIsFinished("count_less_than_owner") + "\n");


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
