package clientClasses;

import askers.ClientDataAsker;
import clientInterfaces.CommandExecutor;
import clientInterfaces.CommandReader;
import clientInterfaces.AbstractClientReceiver;
import clientInterfaces.ClientSenderInterface;
import commandManager.CommandManager;
import messenger.Messenger;
import printer.Printable;
import scanners.MyScanner;

import java.util.LinkedHashMap;
import java.util.Scanner;

public class ClientCommandReader extends Thread {
    private ClientSenderInterface clientSender;
    private AbstractClientReceiver clientReceiver;
    private Printable printer;
    private CommandManager commandInterpreter;
    private MyScanner scanner;
    private ClientDataAsker asker;
    private Messenger messenger;
    private Client client;

    public ClientCommandReader(Client client, ClientSenderInterface clientSender, AbstractClientReceiver clientReceiver, CommandManager commandInterpreter,
                               Printable printer, MyScanner scanner, ClientDataAsker asker,
                               Messenger messenger) {
        this.clientSender = clientSender;
        this.clientReceiver = clientReceiver;
        this.commandInterpreter = commandInterpreter;
        this.printer = printer;
        this.scanner = scanner;
        this.asker = asker;
        this.messenger = messenger;
        this.client = client;
    }

    @Override
    public void run() {
        String[] command;
        CommandExecutor clientCommandExecutor =
                new ClientCommandExecutor(clientSender, printer, commandInterpreter,
                        messenger, asker);
        while (true) {
            try {
                clientCommandExecutor.setMapOfScripts(new LinkedHashMap<>());
                asker.setScriptMode(false);
                String fullCommand = scanner.nextLine() + " ";
                printer.println(fullCommand);
                command = fullCommand.split(" ", 2);
                clientCommandExecutor.runCommand(client.getLogin(), client.getMessenger(), command);
                if (command[0].trim().equals("exit") && command[1].isEmpty()) return;


            } catch (SecurityException e) {
                printer.printlnError(messenger.generateSecurityExceptionMessage());
            }
        }
    }
}
