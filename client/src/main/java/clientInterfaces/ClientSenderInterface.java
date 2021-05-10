package clientInterfaces;

import messenger.Messenger;
import wrappers.Packet;

public interface ClientSenderInterface {

    void send(Packet commandPacket);

    void setMessenger(Messenger messenger);
}
