package messenger;

public class MessengerKat extends AbstractMessenger{

    public MessengerKat() {
        setCommandsInfo ();
    }

    @Override
    public void setCommandsInfo () {
        commandsInfo.put ("help", "mostra ajuda per a les ordres disponibles");
        commandsInfo.put ("info", "imprimeix informació sobre la col·lecció (tipus, data d'inicialització, nombre d'elements, etc.) al flux de sortida estàndard");
        commandsInfo.put ("show", "imprimir tots els elements de la col·lecció en representació de cadenes a la sortida estàndard");
        commandsInfo.put ("insert", "afegir un element nou amb la clau donada");
        commandsInfo.put ("update id", "actualitza el valor de l'element de col·lecció l'identificador de la qual és igual a la donada");
        commandsInfo.put ("remove_key null", "elimina un element de la col·lecció amb la seva clau");
        commandsInfo.put ("clear", "esborrar col·lecció");
        commandsInfo.put ("execute_script file_name", "llegeix i executa l'script des del fitxer especificat. L'escriptura conté ordres en la mateixa forma en què l'usuari les introdueix de forma interactiva.");
        commandsInfo.put ("exit", "finalitzar el programa (sense desar al fitxer)");
        commandsInfo.put ("remove_lower", "elimina tots els elements de la col·lecció que siguin inferiors a l'especificat");
        commandsInfo.put ("replace_if_greater null", "substitueix el valor per clau si el valor nou és superior a l'antic");
        commandsInfo.put ("replace_if_lower null", "substitueix el valor per clau si el valor nou és inferior a l'antic");
        commandsInfo.put ("min_by_unit_of_measure", "mostra qualsevol Object de la col·lecció amb la unitat mínimaOfMeasure");
        commandsInfo.put ("max_by_unit_of_measure", "mostra qualsevol Object de la col·lecció amb la màxima unitOfMeasure");
        commandsInfo.put ("count_less_than_owner", "imprimeix el nombre d'elements el camp del qual és inferior al valor especificat");
        commandsInfo.put ("sign_up", "crear un compte nou");
        commandsInfo.put ("sign_in", "iniciar sessió en un compte existent");

    }

    @Override
    public String getCommandsInfo () {
        return super.getCommandsInfo ();
    }

    @Override
    public String getInfo (Object collectionType, Object elementsType, Object initTime, Object number) {
        return "Tipus de col·lecció:" + collectionType.toString () + "\n" + "Tipus d'elements de la col·lecció:" + elementsType.toString () + "\n" + "Data d'inicialització:" + initTime + "\n "+
                "El nombre d'elements de la col·lecció:" + number.toString () + "\n";
    }

    @Override
    public String generateElementDoesntExistMessage () {
        return "L'Object no existeix!";
    }

    @Override
    public String generateEmptyCollectionMessage () {
        return "La col·lecció està buida.";
    }

    @Override
    public String generateEmptyFileMessage () {
        return "El fitxer està buit.";
    }

    @Override
    public String generateErrorInScriptMessage () {
        return "S'ha produït un error en executar l'script.";
    }

    @Override
    public String generateFileWasNotFoundMessage () {
        return "No s'ha trobat el fitxer.";
    }


    @Override
    public String generateIncorrectNumberOfArgumentsMessage (String command, int numberOfArguments) {
        return "Error! Ordre" + command + "ha d'acceptar" + numberOfArguments + "argument (s)";
    }

    @Override
    public String generateIncorrectFieldInputMessage (String field, String... rules) {
        String message = "Error! El camp" + field + "s'ha introduït incorrectament.";
        message += "Camp";
        for (String rule : rules) {
            message += rule + ",";
        }
        message = message.substring (0, message.length () - 2) + ".";
        return message;
    }

    @Override
    public String generateInputOutputMessage () {
        return "Error! El fitxer d'arrencada és un directori o no es pot obrir!";
    }

    @Override
    public String generateNoLineFoundMessage () {
        return "Error! No s'ha trobat cap entrada de l'usuari.";
    }

    @Override
    public String generateSecurityExceptionMessage () {
        return "Error! Permisos de lectura / escriptura insuficients.";
    }

    @Override
    public String generateServerUnavailable () {
        return "Error! El servidor no està disponible.";
    }

    @Override
    public String generateJsonSyntaxMessage () {
        return "Error de sintaxi Json!";
    }

    @Override
    public String generateUnexpectedErrorMessage () {
        return "Error inesperat!";
    }

    @Override
    public String generateUnknownCommandMessage () {
        return "Comanda desconeguda" + typeHelp ();
    }

    @Override
    public String generateScriptRecursionMessage () {
        return "Advertència! S'ha suprimit un error durant l'execució de l'script.";
    }

    @Override
    public String generateIncorrectFieldInDataMessage () {
        return "Error! Els camps del fitxer no tenen el format correcte.";
    }

    @Override
    public String cantBeEmpty () {
        return cantBe ("línia buida");
    }

    @Override
    public String cantBe (Object object) {
        return "no pot ser " + object.toString ();
    }

    @Override
    public String mustBe (Object object) {
        return "ha de ser " + object.toString ();
    }

    @Override
    public String moreThan (Object object) {
        return "més gran que " + object.toString();
    }

    @Override
    public String lessThan (Object object) {
        return "less than " + object.toString ();
    }

