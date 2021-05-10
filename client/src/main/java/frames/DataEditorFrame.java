package frames;

import checkers.ClientDataChecker;
import clientClasses.Client;
import com.sun.tools.javac.Main;
import data.Color;
import data.Product;
import data.UnitOfMeasure;
import wrappers.CommandPacket;
import wrappers.FieldResult;
import wrappers.Packet;
import wrappers.Result;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class DataEditorFrame extends JFrame {


    private JLabel label = new JLabel();
    private JTextField textField = new JTextField("");
    private JButton button = new JButton();
    private JButton deleteButton = new JButton();


    private Client client;

    private JFrame frame;
    private MainFrame mainFrame;

    private int row;
    private int col;

    public DataEditorFrame(MainFrame mainFrame, Client client, int row, int col) {
        this.client = client;
        this.frame = this;
        this.col = col;
        this.row = row;
        this.mainFrame = mainFrame;
        label.setText(client.getMessenger().doYouWantToChange());
        button.setText(client.getMessenger().changeWord());
        deleteButton.setText(client.getMessenger().deleteWord());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - 175, dimension.height / 2 - 60, 350, 160);

        Container container = this.getContentPane();
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.gridheight = 1;
        constraints.ipady = 20;
        container.add(label, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 4;
        constraints.gridheight = 1;
        container.add(textField, constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.anchor = GridBagConstraints.PAGE_END;
        button.addActionListener(new ButtonEventListener());
        container.add(button, constraints);

        constraints.gridx = 3;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        deleteButton.addActionListener(new DeleteButtonEventListener());
        container.add(deleteButton,constraints);




    }
    class ButtonEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (client.getLogin().equals("guest")){
                JOptionPane.showMessageDialog(null, client.getMessenger().youDontHaveRights(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            String text = textField.getText();
            frame.dispose();
            ClientDataChecker clientDataChecker = client.getClientDataChecker();

            Result<Object> resultOfCheckCell = new FieldResult<>();


            switch (col) {
                case 0, 1, 2, 6 -> resultOfCheckCell.setError(client.getMessenger().youDontHaveRights(), 1);
                case 3 -> resultOfCheckCell = clientDataChecker.checkName(text);
                case 4 -> resultOfCheckCell = clientDataChecker.checkCoordinatesX(text);
                case 5 -> resultOfCheckCell = clientDataChecker.checkCoordinatesY(text);
                case 7 -> resultOfCheckCell = clientDataChecker.checkPrice(text);
                case 8 -> resultOfCheckCell = clientDataChecker.checkPartNumber(text);
                case 9 -> resultOfCheckCell = clientDataChecker.checkManufactureCost(text);
                case 10 -> resultOfCheckCell = clientDataChecker.checkUnitOfMeasure(text);
                case 11 -> resultOfCheckCell = clientDataChecker.checkOwnerName(text);
                case 12 -> resultOfCheckCell = clientDataChecker.checkOwnerPassportId(text, true);
                case 13 -> resultOfCheckCell = clientDataChecker.checkOwnerHairColor(text);
                case 14 -> resultOfCheckCell = clientDataChecker.checkOwnerLocationX(text);
                case 15 -> resultOfCheckCell = clientDataChecker.checkOwnerLocationY(text);
                case 16 -> resultOfCheckCell = clientDataChecker.checkOwnerLocationZ(text);
                case 17 -> resultOfCheckCell = clientDataChecker.checkOwnerLocationName(text);
            }

            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            Product product = mainFrame.getProduct(row);
            Long id = product.getId();

            switch (col) {
                case 0 -> {
                    Packet packet = new CommandPacket("remove_key", mainFrame.getTable().getModel().getValueAt(row, 0));
                    packet.setUser(client.getLogin(), client.getMessenger());
                    client.getClientSender().send(packet);

                    packet = new CommandPacket("insert", Integer.parseInt(text), product);
                    packet.setUser(client.getLogin(), client.getMessenger());
                    client.getClientSender().send(packet);
                    return;
                }
                case 3 -> product.setName(text);
                case 4 -> product.getCoordinates().setX(Long.parseLong(text));
                case 5 -> product.getCoordinates().setY(Double.parseDouble(text));
                case 7 -> product.setPrice(Double.parseDouble(text));
                case 8 -> product.setPartNumber(text);
                case 9 -> product.setManufactureCost(Double.parseDouble(text));
                case 10 -> product.setUnitOfMeasure(UnitOfMeasure.valueOf(text));
                case 11 -> product.getOwner().setName(text);
                case 12 -> product.getOwner().setPassportID(text);
                case 13 -> product.getOwner().setHairColor(Color.valueOf(text));
                case 14 -> product.getOwner().getLocation().setX(Long.parseLong(text));
                case 15 -> product.getOwner().getLocation().setY(Double.parseDouble(text));
                case 16 -> product.getOwner().getLocation().setZ(Float.parseFloat(text));
                case 17 -> product.getOwner().getLocation().setName(text);

            }




            Packet packet = new CommandPacket("update", id, product);
            packet.setUser(client.getLogin(), client.getMessenger());
            client.getClientSender().send(packet);


        }
    }
    class DeleteButtonEventListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Packet packet = new CommandPacket("remove_key", mainFrame.getTable().getModel().getValueAt(row, 0));
            System.out.println(row + " " + col);
            packet.setUser(client.getLogin(), client.getMessenger());
            client.getClientSender().send(packet);
        }
    }
}
