package commands;

import creators.DataBase;
import data.Product;
import messenger.Messenger;
import checker.ServerDataChecker;
import pattern.Collection;
import wrappers.FieldResult;
import wrappers.Result;

import javax.xml.crypto.Data;

public class InsertCommandServ implements Command {
    private Collection collectionManager;
    private Messenger messenger;
    private ServerDataChecker fieldsChecker;
    private DataBase dataBase;

    public InsertCommandServ(Collection collectionManager, ServerDataChecker fieldsChecker, DataBase dataBase) {
        this.collectionManager = collectionManager;
        this.fieldsChecker = fieldsChecker;
        this.dataBase = dataBase;
    }


    /**
     * Выполнение команды
     *
     * @return - true - если команда работала без ошибок, false - если команда работала с ошибками
     */
    @Override
    public Result<String> execute(int port, String login, Object... args) {
        if (login.equals("guest")) return new FieldResult<>(messenger.youDontHaveRights());
        Integer key = (Integer) args[0];
        Product product = (Product) args[1];

        fieldsChecker.setMessenger(messenger);
        collectionManager.setMessenger(messenger);

        product.setId(collectionManager.generateID());

        Result<Object> passportIDResult = fieldsChecker.checkOwnerPassportId(product.getOwner().getPassportID().toString(), true);

        if (passportIDResult.hasError()) {
            return new FieldResult<>(passportIDResult.getError());
        }
        dataBase.addProduct(key, product);
        collectionManager.updateCollection();

        return new FieldResult<>(messenger.commandIsFinished("insert"));


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