    @Override
    public String mustBeUnique () {
        return mustBe("únic");
    }

    @Override
    public String mustBeType (String type) {
        return mustBe("tipus " + type);
    }

    @Override
    public String scriptIsFinished (String path) {
        return "L'escriptura " + path + " completat.";
    }

    @Override
    public String commandIsFinished (String command) {
        return "S'ha executat l'ordre " + command + ".";
    }

    @Override
    public String collectionIsLoaded () {
        return "Col·lecció carregada.";
    }

    @Override
    public String collectionIsSaved () {
        return "La col·lecció s'ha desat.";
    }

    @Override
    public String typeHelp () {
        return "Introduïu ajuda per veure una llista d'ordres disponibles.";
    }

    @Override
    public String lessThanOwner (long i) {
        return "Hi ha menys elements dels introduïts:" + i;
    }

    @Override
    public String elementReplaced (boolean b) {
        if (b) {
            return "L'element s'ha substituït.";
        }
        return "L'element no s'ha substituït.";
    }

    @Override
    public String signInOrUp () {
        return "Si ja teniu un compte, introduïu - sig_in \n" +
                "Si voleu registrar-vos, introduïu - sign_up";
    }

    @Override
    public String typeLogin () {
        return "Introduïu l'inici de sessió:";
    }

    @Override
    public String typePassword () {
        return "Introduïu la contrasenya:";
    }

    @Override
    public String incorrectData () {
        return "Heu introduït dades incorrectes";
    }

    @Override
    public String loginIsExist () {
        return "Aquest accés ja existeix";
    }

    @Override
    public String incorrectLogin () {
        return "El nom d'usuari introduït no existeix";
    }

    @Override
    public String incorrectPassword () {
        return "S'ha introduït una contrasenya no vàlida";
    }

    @Override
    public String languageHasBeenInstalled () {
        return "L'idioma s'ha instal·lat";
    }

    @Override
    public String youDontHaveRights () {
        return "No teniu els drets suficients per executar aquesta ordre";
    }

    @Override
    public String sayHello (String person) {
        return "Hola, " + person + "!";
    }



    @Override
    public String authorizationWord() {
        return "Autorització";
    }

    @Override
    public String signInWord () {
        return "Inici de sessió";
    }

    @Override
    public String signUpWord () {
        return "Registra't";
    }

    @Override
    public String enterWord () {
        return "Entrada";
    }


    @Override
    public String changeWord () {
        return "Canvia";
    }

    @Override
    public String deleteWord () {
        return "Suprimeix";
    }

    @Override
    public String doYouWantToChange () {
        return "Voleu canviar el valor de la cel·la?";
    }

    @Override
    public String currentUserWord () {
        return "Usuari actual:";
    }

    @Override
    public String changeLanguageWord () {
        return "Canvia l'idioma";
    }

    @Override
    public String onFilterWord() {
        return "habilita el filtratge";
    }

    @Override
    public String offFilterWord() {
        return "desactivar el filtratge";
    }

    @Override
    public String getString () {
        return "kat";
    }

    @Override
    public String askLogin () {
        return "Introduïu l'inici de sessió";
    }

    @Override
    public String askPassword () {
        return "Introduïu la contrasenya";
    }

    @Override
    public String askKey () {
        return "Introduïu la clau:";
    }

    @Override
    public String askID () {
        return "Introduïu l'identificador de l'element que voleu actualitzar:";
    }

    @Override
    public String askName () {
        return "Introduïu el camp del nom:";
    }

    @Override
    public String askCoordinates () {
        return "Introduïu les coordenades:";
    }

    @Override
    public String askCoordinatesX () {
        return "Introduïu coordenades de camp.x:";
    }

    @Override
    public String askCoordinatesY () {
        return "Introduïu les coordenades del camp:";
    }

    @Override
    public String askPrice () {
        return "Introduïu el camp de preus:";
    }

    @Override
    public String askPartNumber () {
        return "Introduïu el camp partNumber:";
    }

    @Override
    public String askManufactureCost () {
        return "Introduïu el camp ManufacturingCost:";
    }

    @Override
    public String askUnitOfMeasure () {
        return "Introduïu la unitat de campDeMesura {METERS, CENTIMETERS, SQUARE_METERS, LITERS, MILLIGRAMS}:";
    }

    @Override
    public String askOwner () {
        return "Introduïu el camp del propietari:";
    }

    @Override
    public String askOwnerName () {
        return "Introduïu el camp propietari.nom:";
    }

    @Override
    public String askOwnerPassportID () {
        return "Introduïu propietari.passaportID:";
    }

    @Override
    public String askOwnerHairColor () {
        return "Introduïu el propietari del camp.hairColor {GREEN, RED, BLACK, WHITE, BROWN}:";
    }

    @Override
    public String askOwnerLocation () {
        return "Introduïu el camp propietari.ubicació:";
    }

    @Override
    public String askOwnerLocationX () {
        return "Introduïu el camp owner.location.x:";
    }

    @Override
    public String askOwnerLocationY () {
        return "Introduïu el camp owner.location.y:";
    }

    @Override
    public String askOwnerLocationZ () {
        return "Introduïu el camp owner.location.z:";
    }

    @Override
    public String askOwnerLocationName () {
        return "Introduïu el camp owner.location.name:";
    }
}
