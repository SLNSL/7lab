package serverClasses;

import creators.DataBase;
import wrappers.Packet;

import java.net.DatagramPacket;

public interface ServerReceiverInterface {

    DatagramPacket receive();

    void setMessengerToClient(int port, Boolean b);

    DataBase getDataBase();

    Packet getThisPacket();

    DatagramPacket getThisDatagramPacket();
}
