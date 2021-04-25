package pattern;

import data.Product;
import exception.InputOutputException;
import checker.ServerDataChecker;

import java.util.Map;

public interface Loader {
    void save(Map<Integer, Product> collection) throws InputOutputException;

    Map<Integer, Product> load(ServerDataChecker checker);


}
