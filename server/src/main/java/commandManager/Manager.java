package commandManager;

import checker.ServerDataChecker;
import commands.Command;
import creators.DataBase;
import messenger.Messenger;
import pattern.Collection;

import java.util.HashMap;

public interface Manager {

    Command getCommand(String stringCommand);

    HashMap<String, Command> getMapOfCommands();

    void putLinkToCommands();

    void createCommands(Collection collectionManager, ServerDataChecker fieldsChecker, DataBase dataBase);
}
