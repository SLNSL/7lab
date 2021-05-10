package frames;

import clientClasses.Client;
import messenger.MessengerEng;
import messenger.MessengerKat;
import messenger.MessengerRu;
import messenger.MessengerRum;
import wrappers.CommandPacket;
import wrappers.Packet;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeLanguageFrame extends JFrame {

    private JButton ruButton = new JButton("Русский");
    private JButton engButton = new JButton("English");
    private JButton katButton = new JButton("Català");
    private JButton rumButton = new JButton("Română");

    private Client client;
    private JFrame frame;
    private MainFrame mainFrame;

    public ChangeLanguageFrame(Client client, MainFrame mainFrame){
        this.frame = this;
        this.client = client;
        this.mainFrame = mainFrame;

        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        this.setBounds(dimension.width / 2 - 175, dimension.height / 2 - 50, 350, 100);

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
        constraints.ipady = 20;
        ruButton.addActionListener(new RuButtonEventListener());
        container.add(ruButton, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.ipady = 20;
        engButton.addActionListener(new EngButtonEventListener());
        container.add(engButton, constraints);

        constraints.gridx = 2;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.ipady = 20;
        katButton.addActionListener(new KatButtonEventListener());
        container.add(katButton, constraints);

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.ipady = 20;
        rumButton.addActionListener(new RumButtonEventListener());
        container.add(rumButton, constraints);
    }


    class RuButtonEventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            client.setMessenger(new MessengerRu());
            mainFrame.updateTextToCurrentLanguage();
            frame.dispose();
        }
    }

    class EngButtonEventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            client.setMessenger(new MessengerEng());
            mainFrame.updateTextToCurrentLanguage();
            frame.dispose();
        }
    }


    class RumButtonEventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            client.setMessenger(new MessengerRum());
            mainFrame.updateTextToCurrentLanguage();
            frame.dispose();
        }
    }

    class KatButtonEventListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            client.setMessenger(new MessengerKat());
            mainFrame.updateTextToCurrentLanguage();
            frame.dispose();
        }
    }
}
