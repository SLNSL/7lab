package messenger;

public class MessengerRum extends AbstractMessenger {

    public MessengerRum() {
        setCommandsInfo();
    }

    @Override
    public void setCommandsInfo() {
        commandsInfo.put("help", "afișează ajutor pentru comenzile disponibile");
        commandsInfo.put("info", "tipăriți informații despre colecție (tip, data inițializării, număr de elemente etc.) în fluxul de ieșire standard");
        commandsInfo.put("show", "tipăriți toate elementele colecției în reprezentarea șirului la ieșirea standard");
        commandsInfo.put("insert", "adăugați un element nou cu cheia dată");
        commandsInfo.put("update id", "actualizați valoarea articolului de colecție al cărui id este egal cu cel dat");
        commandsInfo.put("remove_key null", "eliminați un element din colecție prin cheia sa");
        commandsInfo.put("clear", "clear collection");
        commandsInfo.put("execute_script file_name", "citiți și executați scriptul din fișierul specificat. Scriptul conține comenzi în aceeași formă în care utilizatorul le introduce în mod interactiv.");
        commandsInfo.put("exit", "terminați programul (fără a salva în fișier)");
        commandsInfo.put("remove_lower", "eliminați toate elementele din colecție care sunt mai mici decât cel specificat");
        commandsInfo.put("replace_if_greater null", "înlocuiește valoarea cu cheia dacă noua valoare este mai mare decât cea veche");
        commandsInfo.put("replace_if_lower null", "înlocuiește valoarea cu cheia dacă noua valoare este mai mică decât cea veche");
        commandsInfo.put("min_by_unit_of_measure", "afișează orice obiect din colecție cu unitOfMeasure minim");
        commandsInfo.put("max_by_unit_of_measure", "afișează orice obiect din colecție cu unitatea maximăMăsură");
        commandsInfo.put("count_less_than_owner", "tipăriți numărul de articole al căror câmp proprietar este mai mic decât valoarea specificată");
        commandsInfo.put("sign_up", "create new account");
        commandsInfo.put("sign_in", "conectare la un cont existent");

    }

    @Override
    public String getCommandsInfo() {
        return super.getCommandsInfo();
    }

    @Override
    public String getInfo(Object collectionType, Object elementsType, Object initTime, Object number) {
        return "Tipul colecției:" + collectionType.toString() + "\n" + "Tipul elementelor din colecție:" + elementsType.toString() + "\n" + "Data inițializării:" + initTime + "\n" +
                "Numărul de articole din colecție:" + number.toString() + "\n";
    }

    @Override
    public String generateElementDoesntExistMessage() {
        return "Obiectul nu există!";
    }

    @Override
    public String generateEmptyCollectionMessage() {
        return "Colecția este goală.";
    }

    @Override
    public String generateEmptyFileMessage() {
        return "Fișierul este gol.";
    }

    @Override
    public String generateErrorInScriptMessage() {
        return "A apărut o eroare la executarea scriptului.";
    }

    @Override
    public String generateFileWasNotFoundMessage() {
        return "Fișierul nu a fost găsit.";
    }


    @Override
    public String generateIncorrectNumberOfArgumentsMessage(String command, int numberOfArguments) {
        return "Eroare! Comandă" + command + "trebuie să accepte" + numberOfArguments + "argument (e)";
    }

    @Override
    public String generateIncorrectFieldInputMessage(String field, String... reguli) {
        String message = "Eroare! Câmpul" + field + "a fost introdus incorect.";
        message += "Câmp";
        for (String rule : reguli) {
            message += rule + ",";
        }
        message = message.substring(0, message.length() - 2) + ".";
        return message;
    }

    @Override
    public String generateInputOutputMessage() {
        return "Eroare! Fișierul de încărcare este un director sau nu poate fi deschis!";
    }

    @Override
    public String generateNoLineFoundMessage() {
        return "Eroare! Nu a fost găsită nicio intrare de utilizator.";
    }

    @Override
    public String generateSecurityExceptionMessage() {
        return "Eroare! Permisiuni de citire / scriere insuficiente.";
    }

    @Override
    public String generateServerUnavailable() {
        return "Eroare! Serverul nu este disponibil.";
    }

    @Override
    public String generateJsonSyntaxMessage() {
        return "Eroare de sintaxă Json!";
    }

    @Override
    public String generateUnexpectedErrorMessage() {
        return "Eroare neașteptată!";
    }

    @Override
    public String generateUnknownCommandMessage() {
        return "Comandă necunoscută." + typeHelp();
    }

    @Override
    public String generateScriptRecursionMessage() {
        return "Atenție! A fost suprimată o eroare în timpul executării scriptului.";
    }

    @Override
    public String generateIncorrectFieldInDataMessage() {
        return "Eroare! Câmpurile din fișier nu sunt în formatul corect.";
    }

    @Override
    public String cantBeEmpty() {
        return cantBe("linie goală");
    }

    @Override
    public String cantBe(Object object) {
        return "nu poate fi" + object.toString();
    }

    @Override
    public String mustBe(Object object) {
        return "trebuie să fie" + object.toString();
    }

    @Override
    public String moreThan(Object object) {
        return "mai mare decât" + object.toString();
    }

    @Override
    public String lessThan(Object object) {
        return "mai puțin decât" + object.toString();
    }

