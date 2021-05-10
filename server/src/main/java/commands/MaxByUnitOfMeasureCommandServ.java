package commands;

import data.Product;
import exception.IncorrectNumberOfArgumentsException;
import messenger.Messenger;
import pattern.Collection;
import wrappers.FieldResult;
import wrappers.Result;

public class MaxByUnitOfMeasureCommandServ implements Command {
    private Collection collectionManager;
    private Messenger messenger;


    public MaxByUnitOfMeasureCommandServ(Collection collectionManager) {
        this.collectionManager = collectionManager;
    }


    @Override
    public Result<Object> execute(int port, String login,  Object... args) throws IncorrectNumberOfArgumentsException {
        if (login.equals("guest")) return new FieldResult<>(messenger.youDontHaveRights(),1);
        collectionManager.setMessenger(messenger);

        Result<Product> maxProductResult = collectionManager.maxByUnitOfMeasure();
        if (maxProductResult.hasError()) {
            return new FieldResult<>(maxProductResult.getError());
        }


        Product maxProduct = maxProductResult.getResult();


        Result<Integer> keyResult = collectionManager.getKeyById(maxProduct.getId());
        if (keyResult.hasError()) {
            return new FieldResult<>(keyResult.getError() + "\n");
        } else {
            String elementInfo = messenger.getFieldsInfo(keyResult.getResult(), maxProduct);
            return new FieldResult<>(elementInfo + "\n" + messenger.commandIsFinished("max_by_unit_of_measure") + "\n");
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
