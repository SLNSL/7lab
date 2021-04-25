package serverClasses;

import org.slf4j.Logger;
import wrappers.Answer;
import wrappers.AnswerPacket;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerSenderTask implements ServerSenderInterface, Runnable {

    public static final Logger logger = Server.logger;

    private final static int MAX_BUFFER_SIZE = 4000;

    private String message;

    private DatagramSocket socket;
    private Answer answer;
    private InetAddress inetAddress;
    private int port;

    public ServerSenderTask(DatagramSocket socket, Answer answer, InetAddress inetAddress, int port) {
        this.socket = socket;
        this.answer = answer;
        this.inetAddress = inetAddress;
        this.port = port;
    }

    @Override
    public void run() {
        send(answer, inetAddress, port);
    }

    public void send(Answer answer, InetAddress inetAddress, int port) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(answer);
            objectOutputStream.flush();

            byte[] sendBuffer = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.flush();

            DatagramPacket sendPacket = new DatagramPacket(sendBuffer, sendBuffer.length, inetAddress, port);


            if (sendBuffer.length > MAX_BUFFER_SIZE * 2) {
                logger.info("Размер отправленного пакета слишком большой. Пакет будет отправлен по частям");
                partialSend(answer, inetAddress, port);
            } else {
                if (answer.hasError()) {
                    logger.info("Отправлен ответ: {" + answer.getError() + "} клиенту " + sendPacket.getAddress() + ":" + sendPacket.getPort());
                } else {
                    logger.info("Отправлен ответ: {" + answer.getResult() + "} клиенту " + sendPacket.getAddress() + ":" + sendPacket.getPort());
                }
                socket.send(sendPacket);

                objectOutputStream.close();
                byteArrayOutputStream.close();
            }

        } catch (IOException e) {
            logger.error("Не удалось отправить ответ. Ошибка ввода/вывода");
        }

    }

    public void partialSend(Answer fullAnswer, InetAddress address, int port) {
        int sizeOfFullPacket = String.valueOf(fullAnswer.getResult()).getBytes().length;

        int countOfSenders = (sizeOfFullPacket / MAX_BUFFER_SIZE + 1) / 2 + 1;
        Answer partAnswer;
        for (int i = 0; i <= countOfSenders; i++) {
            if (i == countOfSenders) {
                partAnswer = new AnswerPacket(String.valueOf(fullAnswer.getResult()).substring(i * MAX_BUFFER_SIZE));
            } else {
                partAnswer = new AnswerPacket(String.valueOf(fullAnswer.getResult()).substring(i * MAX_BUFFER_SIZE, (i + 1) * MAX_BUFFER_SIZE));
            }
            send(partAnswer, address, port);
        }
    }

}
