package creators;

import com.google.gson.Gson;
import data.Product;
import messenger.Messenger;
import checker.ServerDataChecker;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public interface Creator {
    Map<Integer, Product> createCollection(Map<Integer, Product> products, Gson gson, ServerDataChecker fieldsChecker, Messenger messenger, DataBase dataBase);
}
