package printer;

import frames.MainFrame;

import javax.swing.*;

public class Printer implements Printable {
    private MainFrame frame;

    public Printer(MainFrame frame){
        this.frame = frame;
    }

    /**
     * Выводит сообщение
     *
     * @param object сообщение
     */
    @Override
    public void print(Object object) {
        frame.addTextToLabel(object.toString());
    }

    /**
     * Выводит сообщение с переносом на следующую строку
     *
     * @param object - сообщение
     */
    @Override
    public void println(Object object) {
        frame.addTextToLabel(object.toString() + "\n");
    }

    /**
     * Выводит сообщение ошибки
     *
     * @param object - сообщение
     */
    @Override
    public void printlnError(Object object) {
        frame.addTextToLabel(object.toString() + "\n");
    }

}
