package commands;


import creators.DataBase;
import data.*;
import exception.IncorrectNumberOfArgumentsException;

import messenger.Messenger;

import checker.ServerDataChecker;
import pattern.Collection;


import wrappers.FieldResult;
import wrappers.Result;

/**
 * Команда которая заменяет элемент по полю id на ведённый
 */
public class UpdateCommandServ implements Command {

    private Collection collectionManager;
    private Messenger messenger;
    private ServerDataChecker fieldsChecker;
    private DataBase dataBase;

    public UpdateCommandServ(Collection collectionManager, ServerDataChecker fieldsChecker, DataBase dataBase) {
        this.collectionManager = collectionManager;
        this.fieldsChecker = fieldsChecker;
        this.dataBase = dataBase;
    }

    /**
     * Выполнение команды
     *
     * @return - true - если команда работала без ошибок, false - если команда работала с ошибками
     * @throws IncorrectNumberOfArgumentsException - было передано недопустимое количество аргументов
     */
    @Override
    public Result<String> execute(int port, String login, Object... args) throws IncorrectNumberOfArgumentsException {
        if (login.equals("guest")) return new FieldResult<>(messenger.youDontHaveRights());

        long id = (Long) args[0];
        Product product = (Product) args[1];

        fieldsChecker.setMessenger(messenger);
        collectionManager.setMessenger(messenger);

        Result<Integer> oldProductKeyResult = collectionManager.getKeyById(id);
        if (oldProductKeyResult.hasError()) {
            return new FieldResult<>(oldProductKeyResult.getError());
        }
        Integer oldKey = oldProductKeyResult.getResult();
        if (!collectionManager.get(oldKey).getResult().getUserName().equals(login)){
            return new FieldResult<>(messenger.youDontHaveRights());
        }


        fieldsChecker.getMapOfId().remove(id);
        fieldsChecker.getMapOfPassportId().remove(collectionManager.get(oldProductKeyResult.getResult()).getResult().getOwner().getPassportID());


        Result<Object> passportIDResult = fieldsChecker.checkOwnerPassportId(product.getOwner().getPassportID().toString(), true);
        if (passportIDResult.hasError()) {
            return new FieldResult<>(passportIDResult.getError());
        }


        if (collectionManager.get(oldProductKeyResult.getResult()).getResult().getOwner() != null)
            fieldsChecker.getMapOfPassportId().put(collectionManager.get(oldProductKeyResult.getResult()).getResult().getOwner().getPassportID(), false);
        product.setId(id);
        dataBase.updateProduct(oldProductKeyResult.getResult(), product);
        collectionManager.updateCollection();
        return new FieldResult<>(messenger.commandIsFinished("update") + "\n");

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
