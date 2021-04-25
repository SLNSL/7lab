package commands;

import messenger.Messenger;
import wrappers.CommandPacket;
import wrappers.Packet;

public class HelpCommandClient implements ClientCommands{
    private Messenger messenger;

    public HelpCommandClient(Messenger messenger){
        this.messenger = messenger;
    }
    @Override
    public Packet make(String login, Messenger messenger, String message) {
        if (!message.isEmpty()){
            Packet packet = new CommandPacket(messenger.generateIncorrectNumberOfArgumentsMessage("help", 0));
            return packet;
        }
        Packet packet = new CommandPacket("help", null);
        packet.setUser(login, messenger);
        return packet;
    }
}
