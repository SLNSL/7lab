package creators;

import data.*;
import exception.UnexpectedException;
import messenger.Messenger;
import messenger.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wrappers.FieldResult;
import wrappers.Result;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DataBase {

    public static final Logger logger = LoggerFactory.getLogger("serverInterfaces.Server");
    private Connection connection = null;
    private Statement statement = null;

    public DataBase(String login, String password) throws UnexpectedException{

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            throw new UnexpectedException("Ошибка при подключении PostgreSQL JDBC Driver");
        }

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/Laba7", login, password);
            statement = connection.createStatement();
        } catch (SQLException e){
            throw new UnexpectedException("Ошибка при подключении к БД.");
        }

        logger.info("PostgreSQL JDBC Driver подключен успешно.");

    }

    public Result<String> signUpUser(String login, String password, String language){
        Result<String> result = new FieldResult<>();

        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS;");
            String loginFromDB = "";
            while (resultSet.next()){
                loginFromDB = resultSet.getString("login");
                if (login.equals(loginFromDB)) break;
            }
            if (login.equals(loginFromDB)){
                result.setError(new Translator().getAvailableLanguage(language).loginIsExist());
                return result;
            }


            byte[] passwordData = password.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
            byte[] digest = messageDigest.digest(passwordData);

            PreparedStatement insert = connection.prepareStatement(
                    "INSERT INTO USERS (login, password, language) VALUES (?, ?, ?);");
            insert.setString(1, login);
            insert.setBytes(2, digest);
            insert.setString(3, language);
            insert.executeUpdate();
            logger.info("Пользователь " + login + " зарегестрирован");
            result.setResult("");
            return result;

        } catch (NoSuchAlgorithmException | SQLException e){
            result.setError(e.getMessage());
            return result;
        }
    }

    public Result<String> signInUser(String login, String password, String language){
        Result<String> result = new FieldResult<>();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS;");

            String loginFromDB = "";
            byte[] passwordFromDB = null;
            while (resultSet.next()) {
                loginFromDB = resultSet.getString("login");
                passwordFromDB = resultSet.getBytes("password");
                if (login.equals(loginFromDB)) break;
            }

            if (!login.equals(loginFromDB)) {
                result.setError(Translator.getAvailableLanguage(language).incorrectLogin());
                return result;
            }


            byte[] passwordData = password.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-384");
            byte[] digest = messageDigest.digest(passwordData);

            if (!Arrays.equals(passwordFromDB, digest)) {
                result.setError(new Translator().getAvailableLanguage(language).incorrectPassword());
                return result;
            }
            PreparedStatement updateStatement = connection.prepareStatement(
                    "UPDATE users SET password = ?, language = ? WHERE login = ?"
            );
            updateStatement.setString(3, login);
            updateStatement.setBytes(1, passwordFromDB);
            updateStatement.setString(2, language);
            updateStatement.executeUpdate();


            logger.info("Пользователь " + login + " подключен");
            result.setResult("");
            return result;

        } catch (SQLException | NoSuchAlgorithmException e){
            logger.error("Произошла ошибка", e);
        }
        return null;
    }

    public Messenger getUserMessenger(String login){
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM USERS;");
            String loginFromDB = "";
            String languageFromDB = "";

            while (resultSet.next()) {
                loginFromDB = resultSet.getString("login");
                languageFromDB = resultSet.getString("language");
//                System.out.println(login + "-" + loginFromDB);
                if (login.equals(loginFromDB)) break;
            }
            return new Translator().getAvailableLanguage(languageFromDB);


        } catch (SQLException e){
            logger.error("Произошла ошибка", e);
        }
        return null;
    }

    public Map<Integer, Product> getData(){
        try {
            Map<Integer, Product> products = new LinkedHashMap<>();
            Integer key = null;
            long id = 999999;
            String name = ".";
            Coordinates coordinates = new Coordinates();
            int coordinatesNumber = -5;
            Long coordinatesX = null;
            Double coordinatesY = null;
            LocalDateTime creationDate = null;
            double price = 99999;
            String partNumber = ".";
            double manufactureCost = 99999;
            UnitOfMeasure unitOfMeasure = null;
            Person owner = new Person();
            String userName = ".";

            ResultSet productsSet = statement.executeQuery("SELECT * FROM products;");

            while (productsSet.next()) {
                key = productsSet.getInt("key");
                id = productsSet.getLong("id");
                name = productsSet.getString("name");
                coordinates = getCoordinates(productsSet.getInt("coordinatesnumber"));
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                creationDate = LocalDateTime.parse(productsSet.getString("creationdate"), formatter);
                price = productsSet.getDouble("price");
                partNumber = productsSet.getString("partnumber");
                manufactureCost = productsSet.getDouble("manufacturecost");
                unitOfMeasure = UnitOfMeasure.valueOf(productsSet.getString("unitofmeasure"));
                owner = getOwner(productsSet.getInt("ownernumber"));
                userName = productsSet.getString("username");
                Product product = new Product(id, name, coordinates, creationDate, price, partNumber, manufactureCost, unitOfMeasure, owner, userName);
                products.put(key, product);
            }
            return products;

        } catch (SQLException e){
            logger.error("Произошла ошибка ", e);
        }
        return new HashMap<>();
    }

    public Coordinates getCoordinates(int coordinatesNumber){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM coordinates WHERE number = ?");
            preparedStatement.setInt(1, coordinatesNumber);
            if (preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()){
                    Long x = resultSet.getLong("x");
                    Double y = resultSet.getDouble("y");
                    Coordinates coordinates = new Coordinates(x, y);
                    return coordinates;
                }
            }
        } catch (SQLException e){
            logger.error("Произошла ошибка ", e);
        }
        return null;
    }

    public void addData(Map<Integer, Product> products){
        for (Integer i : products.keySet()){
            addProduct(i, products.get(i));
        }
    }

    public void addProduct(Integer key, Product product){
        String name = product.getName();
        Coordinates coordinates = product.getCoordinates();
        int coordinatesNumber;
        Long coordinatesX = coordinates.getX();
        Double coordinatesY = coordinates.getY();
        LocalDateTime creationDate = product.getCreationDate();
        double price = product.getPrice();
        String partNumber = product.getPartNumber();
        double manufactureCost = product.getManufactureCost();
        UnitOfMeasure unitOfMeasure = product.getUnitOfMeasure();
        Person owner = product.getOwner();
        String ownerName = null;
        String ownerPassportID = null;
        Color ownerColor = null;
        Location ownerLocation = null;

        if (owner != null) {
            ownerName = product.getOwner().getName();
            ownerPassportID = product.getOwner().getPassportID();
            ownerColor = product.getOwner().getHairColor();
            ownerLocation = product.getOwner().getLocation();
        }
        String userName = product.getUserName();

        try {
            PreparedStatement productsInsert = connection.prepareStatement(
                    "INSERT INTO PRODUCTS (key, name, coordinatesnumber, creationDate, price, partNumber, manufactureCost, unitOfMeasure, ownernumber, username) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
            productsInsert.setInt(1, key);
            productsInsert.setString(2, name);
            coordinatesNumber = addCoordinates(coordinatesX, coordinatesY);
            productsInsert.setInt(3, coordinatesNumber);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.of(creationDate.getYear(), creationDate.getMonth(), creationDate.getDayOfMonth(), creationDate.getHour(), creationDate.getMinute());
            String formattedDateTime = dateTime.format(formatter);
            productsInsert.setString(4, formattedDateTime);

            productsInsert.setDouble(5, price);
            productsInsert.setString(6, partNumber);
            productsInsert.setDouble(7, manufactureCost);
            productsInsert.setString(8, unitOfMeasure.toString());
            if (owner != null) {
                productsInsert.setInt(9, addOwner(ownerName, ownerPassportID, ownerColor, ownerLocation));
            }
            productsInsert.setString(10, userName);
            productsInsert.executeUpdate();
            logger.info("ПРОДУКТ ДОБАВЛЕН");

        } catch (SQLException e){
            logger.error("Произошла ошибка", e);
        }

    }

    public int addCoordinates(Long x, Double y){
        try {
            PreparedStatement coordinatesInsert = connection.prepareStatement(
                    "insert into coordinates (x, y) values (?, ?) returning number");
            coordinatesInsert.setLong(1, x);
            coordinatesInsert.setDouble(2, y);
            if (coordinatesInsert.execute()){
                ResultSet rs = coordinatesInsert.getResultSet();
                if (rs.next()){
                    return rs.getInt("number");
                }
            }


        } catch (SQLException e){
            logger.error("Произошла ошибка ", e);
        }
        return -1;
    }

    public int addLocation(long x, double y, Float z, String name){
        try {
            PreparedStatement locationInsert = connection.prepareStatement(
                    "insert into locations (x, y, z, name) values (?, ?, ?, ?) returning number"
            );
            locationInsert.setLong(1, x);
            locationInsert.setDouble(2, y);
            locationInsert.setFloat(3, z);
            locationInsert.setString(4, name);
            if (locationInsert.execute()){
                ResultSet rs = locationInsert.getResultSet();
                if (rs.next()){
                    return rs.getInt("number");
                }
            }

        } catch (SQLException e){
            logger.error("Произошла ошибка ", e);
        }
        return -1;
    }

    public int addOwner(String name, String passportID, Color hairColor, Location location){
        try {
            PreparedStatement ownerInsert = connection.prepareStatement(
                    "insert into persons(name, passportID, hairColor, locationnumber) values (?, ?, ?, ?) returning number"
            );
            ownerInsert.setString(1, name);
            ownerInsert.setString(2, passportID);
            ownerInsert.setString(3, hairColor.toString());
            ownerInsert.setInt(4, addLocation(location.getX(), location.getY(), location.getZ(), location.getName()));
            if (ownerInsert.execute()){
                ResultSet rs = ownerInsert.getResultSet();
                if (rs.next()){
                    return rs.getInt("number");
                }
            }

        } catch (SQLException e){
            logger.error("Произошла ошибка ", e);
        }
        return -1;
    }

    public Person getOwner(int ownerNumber){
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM persons WHERE number = ?");
            preparedStatement.setInt(1, ownerNumber);
            if (preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()){
                    String name = resultSet.getString("name");
                    String passportID = resultSet.getString("passportid");
                    Color hairColor = Color.valueOf(resultSet.getString("haircolor"));
                    Location location = getLocation(resultSet.getInt("locationnumber"));
                    Person owner = new Person(name, passportID, hairColor, location);
                    return owner;
                }
            }

        } catch (SQLException e){
            logger.error("Произошла ошибка ", e);
        }
        return null;
    }

    public Location getLocation(int locationNumber){
        try{
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM locations WHERE number = ?");
            preparedStatement.setInt(1, locationNumber);
            if (preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()){
                    long x = resultSet.getLong("x");
                    double y = resultSet.getDouble("y");
                    Float z = resultSet.getFloat("z");
                    String name = resultSet.getString("name");
                    Location location = new Location(x, y, z, name);
                    return location;
                }
            }

        } catch (SQLException e){
            logger.error("Произошла ошибка", e);
        }
        return null;
    }

    public List<Long> getIdData(){
        List<Long> list = new ArrayList<>();
        try {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCTS;");

            while (resultSet.next()) {
                list.add(resultSet.getLong("id"));
            }

            return list;
        } catch (SQLException e){
            logger.error("Произошла ошибка ", e);
        }
        return list;
    }

    public List<String> getPassportIdData(){
        List<String> list = new ArrayList<>();
        try {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM PERSONS;");

            while (resultSet.next()) {
                list.add(resultSet.getString("passportid"));
            }

            return list;
        } catch (SQLException e){
            logger.error("Произошла ошибка ", e);
        }
        return list;
    }

    public void removeProduct(Integer key){
        Integer coordinatesNumber = null;
        Integer ownerNumber = null;
        Integer locationNumber = null;

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM products WHERE key = ?");
            preparedStatement.setInt(1, key);
            if (preparedStatement.execute()){
                ResultSet resultSet = preparedStatement.getResultSet();
                if (resultSet.next()){
                    ownerNumber = resultSet.getInt("ownernumber");
                    coordinatesNumber = resultSet.getInt("coordinatesnumber");

                }
            }

            if (ownerNumber != null){
                preparedStatement = connection.prepareStatement("SELECT * FROM persons WHERE number = ?");
                preparedStatement.setInt(1, ownerNumber);
                if (preparedStatement.execute()){
                    ResultSet resultSet = preparedStatement.getResultSet();
                    if (resultSet.next()){
                        locationNumber = resultSet.getInt("locationnumber");
                    }
                }
            }

            preparedStatement = connection.prepareStatement("DELETE FROM products WHERE key = ?");
            preparedStatement.setInt(1, key);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("DELETE FROM persons WHERE number = ?");
            preparedStatement.setInt(1, ownerNumber);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("DELETE FROM locations WHERE number = ?");
            preparedStatement.setInt(1, locationNumber);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("DELETE FROM coordinates WHERE number = ?");
            preparedStatement.setInt(1, coordinatesNumber);
            preparedStatement.execute();





        } catch (SQLException e){
            logger.error("Произошла ошибка ", e);
        }
    }

    public void updateProduct(Integer key, Product product){
        removeProduct(key);
        addProduct(key, product);
    }

    public void removeLower(Product product, String login){
        for (Integer i : getData().keySet()){
            if (product.compareTo(getData().get(i)) > 0 && getData().get(i).getUserName().equals(login)){
                removeProduct(i);
            }
        }

    }

    public boolean replaceIfGreater(Integer key, Product p1){
        Product p2 = getData().get(key);
        if (p1.compareTo(p2) > 0) {
            updateProduct(key, p1);
            return true;
        } else {
            return false;
        }
    }

    public boolean replaceIfLower(Integer key, Product p1){
        Product p2 = getData().get(key);
        if (p1.compareTo(p2) < 0) {
            updateProduct(key, p1);
            return true;
        } else {
            return false;
        }
    }

}
