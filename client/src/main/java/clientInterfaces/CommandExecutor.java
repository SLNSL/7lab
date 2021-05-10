package clientInterfaces;

import clientClasses.ClientAccount;
import messenger.Messenger;

import java.util.Map;
import java.util.Scanner;

public interface CommandExecutor {

    void setScanner(Scanner scanner);
    void runCommand(String login, Messenger messenger, String[] command);

    boolean runScript(String login, Messenger messenger, String argument);

    void setMapOfScripts(Map<String, Boolean> mapOfScripts);
}
