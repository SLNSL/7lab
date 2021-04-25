package serverClasses;

import wrappers.Answer;

import java.net.InetAddress;

public interface ServerSenderInterface {

    void partialSend(Answer fullAnswer, InetAddress address, int port);


}
