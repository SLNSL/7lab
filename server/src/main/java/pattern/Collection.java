package pattern;

import creators.DataBase;
import data.Person;
import data.Product;
import exception.EmptyCollectionException;
import messenger.Messenger;
import wrappers.Result;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Map;
import java.util.Vector;

public interface Collection {

    void load();

    Result<Object> canBeDeleted(Integer key, String login);

    void save();

    String info();

    long countLessThanOwner(Person person);

    String writeCollection() throws EmptyCollectionException;

    long generateID();

    Result<Product> getProductById(long id);

    Result<Integer> getKeyById(long id);

    public Product getFirst();

    Result<Product> minByUnitOfMeasure();

    Result<Product> maxByUnitOfMeasure();

    Result<Product> get(Integer key);

    void sort();

    Integer getKey(Product product);

    Map<Integer, Product> getProductCollection();

    void setMessenger(Messenger messenger);

    void updateCollection();

   Vector<Vector<String>> updateRequest();


}



