package pattern;

import com.google.gson.Gson;
import comparators.UnitOfMeasureComparator;
import creators.DataBase;
import data.Person;
import data.Product;
import exception.EmptyCollectionException;
import exception.InputOutputException;
import messenger.Messenger;
import checker.ServerDataChecker;
import creators.CollectionCreator;
import creators.Creator;
import messenger.MessengerEng;
import wrappers.FieldResult;
import wrappers.Result;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Класс для коллекции
 */
public class CollectionManager implements Collection {
    /**
     * Коллекция
     */
    private Map<Integer, Product> productCollection = new LinkedHashMap<>();

    private LocalDateTime initTime;
    private Loader fileManager;

    private ServerDataChecker fieldsChecker;

    private Messenger messenger = new MessengerEng();

    private DataBase dataBase;


    public CollectionManager(Loader fileManager, ServerDataChecker fieldsChecker, DataBase dataBase) {

        this.fileManager = fileManager;
        this.fieldsChecker = fieldsChecker;
        this.dataBase = dataBase;
    }

    /**
     * Загрузить коллекцию используя файл менеджер
     */
    public void load() {
        Creator creator = new CollectionCreator();
        productCollection = creator.createCollection(fileManager.load(fieldsChecker), new Gson(), fieldsChecker, messenger, dataBase);
        initTime = LocalDateTime.now();
    }

    /**
     * удаляет элемент по ключу
     *
     * @param key - ключ
     */
    public Result<Object> canBeDeleted(Integer key, String login) {
        Result<Object> result = new FieldResult<>();
        if (productCollection.get(key) == null) {
            result.setError(messenger.generateElementDoesntExistMessage(), 1);
            return result;
        }

        if (!productCollection.get(key).getUserName().equals(login)){
            result.setError(messenger.youDontHaveRights(), 1);
            return result;
        }

        return result;
    }

    /**
     * сохранить коллекцию в файле
     */
    public void save() throws InputOutputException {
        fileManager.save(productCollection);
    }


    /**
     * Возвращает информацию о коллекции
     *
     * @return информация о коллекции в строковом представлении
     */
    public String info() {


        return messenger.getInfo(productCollection.getClass().getName(), Product.class.getName(), initTime, productCollection.size());
    }


    /**
     * Подсчитывает количество элементов, значение поля owner которых меньше указанного значения.
     *
     * @param person - человек
     * @return - количество
     */
    public long countLessThanOwner(Person person) {
        long count = 0;

        count = productCollection.entrySet().stream()
                .filter(x -> (x.getValue().getOwner() != null && person.compareTo(x.getValue().getOwner()) > 0))
                .count();

        return count;
    }


    /**
     * Возвращает поля элементов коллекции
     *
     * @return - поля элементов коллекции в строков представлении
     * @throws EmptyCollectionException - если коллекция пуста
     */
    public String writeCollection() throws EmptyCollectionException {
        String collectionInfo = "";

        if (productCollection.size() == 0) {
            throw new EmptyCollectionException(messenger.generateEmptyCollectionMessage());
        } else {
            for (Integer key : productCollection.keySet()) {
                collectionInfo += messenger.getFieldsInfo(key, productCollection.get(key));
            }
        }
        return collectionInfo;
    }

    /**
     * Генерирует id, который еще не существует
     *
     * @return id
     */
    public long generateID() {
        Long id = Long.valueOf(productCollection.size()) + 1;
        while (fieldsChecker.isIdExist(id)) {
            id = id + 1;
        }
        return id;
    }

    /**
     * Возвращает продукт по id
     *
     * @param id
     * @return - product
     */
    public Result<Product> getProductById(long id) {
        Product product = null;
        Result<Product> result = new FieldResult<>();

        try {
            product = productCollection.entrySet().stream()
                    .filter(x -> x.getValue().getId() == id)
                    .findFirst().get().getValue();
            result.setResult(product);
        } catch (NoSuchElementException e) {
            result.setError(messenger.generateElementDoesntExistMessage(), 1);
            return result;
        }

        return result;
    }

    /**
     * Выдаёт ключ по id
     *
     * @param id
     * @return - key
     */
    public Result<Integer> getKeyById(long id) {
        Result<Integer> keyResult = new FieldResult<>();
        Integer key;

        try {
            key = productCollection.entrySet().stream()
                    .filter(x -> x.getValue().getId() == id)
                    .findFirst().get().getKey();
            keyResult.setResult(key);
        } catch (NoSuchElementException e) {
            keyResult.setError(messenger.generateElementDoesntExistMessage(), 1);
            return keyResult;
        }

        return keyResult;
    }

    /**
     * Возвращает первый элемент в коллекции
     *
     * @return first element
     */
    public Product getFirst() {
        sort();
        return productCollection.entrySet().stream()
                .findFirst().get().getValue();
    }

