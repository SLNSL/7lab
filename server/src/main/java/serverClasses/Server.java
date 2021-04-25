package serverClasses;


import creators.DataBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import checker.FieldChecker;
import checker.ServerDataChecker;
import pattern.Collection;
import pattern.CollectionManager;
import pattern.FileManager;
import pattern.Loader;
import readers.ServerReader;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.*;

public class Server {
    private DatagramSocket socket;
    private boolean status;
    private byte[] receiveBuf = new byte[2048];
    private byte[] sendBuf = new byte[2048];
    private InetAddress inetAddress;
    private int port;

    private Collection collectionManager;

    private ServerDataChecker fieldChecker;

    private Loader fileLoader;

    public static final Logger logger = LoggerFactory.getLogger(Server.class);

    private DataBase dataBase;


    public static void main(String[] args) {
        disableAccessWarnings();

        Server server = new Server();
        logger.info("Сервер запущен");
        server.run();

    }

    public Server() {
        try {
            this.inetAddress = InetAddress.getByName("localhost");
            this.port = 1320;
            this.socket = new DatagramSocket(port);

            this.dataBase = new DataBase("postgres","1320");

        } catch (SocketException e) {
            logger.error("Произошла ошибка ", e);
        } catch (UnknownHostException e) {
            logger.error("Произошла ошибка ", e);
        }

    }


    public void run() {
//        ServerSenderInterface serverSender = new serverInterfaces.ServerSenderTask(socket);
//        ServerReceiverAbstract serverReceiver = new ServerReceiver(socket, serverSender, this, dataBase);
        ServerSenderPool serverSender = new ServerSenderPool(socket);
        ServerReceiverPool serverReceiverPool = new ServerReceiverPool(socket, serverSender, this, dataBase);


        this.fieldChecker = new FieldChecker();

        this.fileLoader = new FileManager(dataBase);

        this.collectionManager = new CollectionManager(fileLoader, fieldChecker, dataBase);
        collectionManager.load();
        logger.info("Коллекция загружена");


        serverReceiverPool.setDaemon(true);
        serverReceiverPool.start();


        ServerReader serverReader = new ServerReader(collectionManager);
        serverReader.read();


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

    public Collection getCollectionManager() {
        return collectionManager;
    }

    public ServerDataChecker getFieldChecker() {
        return fieldChecker;
    }

    public Loader getFileLoader() {
        return fileLoader;
    }
}
