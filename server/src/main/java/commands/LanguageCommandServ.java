package commands;

import exception.IncorrectNumberOfArgumentsException;
import messenger.Messenger;
import messenger.Translator;
import wrappers.*;

public class LanguageCommandServ implements Command {
    private Messenger messenger;

    public LanguageCommandServ() {

    }

    @Override
    public Result<Object> execute(int port, String login, Object... args) throws IncorrectNumberOfArgumentsException {
        String language = args[0].toString();

        Result<Messenger> messengerResult = new Translator().setLanguage(language.trim());
        if (messengerResult.hasError()) {
            Result<Object> fieldResult = new FieldResult<>();
            fieldResult.setError(messengerResult.getError(), 2);
            return fieldResult;
        }
        this.messenger = messengerResult.getResult();
        return new FieldResult<>();


    }

    @Override
    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }

    public Messenger getMessenger() {
        return messenger;
    }


}
