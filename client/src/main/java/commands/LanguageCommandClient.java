package commands;


import messenger.Messenger;
import wrappers.CommandPacket;
import wrappers.Packet;

public class LanguageCommandClient implements ClientCommands {
    @Override
    public Packet make(String login, Messenger messenger, String message) {
        Packet packet = new CommandPacket("set_language", message);
        packet.setUser(login, messenger);
        return packet;
    }
}
