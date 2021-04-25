package clientClasses;

import clientInterfaces.ClientReceiverInterface;
import clientInterfaces.ClientSenderInterface;
import commands.ClientCommands;
import commands.LanguageCommandClient;
import commands.SignInCommandClient;
import commands.SignUpCommandClient;
import messenger.Messenger;
import printer.Printable;
import wrappers.Answer;
import wrappers.FieldResult;
import wrappers.Packet;
import wrappers.Result;

import java.util.Scanner;

public class ClientAccount {
    private Messenger messenger;
    private String login = "guest";

    public Result<Messenger> setLanguage(ClientReceiverInterface clientReceiver, ClientSenderInterface clientSenderInterface, Printable printer){
        Result<Messenger> result = new FieldResult<>();

        Scanner scanner = new Scanner(System.in);

        printer.println("Введите язык / Enter the language [rus; eng]");
        String language = scanner.nextLine();
        String message = language;

        ClientCommands languageCommand = new LanguageCommandClient();
        Packet packet = languageCommand.make(login, messenger, message);
        clientSenderInterface.send(packet);


        Answer answer = clientReceiver.receive();

        if (answer.hasError()){
            result.setError(answer.getError());
            return result;
        } else {
            this.messenger = (Messenger) answer.getObject();
            printer.println(messenger.typeHelp());
        }
        result.setResult(messenger);
        return result;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public String getLogin(){ return login; }

    public void setLogin(String login) {
        this.login = login;
    }

}
