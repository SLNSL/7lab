package frames;

import data.Color;
import data.UnitOfMeasure;

import javax.print.DocFlavor;
import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class MyDefaultTableModel extends DefaultTableModel {

    public MyDefaultTableModel(Vector<?> v, int i){
        super(v,i);
    }



    @Override
    public Class<?> getColumnClass(int column) {
        return switch (column) {
            case 0 -> Integer.class;
            case 2, 4, 14 -> Long.class;
            case 5, 7, 9, 15 -> Double.class;
            case 10 -> UnitOfMeasure.class;
            case 13 -> Color.class;
            case 16 -> Float.class;
            default -> super.getColumnClass(column);
        };


    }

    @Override
    public Object getValueAt(int row, int column) {
        Object value = super.getValueAt(row, column);
        return switch (column){
            case 0 -> Integer.parseInt(value.toString());
            case 2, 4, 14 -> Long.parseLong(value.toString());
            case 5, 7, 9, 15 -> Double.parseDouble(value.toString());
            case 10 -> UnitOfMeasure.valueOf(value.toString());
            case 13 -> Color.valueOf(value.toString());
            case 16 -> Float.parseFloat(value.toString());
            default -> value;
        };
    }

    @Override
    public synchronized void removeRow(int row) {
        super.removeRow(row);
    }
}