    @Override
    public String mustBeUnique() {
        return mustBe("unic");
    }

    @Override
    public String mustBeType(String type) {
        return mustBe("tip" + type);
    }

    @Override
    public String scriptIsFinished(String path) {
        return "Scriptul" + path + "finalizat.";
    }

    @Override
    public String commandIsFinished(String command) {
        return "Comanda" + command + "a fost executată.";
    }

    @Override
    public String collectionIsLoaded() {
        return "Colecția încărcată.";
    }

    @Override
    public String collectionIsSaved() {
        return "Colecția a fost salvată.";
    }

    @Override
    public String typeHelp() {
        return "Introduceți ajutor pentru a vedea o listă de comenzi disponibile.";
    }

    @Override
    public String lessThanOwner(long i) {
        return "Există mai puține elemente decât cele introduse:" + i;
    }

    @Override
    public String elementReplaced(boolean b) {
        if (b) {
            return "Articolul a fost înlocuit.";
        }
        return "Articolul nu a fost înlocuit.";
    }

    @Override
    public String signInOrUp() {
        return "Dacă aveți deja un cont, introduceți - sig_in \n" +
                "Dacă doriți să vă înregistrați, introduceți - sign_up";
    }

    @Override
    public String typeLogin() {
        return "Introduceți autentificarea:";
    }

    @Override
    public String typePassword() {
        return "Introduceți parola:";
    }

    @Override
    public String incorrectData() {
        return "Ai introdus date incorecte";
    }

    @Override
    public String loginIsExist() {
        return "Această autentificare există deja";
    }

    @Override
    public String incorrectLogin() {
        return "Numele de utilizator introdus nu există";
    }

    @Override
    public String incorrectPassword() {
        return "Parolă nevalidă introdusă";
    }

    @Override
    public String languageHasBeenInstalled() {
        return "Limba a fost instalată";
    }

    @Override
    public String youDontHaveRights() {
        return "Nu ai suficiente drepturi pentru a executa această comandă";
    }

    @Override
    public String sayHello(String person) {
        return "Bună ziua " + person + "!";
    }


    @Override
    public String authorizationWord() {
        return "Autorizație";
    }

    @Override
    public String signInWord() {
        return "Autentificare";
    }

    @Override
    public String signUpWord() {
        return "Înregistrează";
    }

    @Override
    public String enterWord() {
        return "Intrare";
    }


    @Override
    public String changeWord() {
        return "Schimbare";
    }

    @Override
    public String deleteWord() {
        return "Șterge";
    }

    @Override
    public String doYouWantToChange() {
        return "Vrei să schimbi valoarea celulei?";
    }

    @Override
    public String currentUserWord() {
        return "Utilizator curent: ";
    }

    @Override
    public String changeLanguageWord() {
        return "Schimbă limba";
    }

    @Override
    public String onFilterWord() {
        return "activează filtrarea";
    }

    @Override
    public String offFilterWord() {
        return "opriți filtrarea";
    }


    @Override
    public String getString() {
        return "rum";
    }

    @Override
    public String askLogin() {
        return "Introduceți autentificarea";
    }

    @Override
    public String askPassword() {
        return "Introduceți parola";
    }

    @Override
    public String askKey() {
        return "Introduceți cheia:";
    }

    @Override
    public String askID() {
        return "Introduceți ID-ul elementului pe care doriți să îl actualizați:";
    }

    @Override
    public String askName() {
        return "Introduceți câmpul de nume:";
    }

    @Override
    public String askCoordinates() {
        return "Introduceți coordonatele:";
    }

    @Override
    public String askCoordinatesX() {
        return "Introduceți coordonatele câmpului.x:";
    }

    @Override
    public String askCoordinatesY() {
        return "Introduceți coordonatele câmpului.y;";
    }

    @Override
    public String askPrice() {
        return "Introduceți câmpul preț:";
    }

    @Override
    public String askPartNumber() {
        return "Introduceți câmpul partNumber:";
    }

    @Override
    public String askManufactureCost() {
        return "Introduceți câmpul ManufactureCost:";
    }

    @Override
    public String askUnitOfMeasure() {
        return "Introduceți câmpul unitDeMăsură {METERS, CENTIMETERS, SQUARE_METERS, LITERS, MILLIGRAMS}:";
    }

    @Override
    public String askOwner() {
        return "Introduceți câmpul proprietarului:";
    }

    @Override
    public String askOwnerName() {
        return "Introduceți câmpul proprietarului.name:";
    }

    @Override
    public String askOwnerPassportID() {
        return "Introduceți proprietarul.passportID:";
    }

    @Override
    public String askOwnerHairColor() {
        return "Introduceți proprietarul câmpului.hairColor {GREEN, RED, BLACK, WHITE, BROWN}:";
    }

    @Override
    public String askOwnerLocation() {

        return "Introduceți câmpul proprietarului.location:";
    }

    @Override
    public String askOwnerLocationX() {
        return "Introduceți câmpul owner.location.x:";
    }

    @Override
    public String askOwnerLocationY() {
        return "Introduceți câmpul proprietarului.location.y:";
    }

    @Override
    public String askOwnerLocationZ() {
        return "Introduceți câmpul owner.location.z:";
    }

    @Override
    public String askOwnerLocationName() {
        return "Introduceți câmpul proprietarului.location.name:";
    }
}