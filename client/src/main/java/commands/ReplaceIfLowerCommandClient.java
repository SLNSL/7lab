package commands;

import askers.ClientDataAsker;
import checkers.ClientDataChecker;
import creators.ClientDataCreator;
import creators.ObjectCreator;
import data.Product;
import messenger.Messenger;
import printer.Printable;
import wrappers.CommandPacket;
import wrappers.Packet;
import wrappers.Result;

public class ReplaceIfLowerCommandClient implements ClientCommands {
    private Messenger messenger;
    private ClientDataChecker clientDataChecker;
    private ClientDataAsker clientDataAsker;
    private Printable printer;

    public ReplaceIfLowerCommandClient(Messenger messenger, ClientDataChecker clientDataChecker, ClientDataAsker clientDataAsker, Printable printer){
        this.messenger = messenger;
        this.clientDataChecker = clientDataChecker;
        this.clientDataAsker = clientDataAsker;
        this.printer = printer;
    }

    @Override
    public Packet make(String login, Messenger messenger, String message) {
        if (!message.isEmpty()){
            Packet packet = new CommandPacket(messenger.generateIncorrectNumberOfArgumentsMessage("replace_if_lower", 0));
            return packet;
        }
        Integer key;
        Result<Object> keyResult = clientDataChecker.checkKey(clientDataAsker.askKey());
        if (keyResult.hasError()){
            Packet packet = new CommandPacket(keyResult.getError());
            return packet;
        }
        key = (Integer) keyResult.getResult();


        ClientDataCreator clientDataCreator = new ObjectCreator();
        Product product = new Product();
        Result<Object> productResult = clientDataCreator.createProduct(true, clientDataChecker, clientDataAsker, printer);
        if (productResult.hasError()){
            Packet packet = new CommandPacket(productResult.getError());
            return packet;
        }
        product = (Product) productResult.getResult();

        Packet packet = new CommandPacket("replace_if_lower", key, product);
        packet.setUser(login, messenger);
        return packet;


    }
}
