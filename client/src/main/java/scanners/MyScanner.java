package scanners;

import java.util.Scanner;

public class MyScanner extends Thread{
    private String string = null;

    public MyScanner(){

    }

    public synchronized String nextLine(){
        try {
            wait();
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        String message = getString();

        return message;
    }

    public synchronized void setLine(String string){
        this.string = string;
        notify();
    }

    public String getString() {
        return string;
    }
}
