package wrappers;


import messenger.Messenger;

import java.io.Serializable;

public class CommandPacket implements Packet, Serializable {
    private static final long serialVersionUID = 1234567890L;

    private String command;
    private Object[] arguments;
    private String error = "";

    private String login;
    private String password;
    private Messenger messenger;


    public CommandPacket(String error) {
        this.error = error;
    }

    public CommandPacket(String command, Object... arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCommand() {
        return command;
    }

    public Object[] getArguments() {
        if (arguments != null) return arguments;
        Object[] obj = new Object[1];
        obj[0] = "";
        return obj;
    }

    public String getError() {
        return error;
    }

    @Override
    public void setUser(String login, Messenger messenger) {
        this.login = login;
        this.messenger = messenger;
    }

    @Override
    public String getLogin() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Messenger getMessenger() {
        return messenger;
    }

    public boolean hasError() {
        return !error.equals("");
    }



}
