package clientClasses;

import clientInterfaces.ClientReceiverInterface;
import clientInterfaces.ClientSenderInterface;
import commands.ClientCommands;
import commands.LanguageCommandClient;
import frames.AuthorizationFrame;
import frames.LanguageFrame;
import messenger.Messenger;
import printer.Printable;
import wrappers.Answer;
import wrappers.FieldResult;
import wrappers.Packet;
import wrappers.Result;

import javax.swing.*;

public class ClientAccount {
    private Messenger messenger;
    private String login = "guest";

    private ClientReceiverInterface clientReceiver;
    private ClientSenderInterface clientSender;
    private Printable printer;

    private Client client;

    public ClientAccount(Client client) {
        this.client = client;
    }

    public void setLanguage(ClientReceiverInterface clientReceiver, ClientSenderInterface clientSenderInterface, Printable printer) {
        this.clientReceiver = clientReceiver;
        this.clientSender = clientSenderInterface;
        this.printer = printer;
        LanguageFrame frame = new LanguageFrame(this);

    }

    public Result<Messenger> generateClient(String message) {
        Result<Messenger> result = new FieldResult<>();

        ClientCommands languageCommand = new LanguageCommandClient();
        Packet packet = languageCommand.make(login, messenger, message);
        clientSender.send(packet);


        Answer answer = clientReceiver.receive();


        if (answer.hasError()) {
            result.setError(answer.getError(), 2);
            return result;
        } else {
            this.messenger = (Messenger) answer.getObject();
            client.setMessenger((Messenger) answer.getObject());
            JOptionPane.showMessageDialog(null, messenger.languageHasBeenInstalled() + "\n" + messenger.typeHelp(), "", JOptionPane.PLAIN_MESSAGE);
        }
        result.setResult(messenger);
        AuthorizationFrame authorizationFrame = new AuthorizationFrame(client);
        return result;
    }

    public Messenger getMessenger() {
        return messenger;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }
}
