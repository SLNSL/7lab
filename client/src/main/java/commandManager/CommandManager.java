package commandManager;

import commands.ClientCommands;

import java.util.HashMap;

public interface CommandManager {
    void generateCommandMaker();

    ClientCommands getCommand(String command);

    HashMap<String, ClientCommands> getCommandMaker();
}
