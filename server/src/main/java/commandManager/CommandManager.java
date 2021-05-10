package commandManager;

import checker.ServerDataChecker;
import commands.*;
import creators.DataBase;
import pattern.Collection;
import serverClasses.ServerReceiverInterface;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class CommandManager implements Manager{


    /**
     * map, which contains links to the methods by their names
     */
    private HashMap<String, Command> mapOfCommands = new LinkedHashMap<>();


    private ServerReceiverInterface serverReceiver;

    private Command help;
    private Command exit;
    private Command show;
    private Command insert;
    private Command executeScript;
    private Command info;
    private Command removeKey;
    private Command update;
    private Command clear;
    private Command removeLower;
    private Command replaceIfGreater;
    private Command replaceIfLower;
    private Command minByUnitOfMeasure;
    private Command maxByUnitOfMeasure;
    private Command countLessThanOwner;
    private Command setLanguage;
    private Command signIn;
    private Command signUp;

    private Command updateData;


    public CommandManager() {
    }


    public CommandManager(ServerReceiverInterface serverReceiver,  DataBase dataBase, Collection collectionManager, ServerDataChecker fieldsChecker) {
        this.serverReceiver = serverReceiver;
        createCommands(collectionManager, fieldsChecker, dataBase);
        putLinkToCommands();

    }

    public void createCommands(Collection collectionManager, ServerDataChecker fieldsChecker, DataBase dataBase) {
        help = new HelpCommandServ();
        exit = new ExitCommandServ(collectionManager);
        show = new ShowCommandServ(collectionManager);
        setLanguage = new LanguageCommandServ();
        insert = new InsertCommandServ(collectionManager, fieldsChecker, dataBase);
        executeScript = new ExecuteScriptCommandServ();
        info = new InfoCommandServ(collectionManager);
        removeKey = new RemoveKeyCommandServ(collectionManager, fieldsChecker, dataBase);
        update = new UpdateCommandServ(collectionManager,  fieldsChecker, dataBase);
        clear = new ClearCommandServ(collectionManager, dataBase);
        removeLower = new RemoveLowerCommandServ(collectionManager, dataBase);
        replaceIfGreater = new ReplaceIfGreaterCommandServ(collectionManager, fieldsChecker, dataBase);
        replaceIfLower = new ReplaceIfLowerCommandServ(collectionManager, fieldsChecker, dataBase);
        minByUnitOfMeasure = new MinByUnitOfMeasureCommandServ(collectionManager);
        maxByUnitOfMeasure = new MaxByUnitOfMeasureCommandServ(collectionManager);
        countLessThanOwner = new CountLessThanOwnerCommandServ(collectionManager);
        signIn = new SignInCommandServ(dataBase, serverReceiver);
        signUp = new SignUpCommandServ(dataBase, serverReceiver);
        updateData = new UpdateDataCommandServ(collectionManager);
    }

    /**
     * Кладёт в мапу ссылки на выполнение методов, которые выполняют команды
     */
    public void putLinkToCommands() {
        mapOfCommands.put("help", this.help);
        mapOfCommands.put("exit", this.exit);
        mapOfCommands.put("show", this.show);
        mapOfCommands.put("insert", this.insert);
        mapOfCommands.put("execute_script", this.executeScript);
        mapOfCommands.put("info", this.info);
        mapOfCommands.put("remove_key", this.removeKey);
        mapOfCommands.put("update", this.update);
        mapOfCommands.put("clear", this.clear);
        mapOfCommands.put("remove_lower", this.removeLower);
        mapOfCommands.put("replace_if_greater", this.replaceIfGreater);
        mapOfCommands.put("replace_if_lower", this.replaceIfLower);
        mapOfCommands.put("min_by_unit_of_measure", this.minByUnitOfMeasure);
        mapOfCommands.put("max_by_unit_of_measure", this.maxByUnitOfMeasure);
        mapOfCommands.put("count_less_than_owner", this.countLessThanOwner);
        mapOfCommands.put("set_language", this.setLanguage);
        mapOfCommands.put("sign_in", this.signIn);
        mapOfCommands.put("sign_up", this.signUp);
        mapOfCommands.put("update_data", this.updateData);
    }


    public HashMap<String, Command> getMapOfCommands() {
        return mapOfCommands;
    }

    public Command getCommand(String stringCommand) {
        return mapOfCommands.get(stringCommand);
    }


}
