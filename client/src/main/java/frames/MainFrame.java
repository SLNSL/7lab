package frames;

import clientClasses.Client;
import data.*;
import messenger.Messenger;
import scanners.MyScanner;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

public class MainFrame extends JFrame {

    private String login;
    private MyScanner scanner;
    private Client client;

    private MainFrame frame;
    private JTextField textField = new JTextField(5);
    private JTextArea servMessage = new JTextArea("");
    private JScrollPane scrollPane;
    private JTable table;
    private AutomaticUpdater automaticUpdater;
    private JScrollPane scrollPaneTable;
    private JLabel userLabel;
    private JScrollPane paintScrollPane;
    private MyCanvas paintPanel;
    private JButton enterButton;
    private JButton languageButton;

    private HashMap<Long, Integer> listOfX = new HashMap();
    private HashMap<Long, Integer> listOfY = new HashMap();
    private HashMap<Long, Integer> listOfRealX = new HashMap();
    private HashMap<Long, Integer> listOfRealY = new HashMap();
    private HashMap<Long, String> listOfUsers = new HashMap();
    private ArrayList<Long> listOfID = new ArrayList<>();
    private HashMap<Long, Integer> listOfSize = new HashMap();

    private boolean isTableFiltered = false;
    private JTextField filterWord;
    private JTextField filterColumn;
    private JButton filterButton;

    public void run(String login, Messenger messenger) {
        this.login = login;
        automaticUpdater.start();
        setVisible(true);
        userLabel.setText(messenger.currentUserWord() + login);
        languageButton.setText(messenger.changeLanguageWord());
        enterButton.setText(messenger.enterWord());
        filterButton.setText(messenger.onFilterWord());
    }

