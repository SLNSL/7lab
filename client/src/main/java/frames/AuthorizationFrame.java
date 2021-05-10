package frames;

import clientClasses.Client;
import messenger.Messenger;
import messenger.MessengerRu;
import wrappers.CommandPacket;
import wrappers.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthorizationFrame extends JFrame {


    private JTextField loginField;
    private JTextField passwordField;


    private Messenger messenger;
    private Client client;
    private JFrame frame;


    public AuthorizationFrame(Client client){
        this.frame = this;


        this.client = client;
        Messenger messenger = client.getMessenger();
        this.messenger = messenger;
        JLabel loginLabel = new JLabel(client.getMessenger().askLogin());
        loginField = new JTextField();
        JLabel passwordLabel = new JLabel(client.getMessenger().askPassword());
        passwordField = new JTextField();
        JButton inButton = new JButton(messenger.signInWord());
        JButton upButton = new JButton(messenger.signUpWord());

        JLabel authLabel = new JLabel(messenger.authorizationWord(), JLabel.CENTER);


        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - 150, dimension.height / 2 - 85, 300, 170);

        Container container = this.getContentPane();
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;



        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;
        container.add(authLabel,constraints);

        constraints.insets = new Insets(20,0,0,0);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.05;
        container.add(loginLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        container.add(loginField,constraints);


        constraints.insets = new Insets(10,0,0,0);
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.05;

        container.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        constraints.weightx = 0.5;
        container.add(passwordField,constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.weightx = 0.5;
        inButton.addActionListener(new InButtonEventListener());
        container.add(inButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.weightx = 0.5;
        upButton.addActionListener(new UpButtonEventListener());
        container.add(upButton, constraints);
    }


    class InButtonEventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String login = loginField.getText();
            String password = passwordField.getText();

            Packet packet;
            packet = new CommandPacket("sign_in",  login, password);

            packet.setUser(login, messenger);
            frame.dispose();
            client.run();

            client.getClientSender().send(packet);
        }
    }

    class UpButtonEventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String login = loginField.getText();
            String password = passwordField.getText();

            Packet packet;
            packet = new CommandPacket("sign_up",  login, password);

            packet.setUser(login, messenger);
            frame.dispose();
            client.run();

            client.getClientSender().send(packet);
        }
    }
}
