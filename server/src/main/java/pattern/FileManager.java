package pattern;

import com.google.gson.Gson;
import creators.DataBase;
import data.Product;
import exception.*;
import messenger.Messenger;
import messenger.MessengerRu;
import checker.ServerDataChecker;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class FileManager implements Loader {

    private DataBase dataBase;

    public FileManager(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    /**
     * Загружает коллекцию из файла
     *
     * @return - collection
     */
    public Map<Integer, Product> load(ServerDataChecker fieldsChecker) {
        Map<Integer, Product> products = new LinkedHashMap<Integer, Product>();

        products = dataBase.getData();
        return products;

    }

    /**
     * Сохранение коллекции в файл
     *
     * @param collection
     * @throws InputOutputException - если это было исключение ввода/вывода
     */
    public void save(Map<Integer, Product> collection) throws InputOutputException {
        dataBase.addData(collection);

    }

}
