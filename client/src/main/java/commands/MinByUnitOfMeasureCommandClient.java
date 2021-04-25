package commands;

import messenger.Messenger;
import wrappers.CommandPacket;
import wrappers.Packet;

public class MinByUnitOfMeasureCommandClient implements ClientCommands{
    private Messenger messenger;


    public MinByUnitOfMeasureCommandClient(Messenger messenger){
        this.messenger = messenger;
    }


    @Override
    public Packet make(String login, Messenger messenger, String message) {
        if (!message.isEmpty()){
            Packet packet = new CommandPacket(messenger.generateIncorrectNumberOfArgumentsMessage("min_by_unit_of_measure", 0));
            return packet;
        }
        Packet packet = new CommandPacket("min_by_unit_of_measure", null);
        packet.setUser(login, messenger);
        return packet;
    }

}
