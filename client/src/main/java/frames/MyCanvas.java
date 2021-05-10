package frames;

import scanners.MyScanner;

import javax.accessibility.Accessible;
import javax.accessibility.AccessibleContext;
import javax.imageio.ImageIO;
import javax.naming.Context;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MyCanvas extends JPanel {
    private int x;
    private int y;

    private HashMap<String, Color> mapOfColors;

    private MainFrame frame;

    private Graphics graphics;

    private HashMap<Long, Integer> listOfX;
    private HashMap<Long, Integer> listOfY;
    private HashMap<Long, Integer> listOfSize;
    private ArrayList<Long> listOfId;


    public MyCanvas(MainFrame frame) {
        this.x = x;
        this.y = y;
        this.frame = frame;
        mapOfColors = new HashMap<>();

    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        this.graphics = g;
        listOfX = frame.getListOfX();
        listOfY = frame.getListOfY();
        HashMap<Long, String> listOfUsers = frame.getListOfUsers();
        listOfId = frame.getListOfID();
        listOfSize = frame.getListOfSize();
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\nik23\\OneDrive\\Рабочий стол\\Уник\\Программирование\\7_lab\\7_lab_gradle\\сетка.jpg"));
            g.drawImage(image, 0, 0, this);
        } catch (IOException e){
            e.printStackTrace();
        }

        for (int i = 0; i < listOfId.size(); i++) {

            int x = listOfX.get(listOfId.get(i));
            int y = listOfY.get(listOfId.get(i));

            g.setColor(getUserColor(listOfUsers.get(listOfId.get(i))));
            g.fillOval(x,y,listOfSize.get(listOfId.get(i)), listOfSize.get(listOfId.get(i)));

        }
    }

    public Color getUserColor(String user) {
        if (mapOfColors.get(user) == null) {
            mapOfColors.put(user, getNewColor());
        }
        return mapOfColors.get(user);
    }

    public Color getNewColor() {
        int r = Math.toIntExact(Math.round(Math.random() * 255));
        int g = Math.toIntExact(Math.round(Math.random() * 255));
        int b = Math.toIntExact(Math.round(Math.random() * 255));
        return new Color(r, g, b);
    }


    public void addAnimation(Long id) {
        int k = 0;
        repaint();
        while (k != 20) {

            try {
                Thread.currentThread().sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listOfSize.put(id,listOfSize.get(id)+1);
            repaint();
            k++;

        }

    }

    public void deleteAnimation(Long id){
        int k = 20;
        while (k!=0){
            try {
                Thread.currentThread().sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listOfSize.put(id,listOfSize.get(id)-1);
            repaint();
            k--;
        }
    }


}
