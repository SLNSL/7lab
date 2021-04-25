package clientClasses;

import askers.ClientDataAsker;
import askers.FieldAsker;
import checkers.ClientDataChecker;
import checkers.FieldChecker;
import clientInterfaces.CommandReader;
import clientInterfaces.LanguageClientInterface;
import clientInterfaces.AbstractClientReceiver;
import clientInterfaces.ClientSenderInterface;
import commandManager.ClientCommandManager;
import commandManager.CommandManager;
import messenger.Messenger;
import printer.Printable;
import printer.Printer;


import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class Client {
    private SocketAddress address;
    private DatagramChannel datagramChannel;


    private Messenger messenger;
    private String login = "guest";
    private String password = "guest";

    private ClientAccount clientAuthorization;

    private ByteBuffer buf = ByteBuffer.allocate(8192);


    public static void main(String[] args) {
        disableAccessWarnings();
        Printable printer = new Printer();


        Client client = new Client();

        client.run();
    }


    public void run() {
        Printable printer = new Printer();
        Scanner scanner = new Scanner(System.in);
        ClientSenderInterface clientSender = new ClientSender(address, datagramChannel, printer, messenger);
        AbstractClientReceiver clientReceiver = new ClientReceiver(datagramChannel, address, printer, messenger, this);

        LanguageClientInterface languageClient = new LanguageClient(clientSender, clientReceiver, printer);
        languageClient.setScanner(scanner);


        this.clientAuthorization = new ClientAccount();
        clientAuthorization.setLanguage(clientReceiver, clientSender, printer);

        this.messenger = clientAuthorization.getMessenger();

        if (messenger != null) {

            ClientDataChecker clientDataChecker = new FieldChecker(messenger);
            ClientDataAsker clientDataAsker = new FieldAsker(scanner, printer, messenger);

            CommandManager commandInterpreter = new ClientCommandManager(messenger, clientDataChecker, clientDataAsker, printer);
            CommandReader clientReader = new ClientCommandReader(clientSender, clientReceiver, commandInterpreter, printer, scanner, clientDataAsker, messenger);
            clientDataAsker.setScanner(scanner);

            clientReceiver.start();

            clientReader.read(clientAuthorization);

        }


    }


    public Client() {
        try {
            this.address = new InetSocketAddress("localhost", 1320);
            this.datagramChannel = DatagramChannel.open();


        } catch (SocketException e) {
            System.out.println("CLIENT SOCKET EX");
        } catch (IOException e) {
            System.out.println("CLIENT IO");
        }

    }

    public String sendEcho(String msg) throws SocketException, IOException {
        buf.put(msg.getBytes());
        buf.flip();

        DatagramChannel sendPacket = DatagramChannel.open();
        sendPacket.connect(address);

        sendPacket.send(buf, address);
        buf.clear();

        sendPacket.receive(buf);
        buf.flip();

        Charset latin = StandardCharsets.UTF_8;
        CharBuffer latinBuffer = latin.decode(buf);
        String result = new String(latinBuffer.array());

        String received = result;
        return received;
    }

    public ClientAccount getClientAuthorization() {
        return clientAuthorization;
    }

    public void setLogin(String login){
        clientAuthorization.setLogin(login);
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