    /**
     * Возвращает элемент, который минимален по unitOfMeasure
     *
     * @return - элемент
     * @throws EmptyCollectionException - если коллекция пуста
     */
    public Result<Product> minByUnitOfMeasure() {
        if (productCollection.isEmpty()) {
            Result<Product> result = new FieldResult<>();
            result.setError(messenger.generateEmptyCollectionMessage(), 1);
            return result;
        }
        Product p1;

        Map.Entry productEntry = ((Map.Entry) productCollection.entrySet().stream()
                .sorted(new UnitOfMeasureComparator())
                .findFirst().get());

        Result<Product> result = new FieldResult<>();
        p1 = (Product) productEntry.getValue();
        result.setResult(p1);

        return result;
    }

    /**
     * Возвращает элемент, максимальный по unitOfMeasure
     *
     * @return элемент
     * @throws EmptyCollectionException - если коллекция пуста
     */
    public Result<Product> maxByUnitOfMeasure() {
        if (productCollection.isEmpty()) {
            Result<Product> result = new FieldResult<>();
            result.setError(messenger.generateEmptyCollectionMessage(), 1);
            return result;
        }
        ;
        Product p1;
        Result<Product> result = new FieldResult<>();
        Map.Entry productEntry = ((Map.Entry) productCollection.entrySet().stream()
                .sorted(new UnitOfMeasureComparator().reversed())
                .findFirst().get());

        p1 = (Product) productEntry.getValue();
        result.setResult(p1);

        return result;
    }

    /**
     * Возвращает элемент по ключу
     *
     * @param key
     * @return - element
     */
    public Result<Product> get(Integer key) {
        Result<Product> result = new FieldResult<>();
        try {
            Product product = productCollection.entrySet().stream()
                    .filter(x -> x.getKey().equals(key))
                    .findFirst().get().getValue();
            result.setResult(product);
        } catch (NoSuchElementException e) {
            result.setError(messenger.generateElementDoesntExistMessage(), 1);
            return result;
        }
        return result;
    }




    /**
     * Сортирует коллекции
     */
    public void sort() {
        List<Product> mapValues = new ArrayList<>(productCollection.values());
        LinkedHashMap<Integer, Product> newProductCollection = new LinkedHashMap<>();
        mapValues = mapValues
                .stream()
                .sorted()
                .collect(Collectors.toList());

        productCollection = mapValues
                .stream()
                .collect(Collectors.toMap(x -> getKey(x), x -> x, (x, y) -> y, LinkedHashMap::new));
    }

    /**
     * Возвращает ключ по продукту
     *
     * @param product
     * @return key
     */
    public Integer getKey(Product product) {
        for (Integer i : productCollection.keySet()) {
            if (productCollection.get(i).equals(product)) return i;
        }
        return null;
    }

    public Map<Integer, Product> getProductCollection() {
        return productCollection;
    }

    public void setMessenger(Messenger messenger) {
        this.messenger = messenger;
        this.fieldsChecker.setMessenger(messenger);
    }

    @Override
    public synchronized Vector<Vector<String>> updateRequest() {
        Vector<Vector<String>> data = new Vector<>();


        for (Integer i : dataBase.getData().keySet()){
            Product product = dataBase.getData().get(i);
            Vector<String> row = new Vector<>();
            row.add(String.valueOf(getKey(product)));
            row.add(product.getUserName());
            row.add(String.valueOf(product.getId()));
            row.add(String.valueOf(product.getName()));
            row.add(String.valueOf(product.getCoordinates().getX()));
            row.add(String.valueOf(product.getCoordinates().getY()));
            row.add(String.valueOf(product.getCreationDate()));
            row.add(String.valueOf(product.getPrice()));
            row.add(String.valueOf(product.getPartNumber()));
            row.add(String.valueOf(product.getManufactureCost()));
            row.add(String.valueOf(product.getUnitOfMeasure()));
            if (product.getOwner() == null){
                row.add(null);
                row.add(null);
                row.add(null);
                row.add(null);
                row.add(null);
                row.add(null);
                row.add(null);
            } else {
                row.add(String.valueOf(product.getOwner().getName()));
                row.add(String.valueOf(product.getOwner().getPassportID()));
                row.add(String.valueOf(product.getOwner().getHairColor()));
                row.add(String.valueOf(product.getOwner().getLocation().getX()));
                row.add(String.valueOf(product.getOwner().getLocation().getY()));
                row.add(String.valueOf(product.getOwner().getLocation().getZ()));
                row.add(String.valueOf(product.getOwner().getLocation().getName()));
            }
            data.add(row);


        }
        return data;

    }

    @Override
    public synchronized void updateCollection() {
        productCollection = dataBase.getData();
        fieldsChecker.setMapOfId(new HashMap<>());
        fieldsChecker.setMapOfPassportId(new HashMap<>());

        List<Long> listOfId = dataBase.getIdData();
        List<String> listOfPassportId = dataBase.getPassportIdData();

        for (Long id : listOfId){
            fieldsChecker.getMapOfId().put(id, true);
        }

        for (String passportId: listOfPassportId){
            fieldsChecker.getMapOfPassportId().put(passportId, true);
        }
    }



}
