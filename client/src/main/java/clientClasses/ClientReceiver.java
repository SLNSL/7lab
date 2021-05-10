package clientClasses;

import clientInterfaces.AbstractClientReceiver;
import messenger.Messenger;
import messenger.MessengerEng;
import printer.Printable;
import wrappers.*;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;
import java.util.Vector;

public class ClientReceiver extends AbstractClientReceiver {

    private ByteBuffer buf = ByteBuffer.allocate(8192);
    private DatagramChannel datagramChannel;
    private SocketAddress socketAddress;
    private Printable printer;
    private Messenger messenger = new MessengerEng();

    private Client client;

    private boolean isItFirstMessage = true;

    public ClientReceiver(DatagramChannel datagramChannel, SocketAddress socketAddress, Printable printer, Messenger messenger, Client client) {
        this.datagramChannel = datagramChannel;
        this.socketAddress = socketAddress;
        this.printer = printer;
        this.messenger = messenger;
        this.client = client;
    }


    @Override
    public void run() {
        try {
            datagramChannel.connect(socketAddress);
        } catch (IOException e) {
            printer.printlnError(messenger.generateUnexpectedErrorMessage());
        }

        while (!isInterrupted()) {
            receive();
        }
    }


    public Answer receive() {
        try {
            buf.clear();


            datagramChannel.receive(buf);
            buf.flip();


            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            Answer answer = (Answer) objectInputStream.readObject();

            switch (answer.getAnswerType()){
                case AUTHORIZATION -> {
                    if (!answer.hasError()) {
                        String[] answerMessage = answer.getResult().toString().split(" ", 3);
                        client.setLogin(answerMessage[1]);
                        JOptionPane.showMessageDialog(null, answerMessage[0] + " " + answerMessage[1] + "!", "", JOptionPane.PLAIN_MESSAGE);
                        client.getMainFrame().setLogin(messenger.currentUserWord() + client.getLogin());
                    } else {
                        JOptionPane.showMessageDialog(null, answer.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                case DATA -> {
                    Vector<Vector<String>> data = (Vector<Vector<String>> ) answer.getObject();
                    client.getMainFrame().updateRequest(data);

                }
                case DEFAULT -> {
                    if (answer.hasError()) {
                        if (answer.getErrorType() != 2) {
                            JOptionPane.showMessageDialog(null, answer.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                        }
                        if (answer.getErrorType() == 2) {
                            JOptionPane.showMessageDialog(null, answer.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        }
                    } else {
                        if (answer.getResult() instanceof String) {
                            printer.println(answer.getResult().toString());
                        }
                    }
                }
            }


//            if (answer.getAnswerType().equals(AnswerType.AUTHORIZATION)) {
//                if (!answer.hasError()) {
//                    String[] answerMessage = answer.getResult().toString().split(" ", 3);
//                    client.setLogin(answerMessage[1]);
//                    JOptionPane.showMessageDialog(null, answerMessage[0] + " " + answerMessage[1] + "!", "", JOptionPane.PLAIN_MESSAGE);
//                } else {
//                    JOptionPane.showMessageDialog(null, answer.getError(), "", JOptionPane.INFORMATION_MESSAGE);
//                }
//
//            } else {
//
//                if (answer.hasError()) {
//                    if (answer.getErrorType() != 2) {
//                        JOptionPane.showMessageDialog(null, answer.getError(), "", JOptionPane.INFORMATION_MESSAGE);
//                    }
//                    if (answer.getErrorType() == 2) {
//                        JOptionPane.showMessageDialog(null, answer.getError(), "", JOptionPane.INFORMATION_MESSAGE);
//                        System.exit(0);
//                    }
//                } else {
//                    if (answer.getResult() instanceof String) {
//                        printer.println(answer.getResult().toString());
//                    }
//                }
//
//            }
            byteArrayInputStream.close();
            objectInputStream.close();

            buf.clear();


            datagramChannel.disconnect();

            return answer;
        } catch (PortUnreachableException | IllegalStateException e) {
            printer.printlnError(messenger.generateServerUnavailable());
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            printer.printlnError(messenger.generateUnexpectedErrorMessage());
            e.printStackTrace();
        }
        return null;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
    }
}