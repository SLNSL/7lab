package frames;

import checkers.ClientDataChecker;
import clientClasses.Client;
import data.*;
import wrappers.CommandPacket;
import wrappers.FieldResult;
import wrappers.Packet;
import wrappers.Result;

import javax.accessibility.AccessibleContext;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FieldInfoFrame extends JFrame {

    private JLabel keyInfo = new JLabel("key: ");
    private JLabel keyField = new JLabel("5");

    private JLabel userInfo = new JLabel("user: ");
    private JLabel userField = new JLabel("5");

    private JLabel idInfo = new JLabel("id: ");
    private JLabel idField = new JLabel("5");

    private JLabel nameInfo = new JLabel("name: ");
    private JTextField nameField = new JTextField("5");

    private JLabel xCoordInfo = new JLabel("coordinates.x: ");
    private JTextField xCoordField = new JTextField("5");

    private JLabel yCoordInfo = new JLabel("coordinates.y: ");
    private JTextField yCoordField = new JTextField("5");

    private JLabel dateInfo = new JLabel("date: ");
    private JLabel dateField = new JLabel("5");

    private JLabel priceInfo = new JLabel("price: ");
    private JTextField priceField = new JTextField("5");

    private JLabel partNumberInfo = new JLabel("partNumber: ");
    private JTextField partNumberField = new JTextField("5");

    private JLabel manufactureInfo = new JLabel("manufactureCost: ");
    private JTextField manufactureField = new JTextField("5");

    private JLabel unitInfo = new JLabel("unitOfMeasure: ");
    private JTextField unitField = new JTextField("5");

    private JLabel ownerNameInfo = new JLabel("owner.name: ");
    private JTextField ownerNameField = new JTextField("5");

    private JLabel ownerPassportInfo = new JLabel("owner.passportID: ");
    private JTextField ownerPassportField = new JTextField("5");

    private JLabel ownerColorInfo = new JLabel("owner.hairColor: ");
    private JTextField ownerColorField = new JTextField("5");

    private JLabel ownerLocationXInfo = new JLabel("owner.location.x: ");
    private JTextField ownerLocationXField = new JTextField("5");

    private JLabel ownerLocationYInfo = new JLabel("owner.location.y: ");
    private JTextField ownerLocationYField = new JTextField("5");

    private JLabel ownerLocationZInfo = new JLabel("owner.location.z: ");
    private JTextField ownerLocationZField = new JTextField("5");

    private JLabel ownerLocationNameInfo = new JLabel("owner.location.name: ");
    private JTextField ownerLocationNameField = new JTextField("5");

    private JButton button = new JButton();
    private JButton deleteButton = new JButton();

    private Client client;
    private MyDefaultTableModel model;
    private int row;
    private MainFrame mainFrame;
    private Frame frame;


    public FieldInfoFrame(MainFrame mainFrame, Client client, int row){
        button.setText(client.getMessenger().changeWord());
        deleteButton.setText(client.getMessenger().deleteWord());
        this.client = client;
        this.frame = this;
        this.row = row;
        this.mainFrame = mainFrame;
        model = (MyDefaultTableModel) mainFrame.getTable().getModel();
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - 200, dimension.height / 2 - 175, 400, 350);

        Container container = this.getContentPane();
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.ipady = 5;
        constraints.ipadx = 20;
        container.add(keyInfo, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        keyField.setText(model.getValueAt(row, 0).toString());
        container.add(keyField,constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        container.add(userInfo, constraints);

        constraints.gridx = 3;
        constraints.gridy = 0;
        userField.setText(model.getValueAt(row, 1).toString());
        container.add(userField, constraints);


        constraints.gridx = 0;
        constraints.gridy = 1;
        container.add(idInfo,constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        idField.setText(model.getValueAt(row,2).toString());
        container.add(idField,constraints);

        constraints.gridx = 2;
        constraints.gridy = 1;
        container.add(nameInfo, constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        nameField.setText(model.getValueAt(row, 3).toString());
        container.add(nameField, constraints);



        constraints.gridx = 0;
        constraints.gridy = 2;
        container.add(xCoordInfo,constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        xCoordField.setText(model.getValueAt(row,4).toString());
        container.add(xCoordField,constraints);

        constraints.gridx = 2;
        constraints.gridy = 2;
        container.add(yCoordInfo, constraints);

        constraints.gridx = 3;
        constraints.gridy = 2;
        yCoordField.setText(model.getValueAt(row,5).toString());
        container.add(yCoordField, constraints);



        constraints.gridx = 0;
        constraints.gridy = 3;
        container.add(dateInfo,constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        dateField.setText(model.getValueAt(row,6).toString());
        container.add(dateField,constraints);

        constraints.gridx = 2;
        constraints.gridy = 3;
        container.add(priceInfo, constraints);

        constraints.gridx = 3;
        constraints.gridy = 3;
        priceField.setText(model.getValueAt(row,7).toString());
        container.add(priceField, constraints);




        constraints.gridx = 0;
        constraints.gridy = 4;
        container.add(partNumberInfo,constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        partNumberField.setText(model.getValueAt(row,8).toString());
        container.add(partNumberField,constraints);

        constraints.gridx = 2;
        constraints.gridy = 4;
        container.add(manufactureInfo, constraints);

        constraints.gridx = 3;
        constraints.gridy = 4;
        manufactureField.setText(model.getValueAt(row,9).toString());
        container.add(manufactureField, constraints);




        constraints.gridx = 0;
        constraints.gridy = 5;
        container.add(unitInfo,constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        unitField.setText(model.getValueAt(row,10).toString());
        container.add(unitField,constraints);

        constraints.gridx = 2;
        constraints.gridy = 5;
        container.add(ownerNameInfo, constraints);

        constraints.gridx = 3;
        constraints.gridy = 5;
        ownerNameField.setText(model.getValueAt(row,11).toString());
        container.add(ownerNameField, constraints);




        constraints.gridx = 0;
        constraints.gridy = 6;
        container.add(ownerPassportInfo,constraints);

        constraints.gridx = 1;
        constraints.gridy = 6;
        ownerPassportField.setText(model.getValueAt(row,12).toString());
        container.add(ownerPassportField,constraints);

        constraints.gridx = 2;
        constraints.gridy = 6;
        container.add(ownerColorInfo, constraints);

        constraints.gridx = 3;
        constraints.gridy = 6;
        ownerColorField.setText(model.getValueAt(row,13).toString());
        container.add(ownerColorField, constraints);




        constraints.gridx = 0;
        constraints.gridy = 7;
        container.add(ownerLocationXInfo,constraints);

        constraints.gridx = 1;
        constraints.gridy = 7;
        ownerLocationXField.setText(model.getValueAt(row,14).toString());
        container.add(ownerLocationXField,constraints);

        constraints.gridx = 2;
        constraints.gridy = 7;
        container.add(ownerLocationYInfo, constraints);

        constraints.gridx = 3;
        constraints.gridy = 7;
        ownerLocationYField.setText(model.getValueAt(row,15).toString());
        container.add(ownerLocationYField, constraints);




        constraints.gridx = 0;
        constraints.gridy = 8;
        container.add(ownerLocationZInfo,constraints);

        constraints.gridx = 1;
        constraints.gridy = 8;
        ownerLocationZField.setText(model.getValueAt(row,16).toString());
        container.add(ownerLocationZField,constraints);

        constraints.gridx = 2;
        constraints.gridy = 8;
        container.add(ownerLocationNameInfo, constraints);

        constraints.gridx = 3;
        constraints.gridy = 8;
        ownerLocationNameField.setText(model.getValueAt(row,17).toString());
        container.add(ownerLocationNameField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 9;
        constraints.gridwidth = 4;
        constraints.gridheight = 1;
        constraints.ipady = 1;
        constraints.ipadx = 1;
        constraints.insets = new Insets(10,0,0,0);
        button.addActionListener(new ChangeButtonEventListener());
        container.add(button, constraints);

        constraints.gridx = 0;
        constraints.gridy = 10;
        constraints.gridwidth = 4;
        constraints.gridheight = 1;
        deleteButton.addActionListener(new DeleteButtonEventListener());
        container.add(deleteButton, constraints);

    }

    class ChangeButtonEventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Result<Object> resultOfCheckCell = new FieldResult<>();
            ClientDataChecker clientDataChecker = client.getClientDataChecker();

            resultOfCheckCell = clientDataChecker.checkName(nameField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            resultOfCheckCell = clientDataChecker.checkCoordinatesX(xCoordField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            resultOfCheckCell = clientDataChecker.checkCoordinatesY(yCoordField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            resultOfCheckCell = clientDataChecker.checkPrice(priceField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            resultOfCheckCell = clientDataChecker.checkPartNumber(partNumberField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            resultOfCheckCell = clientDataChecker.checkManufactureCost(manufactureField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            resultOfCheckCell = clientDataChecker.checkUnitOfMeasure(unitField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            resultOfCheckCell = clientDataChecker.checkOwnerName(ownerNameField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }


            if (!ownerPassportField.getText().equals(model.getValueAt(row, 12))) {
                resultOfCheckCell = clientDataChecker.checkOwnerPassportId(ownerPassportField.getText(), true);
            } else {
                resultOfCheckCell = clientDataChecker.checkOwnerPassportId(ownerPassportField.getText(), false);
            }
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            resultOfCheckCell = clientDataChecker.checkOwnerHairColor(ownerColorField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            resultOfCheckCell = clientDataChecker.checkOwnerLocationX(ownerLocationXField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            resultOfCheckCell = clientDataChecker.checkOwnerLocationY(ownerLocationYField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            resultOfCheckCell = clientDataChecker.checkOwnerLocationZ(ownerLocationZField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            resultOfCheckCell = clientDataChecker.checkOwnerLocationName(ownerLocationNameField.getText());
            if (resultOfCheckCell.hasError()){
                JOptionPane.showMessageDialog(null, resultOfCheckCell.getError(), "", JOptionPane.INFORMATION_MESSAGE);
                return;
            }




            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            Integer key = Integer.parseInt(keyField.getText());
            String userName = userField.getText();
            long id = Long.parseLong(idField.getText());
            String name = nameField.getText();
            Long xCoord = Long.parseLong(xCoordField.getText());
            Double yCoord = Double.parseDouble(yCoordField.getText());
            LocalDateTime date = LocalDateTime.parse(dateField.getText(), formatter);
            double price = Double.parseDouble(priceField.getText());
            String partNumber = partNumberField.getText();
            double manufacture = Double.parseDouble(manufactureField.getText());
            UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(unitField.getText());
            String ownerName = ownerNameField.getText();
            String ownerPassport = ownerPassportField.getText();
            data.Color ownerColor = data.Color.valueOf(ownerColorField.getText());
            long ownerLocX = Long.parseLong(ownerLocationXField.getText());
            double ownerLocY = Double.parseDouble(ownerLocationYField.getText());
            Float ownerLocZ = Float.parseFloat(ownerLocationZField.getText());
            String ownerLocName = ownerLocationNameField.getText();

            Product product = new Product(id, name, new Coordinates(xCoord, yCoord),
                    date, price, partNumber, manufacture, unitOfMeasure,
                    new Person(ownerName, ownerPassport, ownerColor, new Location(ownerLocX,ownerLocY, ownerLocZ, ownerLocName)), userName);

            Product oldProduct = mainFrame.getProduct(row);

            CommandPacket packet = new CommandPacket("update", oldProduct.getId(), product);
            packet.setUser(client.getLogin(), client.getMessenger());
            client.getClientSender().send(packet);
            frame.dispose();

        }
    }

    class DeleteButtonEventListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            Packet packet = new CommandPacket("remove_key", mainFrame.getTable().getModel().getValueAt(row, 0));
            packet.setUser(client.getLogin(), client.getMessenger());
            client.getClientSender().send(packet);
        }
    }
}
