package askers;

import data.Color;
import data.UnitOfMeasure;
import scanners.MyScanner;

import java.util.Scanner;

/**
 * Интерфейс, имеющий методы, которые будут запрашивать ввод полей
 */
public interface ClientDataAsker {

    String askLogin();

    String askPassword();

    String askKey();

    String askId();

    String askName();

    String askCoordinatesX();

    String askCoordinatesY();

    String askPrice();

    String askPartNumber();

    String askManufactureCost();

    String askUnitOfMeasure();

    String askOwnerName();

    String askOwnerPassportID();

    String askOwnerHairColor();

    String askOwnerLocationX();

    String askOwnerLocationY();

    String askOwnerLocationZ();

    String askOwnerLocationName();

    void setScanner(MyScanner scanner);

    MyScanner getScanner();

    void setScriptMode(boolean scriptMode);

    boolean isScriptMode();
}
