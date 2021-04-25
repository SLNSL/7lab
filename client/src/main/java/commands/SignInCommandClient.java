package commands;

import askers.ClientDataAsker;
import messenger.Messenger;
import wrappers.CommandPacket;
import wrappers.Packet;

public class SignInCommandClient implements ClientCommands{
    private ClientDataAsker clientDataAsker;


    public SignInCommandClient( ClientDataAsker clientDataAsker){
        this.clientDataAsker = clientDataAsker;
    }


    @Override
    public Packet make(String login, Messenger messenger, String message) {
        if (!message.isEmpty()){
            Packet packet = new CommandPacket(messenger.generateIncorrectNumberOfArgumentsMessage("sign_in", 0));
            return packet;
        }
        login = clientDataAsker.askLogin();
        String password = clientDataAsker.askPassword();


        Packet packet = new CommandPacket("sign_in",  login, password);
        packet.setUser(login, messenger);


        return packet;
    }
}
