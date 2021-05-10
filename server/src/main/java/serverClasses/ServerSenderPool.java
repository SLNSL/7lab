package serverClasses;

import wrappers.Answer;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ServerSenderPool extends Thread{

    private DatagramSocket socket;
    private Answer answer;
    private InetAddress inetAddress;
    private int port;

    private ThreadPoolExecutor executor;

    public ServerSenderPool(DatagramSocket socket) {
        this.socket = socket;
        this.executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(20);
    }

    @Override
    public void run() {
        while(!isInterrupted());
    }


    public void send(Answer answer, InetAddress address, int port){
        ServerSenderTask serverSenderTask = new ServerSenderTask(socket, answer, address, port);
        executor.execute(serverSenderTask);
    }
}
