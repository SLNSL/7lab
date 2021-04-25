package commands;

import messenger.Messenger;
import wrappers.CommandPacket;
import wrappers.Packet;

public class ShowCommandClient implements ClientCommands{
    private Messenger messenger;

    public ShowCommandClient(Messenger messenger){
        this.messenger =messenger;
    }


    @Override
    public Packet make(String login, Messenger messenger, String message) {
        if (!message.isEmpty()){
            Packet packet = new CommandPacket(messenger.generateIncorrectNumberOfArgumentsMessage("show", 0));
            return packet;
        }
        Packet packet = new CommandPacket("show",  null);
        packet.setUser(login, messenger);
        return packet;
    }

}
