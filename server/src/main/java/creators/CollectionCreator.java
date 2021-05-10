package creators;


import com.google.gson.Gson;
import data.Product;
import messenger.Messenger;
import checker.ServerDataChecker;
import checker.LoadedChecker;
import wrappers.Result;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CollectionCreator implements Creator {


    public CollectionCreator() {
    }

    public Map<Integer, Product> createCollection(Map<Integer, Product> products, Gson gson, ServerDataChecker fieldsChecker, Messenger messenger, DataBase dataBase) {

        fieldsChecker.setMapOfId(new HashMap<>());

        fieldsChecker.setMapOfPassportId(new HashMap<>());

        Map<Integer, Product> result = new LinkedHashMap<>();


        if (products.size() == 0) {
//            serverSender.send(new AnswerPacket(sendMessage), inetAddress, port);
            return result;
        }

        result = products;

        Result<Map<Integer, Product>> collectionResult = LoadedChecker.checkCollection(result, fieldsChecker, dataBase);
        result = collectionResult.getResult();


//        serverSender.send(new AnswerPacket(sendMessage), inetAddress, port);

        return result;
    }
}
