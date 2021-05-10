package frames;

import clientClasses.Client;
import clientClasses.ClientSender;
import clientInterfaces.ClientReceiverInterface;
import clientInterfaces.ClientSenderInterface;
import com.sun.tools.javac.Main;
import wrappers.Answer;
import wrappers.CommandPacket;

import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Handler;

public class AutomaticUpdater extends Thread{
    private MainFrame frame;
    private Client client;

    public AutomaticUpdater(MainFrame mainFrame, Client client){
        this.frame = mainFrame;
        this.client = client;
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            try {
                requestUpdate();
                sleep(3000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    public void requestUpdate(){
        ClientSenderInterface clientSender = client.getClientSender();
        ClientReceiverInterface clientReceiver = client.getClientReceiver();

        CommandPacket commandPacket = new CommandPacket("update_data", null);
        commandPacket.setUser(client.getLogin(), client.getMessenger());


        clientSender.send(commandPacket);

    }
}
