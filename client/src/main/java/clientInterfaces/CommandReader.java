package clientInterfaces;

import clientClasses.ClientAccount;
import messenger.Messenger;

public interface CommandReader {

    void read(ClientAccount clientAccount);
}
