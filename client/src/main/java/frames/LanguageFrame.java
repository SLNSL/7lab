package frames;

import clientClasses.ClientAccount;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.net.DatagramPacket;

public class LanguageFrame extends JFrame {
    private JLabel enterTheLanguageLabel = new JLabel("Введите язык / Enter the language / Introduceți limba / Introduïu l'idioma  [rus; eng; rum; kat]:");
    private JTextField languageField = new JTextField("", 5);
    private JButton button = new JButton("GO");
    private String language = "";
    private JFrame frame;

    private ClientAccount clientAccount;

    public LanguageFrame(ClientAccount clientAccount) {
        this.clientAccount = clientAccount;
        frame = new JFrame("Language");
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        frame.setBounds(dimension.width / 2 - 300, dimension.height / 2 - 100, 600, 200);

        Container container = frame.getContentPane();
        container.setLayout(new GridLayout(3, 1, 2, 2));
        container.add(enterTheLanguageLabel);
        container.add(languageField);

        button.addActionListener(new ButtonEventListener());
        container.add(button);
    }

    class ButtonEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            language = languageField.getText();
            frame.dispose();
            clientAccount.generateClient(language);

        }
    }

    public String getLanguage() {
        return language;
    }
}
