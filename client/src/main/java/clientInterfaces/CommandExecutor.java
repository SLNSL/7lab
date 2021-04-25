package clientInterfaces;

import clientClasses.ClientAccount;

import java.util.Map;
import java.util.Scanner;

public interface CommandExecutor {

    void setScanner(Scanner scanner);
    void runCommand(ClientAccount clientAuthorization, String[] command);

    boolean runScript(ClientAccount clientAuthorization, String argument);

    void setMapOfScripts(Map<String, Boolean> mapOfScripts);
}
