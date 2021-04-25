//package commands;
//
//import exception.IncorrectNumberOfArgumentsException;
//import messenger.Messenger;
//import wrappers.CommandPacket;
//import wrappers.Packet;
//import wrappers.Result;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.net.DatagramPacket;
//
//public class AuthorizationCommandServ implements Command{
//    @Override
//    public Result<String> execute(int port, Object... args) throws IncorrectNumberOfArgumentsException {
//
//        DatagramPacket signCommandPacket = serverReceiver.receive();
//
//        try {
//            byte[] buf = signCommandPacket.getData();
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf);
//            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//            Packet commandPacket = (CommandPacket) objectInputStream.readObject();
//            String stringCommand = commandPacket.getCommand();
//
//            Command command = interpreter.getCommand(stringCommand);
//
//            Object[] arguments = commandPacket.getArguments();
//
//            command.execute(port, arguments);
//
//        } catch (IOException | ClassNotFoundException e){
//
//        }
//    }
//
//    @Override
//    public void setMessenger(Messenger messenger) {
//
//    }
//
//    @Override
//    public Messenger getMessenger() {
//        return null;
//    }
//}
