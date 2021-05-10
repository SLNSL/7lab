package clientClasses;

import askers.ClientDataAsker;
import askers.FieldAsker;
import checkers.ClientDataChecker;
import checkers.FieldChecker;
import clientInterfaces.AbstractClientReceiver;
import clientInterfaces.ClientSenderInterface;
import commandManager.ClientCommandManager;
import commandManager.CommandManager;

import frames.*;
import messenger.Messenger;
import printer.Printable;
import printer.Printer;
import scanners.MyScanner;


import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;


public class Client {
    private SocketAddress address;
    private DatagramChannel datagramChannel;


    private Messenger messenger;
    private String login = "guest";
    private String password = "guest";

    private ClientAccount clientAuthorization;

    private ByteBuffer buf = ByteBuffer.allocate(8192);

    private ClientSenderInterface clientSender;
    private MyScanner scanner;
    private Printable printer;
    private AbstractClientReceiver clientReceiver;
    private ClientDataChecker clientDataChecker;

    private MainFrame mainFrame;


    public static void main(String[] args) {
        disableAccessWarnings();


        Client client = new Client();

        client.create();
    }


    public void create() {
        this.clientAuthorization = new ClientAccount(this);
        this.scanner = new MyScanner();
        mainFrame = new MainFrame(scanner, this);
        this.printer = new Printer(mainFrame);
        this.clientSender = new ClientSender(address, datagramChannel, printer, messenger);
        this.clientReceiver = new ClientReceiver(datagramChannel, address, printer, messenger, this);


        clientAuthorization.setLanguage(clientReceiver, clientSender, printer);


    }

    public void run() {

        this.messenger = clientAuthorization.getMessenger();
        clientReceiver.setMessenger(messenger);
        clientSender.setMessenger(messenger);

        if (messenger != null) {
            clientDataChecker = new FieldChecker(messenger);
            ClientDataAsker clientDataAsker = new FieldAsker(scanner, printer, messenger);

            CommandManager commandInterpreter = new ClientCommandManager(messenger, clientDataChecker, clientDataAsker, printer);
            ClientCommandReader clientReader = new ClientCommandReader(this, clientSender, clientReceiver, commandInterpreter, printer, scanner, clientDataAsker, messenger);
            clientDataAsker.setScanner(scanner);
            mainFrame.run(getLogin(), messenger);

            clientReceiver.start();
            clientReader.start();

        }
    }


    public Client() {
        try {
            this.address = new InetSocketAddress("localhost", 1320);
            this.datagramChannel = DatagramChannel.open();


        } catch (SocketException e) {
            printer.printlnError(e.getMessage());
        } catch (IOException e) {
            printer.printlnError(e.getMessage());
        }


    }



    public void setLogin(String login) {
        clientAuthorization.setLogin(login);
    }

    public String getLogin(){
        return clientAuthorization.getLogin();
    }

    public Messenger getMessenger(){
        return messenger;
    }

    public ClientSenderInterface getClientSender() {
        return clientSender;
    }

    public ClientDataChecker getClientDataChecker() {
        return clientDataChecker;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public synchronized  AbstractClientReceiver getClientReceiver() {
        return clientReceiver;
    }

    public void setMessenger(Messenger messenger){
        this.messenger = messenger;
        this.clientAuthorization.setMessenger(messenger);
    }

    @SuppressWarnings("unchecked")
    public static void disableAccessWarnings() {
        try {
            Class unsafeClass = Class.forName("sun.misc.Unsafe");
            Field field = unsafeClass.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            Object unsafe = field.get(null);

            Method putObjectVolatile = unsafeClass.getDeclaredMethod("putObjectVolatile", Object.class, long.class, Object.class);
            Method staticFieldOffset = unsafeClass.getDeclaredMethod("staticFieldOffset", Field.class);

            Class loggerClass = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field loggerField = loggerClass.getDeclaredField("logger");
            Long offset = (Long) staticFieldOffset.invoke(unsafe, loggerField);
            putObjectVolatile.invoke(unsafe, loggerClass, offset, null);
        } catch (Exception ignored) {
        }
    }

}