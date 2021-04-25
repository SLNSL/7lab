package commands;

import messenger.Messenger;
import wrappers.Packet;

public interface ClientCommands {
    Packet make(String login, Messenger messenger, String message);
}
