package serverClasses;

import creators.DataBase;

import java.net.DatagramSocket;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerReceiverPool extends Thread{
    private DatagramSocket socket;
    private ServerSenderPool serverSender;
    private Server server;
    private DataBase dataBase;

    private ThreadPoolExecutor executor;


    public ServerReceiverPool(DatagramSocket socket, ServerSenderPool serverSender, Server server, DataBase dataBase) {
        this.socket = socket;
        this.serverSender = serverSender;
        this.server = server;
        this.dataBase = dataBase;
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
    }


    @Override
    public void run() {
        ServerReceiverTask serverReceiver = new ServerReceiverTask(socket, serverSender, server, dataBase);
        executor.execute(serverReceiver);
    }
}