    public MainFrame(MyScanner scanner, Client client) {
        this.client = client;
        this.scanner = scanner;
        automaticUpdater = new AutomaticUpdater(this, client);
        this.frame = this;
        filterWord = new JTextField();
        filterColumn = new JTextField();
        filterButton = new JButton();

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        enterButton = new JButton();
        languageButton = new JButton();

        Container container = frame.getContentPane();
        container.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        container.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.weightx = 0.5;

        constraints.gridx = 3;
        constraints.gridy = 0;
        constraints.weightx = 0.2;

        container.add(filterWord, constraints);

        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.weightx = 0.03;

        container.add(filterColumn, constraints);

        constraints.gridx = 5;
        constraints.gridy = 0;
        constraints.weightx = 0.2;
        constraints.ipady = 15;
        filterButton.addActionListener(new FilterButtonEventListener());
        filterButton.setMaximumSize(new Dimension(100, 10));
        filterButton.setMinimumSize(new Dimension(100, 10));
        container.add(filterButton, constraints);


        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.weightx = 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.ipady = 550;
        paintPanel = new MyCanvas(this);
        paintPanel.setMinimumSize(new Dimension(950, 550));
        paintPanel.setMaximumSize(new Dimension(950, 550));

        paintPanel.addMouseListener(new CanvasEventListener());
        paintScrollPane = new JScrollPane(paintPanel);


        container.add(paintScrollPane, constraints);

        constraints.gridx = 3;
        constraints.gridy = 1;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.weightx = 0.67;


        Vector<String> header = new Vector<>();
        header.addAll(new ArrayList<>() {
            {
                add("key");
                add("user_name");
                add("id");
                add("name");
                add("x_coordinates");
                add("y_coordinates");
                add("date");
                add("price");
                add("part_number");
                add("manufacture_cost");
                add("unit_of_measure");
                add("name_person");
                add("passport_id_person");
                add("hair_color_person");
                add("x_location_person");
                add("y_location_person");
                add("z_location_person");
                add("name_location_person");
            }
        });

        MyDefaultTableModel model = new MyDefaultTableModel(header, 0);
        table = new JTable(model);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.setAutoCreateRowSorter(true);
        table.addMouseListener(new TableClick());
        scrollPaneTable = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        container.add(scrollPaneTable, constraints);


        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 6;
        constraints.gridheight = 1;
        constraints.ipady = 120;
        DefaultCaret caret = (DefaultCaret) servMessage.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scrollPane = new JScrollPane(servMessage, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        container.add(scrollPane, constraints);


        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.ipady = 20;
        constraints.weightx = 0.38;
        userLabel = new JLabel(login);
        userLabel.setSize(100, 200);
        userLabel.setMaximumSize(new Dimension(125, 20));
        userLabel.setMinimumSize(new Dimension(125, 20));
        container.add(userLabel, constraints);


        constraints.gridx = 1;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.insets = new Insets(10, 10, 0, 0);

        container.add(textField, constraints);

        constraints.gridx = 2;
        constraints.gridy = 3;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        constraints.weightx = 0.5;
        constraints.insets = new Insets(10, 10, 0, 0);
        enterButton.setMinimumSize(new Dimension(20, 20));
        enterButton.setMaximumSize((new Dimension(20, 20)));
        enterButton.addActionListener(new ButtonEventListener());
        container.add(enterButton, constraints);


        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.gridwidth = 3;
        constraints.gridheight = 1;
        constraints.weightx = 0.5;
        constraints.insets = new Insets(10, 10, 0, 0);
        languageButton.setMinimumSize(new Dimension(125, 20));
        languageButton.setMaximumSize((new Dimension(125, 20)));
        languageButton.addActionListener(new LanguageButtonEventListener());

        container.add(languageButton, constraints);


    }

    class ButtonEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            scanner.setLine(textField.getText());
            textField.setText("");
        }
    }

    class LanguageButtonEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            ChangeLanguageFrame changeLanguageFrame = new ChangeLanguageFrame(client, frame);
        }
    }

    class FilterButtonEventListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (!isTableFiltered && !filterWord.getText().isEmpty() && !filterColumn.getText().isEmpty()) {
                isTableFiltered = true;
            } else {
                if (isTableFiltered) {
                    isTableFiltered = false;

                }
            }
            if (isTableFiltered){
                int column = Integer.parseInt(filterColumn.getText());
                String text = filterWord.getText();
                RowFilter rowFilter = RowFilter.regexFilter(text,column);
                filterButton.setText(client.getMessenger().offFilterWord());
                ((TableRowSorter)table.getRowSorter()).setRowFilter(rowFilter);

            } else {
                filterButton.setText(client.getMessenger().onFilterWord());
                ((TableRowSorter)table.getRowSorter()).setRowFilter(null);
            }

        }
    }

    class CanvasEventListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {

            int x = e.getX();
            int y = e.getY();

            ArrayList<Integer> listOfObjectsNearThePoint = new ArrayList<>();

            for (int i = 0; i < listOfID.size(); i++) {
                if (listOfX.get(listOfID.get(i)) <= x && x <= listOfX.get(listOfID.get(i)) + 20 && listOfY.get(listOfID.get(i)) <= y && y <= listOfY.get(listOfID.get(i)) + 20) {
                    listOfObjectsNearThePoint.add(getRowById(listOfID.get(i)));
                }
            }

            int objectRow = Integer.MAX_VALUE;
            long minId = Long.MAX_VALUE;
            if (listOfObjectsNearThePoint.size() == 0) return;
            else {
                if (listOfObjectsNearThePoint.size() > 1) {
                    for (int i = 0; i < listOfObjectsNearThePoint.size(); i++) {
                        if ((Long) table.getModel().getValueAt(listOfObjectsNearThePoint.get(i), 2) < minId) {
                            minId = (Long) table.getModel().getValueAt(listOfObjectsNearThePoint.get(i), 2);
                            objectRow = listOfObjectsNearThePoint.get(i);
                        }
                    }
                }
                if (listOfObjectsNearThePoint.size() == 1) {

                    objectRow = listOfObjectsNearThePoint.get(0);
                }

                FieldInfoFrame fieldInfoFrame = new FieldInfoFrame(frame, client, objectRow);
            }
        }
    }

    public void addTextToLabel(String message) {
        String oldMessages = servMessage.getText() + message;
        servMessage.setText(oldMessages);
    }

    public void updateRequest(Vector<Vector<String>> data) {

        deleteOldData(data);
        Vector<Vector<String>> onlyNewData = getOnlyNewData(data);

        MyDefaultTableModel model = (MyDefaultTableModel) table.getModel();
        for (int i = 0; i < onlyNewData.size(); i++) {
            model.addRow(onlyNewData.get(i));
            int x = Integer.parseInt(onlyNewData.get(i).get(4));
            double yDouble = Double.parseDouble(onlyNewData.get(i).get(5));
            int y = Math.toIntExact(Math.round(yDouble));
            long id = Long.valueOf(onlyNewData.get(i).get(2));

            listOfID.add(id);

            listOfRealX.put(id, x);
            if (x > 950) x = 950;
            listOfX.put(id, x);
            listOfRealY.put(id, y);
            if (y > 550) y = 550;
            listOfY.put(id, y);

            listOfSize.put(id, 0);
            listOfUsers.put(id, onlyNewData.get(i).get(1));
            paintPanel.addAnimation(id);

        }


    }

    public void setLogin(String login) {
        userLabel.setText(login);
    }

    public Vector<Vector<String>> getOnlyNewData(Vector<Vector<String>> data) {
        Vector<String> rows = new Vector<>();
        Vector<Vector<String>> tableData = new Vector<>();
        for (int i = 0; i < table.getModel().getRowCount(); i++) {
            for (int j = 0; j < table.getModel().getColumnCount(); j++) {
                rows.add(table.getModel().getValueAt(i, j).toString());
            }
            tableData.add(rows);
            rows = new Vector<>();
        }


        Vector<Vector<String>> onlyNewData = new Vector<>();
        for (int i = 0; i < data.size(); i++) {
            if (!tableData.contains(data.get(i))) {
                onlyNewData.add(data.get(i));
            }
        }
        return onlyNewData;

    }

    public void deleteOldData(Vector<Vector<String>> data) {
        Vector<String> rows = new Vector<>();
        Vector<Vector<String>> tableData = new Vector<>();
        for (int i = 0; i < table.getModel().getRowCount(); i++) {
            for (int j = 0; j < table.getModel().getColumnCount(); j++) {
                rows.add(table.getModel().getValueAt(i, j).toString());
            }
            tableData.add(rows);
            rows = new Vector<>();
        }

        for (int i = 0; i < tableData.size(); i++) {
            if (!data.contains(tableData.get(i))) {
                Long id = Long.valueOf(tableData.get(i).get(2));

                Integer x = Integer.parseInt(tableData.get(i).get(4));
                double yDouble = Double.parseDouble(tableData.get(i).get(5));
                Integer y = Math.toIntExact(Math.round(yDouble));
                paintPanel.deleteAnimation(id);
                listOfRealX.remove(id);
                listOfRealY.remove(id);
                if (x > 950) x = 950;
                if (y > 550) y = 550;
                listOfX.remove(id);
                listOfY.remove(id);
                listOfSize.remove(id);
                listOfUsers.remove(id);
                listOfSize.put(id, 0);
                listOfID.remove(id);

                ((MyDefaultTableModel) table.getModel()).removeRow(i);
                paintPanel.repaint();
            }
        }
    }

    class TableClick extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            JTable obj = (JTable) e.getSource();
            int row = obj.rowAtPoint(e.getPoint());
            int col = obj.columnAtPoint(e.getPoint());
            row = table.convertRowIndexToModel(row);
            col = table.convertColumnIndexToModel(col);
            DataEditorFrame dataEditorFrame = new DataEditorFrame(frame, client, row, col);


        }
    }

    public HashMap<Long, Integer> getListOfX() {
        return listOfX;
    }

    public HashMap<Long, Integer> getListOfY() {
        return listOfY;
    }

    public HashMap<Long, Integer> getListOfRealX() {
        return listOfRealX;
    }

    public HashMap<Long, Integer> getListOfRealY() {
        return listOfRealY;
    }

    public JTable getTable() {
        return table;
    }

    public Product getProduct(int row) {
        Product product;

        MyDefaultTableModel model = (MyDefaultTableModel) table.getModel();
        String userName = (String) model.getValueAt(row, 1);
        long id = (Long) model.getValueAt(row, 2);
        String name = (String) model.getValueAt(row, 3);
        Long xCoord = (Long) model.getValueAt(row, 4);
        Double yCoord = (Double) model.getValueAt(row, 5);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime date = LocalDateTime.parse((String) model.getValueAt(row, 6), formatter);
        double price = (Double) model.getValueAt(row, 7);
        String partNumber = (String) model.getValueAt(row, 8);
        Double manufactureCost = (Double) model.getValueAt(row, 9);
        UnitOfMeasure unitOfMeasure = UnitOfMeasure.valueOf(model.getValueAt(row, 10).toString());

        if (model.getValueAt(row, 11) != null) {
            String ownerName = (String) model.getValueAt(row, 11);
            String ownerPassport = (String) model.getValueAt(row, 12);
            data.Color ownerColor = data.Color.valueOf(model.getValueAt(row, 13).toString());
            Long ownerLocationX = (Long) model.getValueAt(row, 14);
            Double ownerLocationY = (Double) model.getValueAt(row, 15);
            Float ownerLocationZ = (Float) model.getValueAt(row, 16);
            String ownerLocationName = (String) model.getValueAt(row, 17);
            product = new Product(
                    id, name, new Coordinates(xCoord, yCoord), date, price, partNumber, manufactureCost,
                    unitOfMeasure, new Person(ownerName, ownerPassport, ownerColor,
                    new Location(ownerLocationX, ownerLocationY, ownerLocationZ, ownerLocationName)), userName
            );
        } else {
            product = new Product(
                    id, name, new Coordinates(xCoord, yCoord), date, price, partNumber, manufactureCost,
                    unitOfMeasure, null, userName
            );
        }
        return product;
    }

    public HashMap<Long, String> getListOfUsers() {
        return listOfUsers;
    }

    public int getRowById(long id) {
        for (int i = 0; i < table.getModel().getRowCount(); i++) {
            if ((Long) (((MyDefaultTableModel) table.getModel()).getValueAt(i, 2)) == id) {
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }

    public HashMap<Long, Integer> getListOfSize() {
        return listOfSize;
    }

    public ArrayList<Long> getListOfID() {
        return listOfID;
    }

    public void updateTextToCurrentLanguage() {
        languageButton.setText(client.getMessenger().changeLanguageWord());
        userLabel.setText(client.getMessenger().currentUserWord() + client.getLogin());
        enterButton.setText(client.getMessenger().enterWord());
    }
}
