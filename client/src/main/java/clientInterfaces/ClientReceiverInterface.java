package clientInterfaces;

import messenger.Messenger;
import wrappers.Answer;

public interface ClientReceiverInterface {

    Answer receive();

    void setMessenger(Messenger messenger);
}
