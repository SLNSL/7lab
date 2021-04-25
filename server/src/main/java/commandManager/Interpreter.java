package commandManager;


import serverClasses.ServerReceiverInterface;
import wrappers.Answer;
import messenger.Messenger;

import java.net.DatagramPacket;

public interface Interpreter {
    Answer treat(DatagramPacket datagramPacket);

    void putCommand(DatagramPacket datagramPacket, Messenger messenger, Manager commandManager, ServerReceiverInterface serverReceiver);

    void sendAnswer(Answer answer);

    void setCommandManager(Manager commandManager);

    void setReceiver(ServerReceiverInterface serverReceiver);

}
