package Business;

import Persistance.*;
import Presentation.PresentationController;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * La clase {@code BusinessController} representa la capa de negocio del programa elCofre Digital Shopping Experiences.
 * Gestiona la lógica de negocio relacionada con productos, tiendas, catálogos, valoraciones y el carrito de compras.
 */
public class BusinessController {

    private final Scanner scanner = new Scanner(System.in);

    private ProductsDAO tipusP;

    private ShopsDAO tipusS;


    public boolean checkApi(){return API.checkApi();}

    /**
     * Controlador de presentación.
     */
    private final PresentationController presentationController = new PresentationController();

    /**
     * Lista de productos en el carrito.
     */
    private final JsonArray productesCarret = new JsonArray();

    /**
     * Maneja errores de entrada de tipo entero.
     *
     * @return Entero ingresado por el usuario.
     */
    public int errorInt(){
        int opt = 0;
        try {
            opt = scanner.nextInt();
            scanner.nextLine();
            return opt;
        }catch (InputMismatchException e){
            presentationController.getVista().errorInt();
            scanner.nextLine();
            return 0;
        }
    }

    /**
     * Inicia la ejecución del programa.
     */
    public void run(){
        presentationController.getVista().missatgePresentacio();
        boolean ok = checkApi();
        if(ok){
            presentationController.getVista().start();
            tipusP = new ProductsApi();
            tipusS = new ShopsApi();
            startProgram();
        }else{
            presentationController.getVista().errorApi();
            String ruta = "src/Arxius/products.json";
            File arxiu = new File(ruta);
            if(!arxiu.exists()){
                presentationController.getVista().errorPersistencia();
            }else{
                presentationController.getVista().start();
                tipusS = new ShopsJson();
                tipusP = new ProductsJson();
                startProgram();
            }
        }

    }

    /**
     * Inicia el programa y maneja el menú principal.
     */
    private void startProgram(){
        boolean run = true;
        while(run) {
            presentationController.getVista().menu();
            int opt = 0;
            opt = errorInt();
            switch (opt) {
                case 1 -> gestioProductes();
                case 2 -> gestioDeTendes();
                case 3 -> cercaDeProductes();
                case 4 -> llistarTendes();
                case 5 -> gestioCarret();
                case 6 -> {
                    presentationController.getVista().exit();
                    run = false;
                }
                default -> presentationController.getVista().opcioNoValida();
            }
        }
    }

    /**
     * Maneja las operaciones relacionadas con productos.
     */
    private void gestioProductes(){
        presentationController.getVista().menu1();
        int opt = errorInt();
        switch (opt){
            case 1 -> creacioProducte();
            case 2 -> eliminacioProductes();
            case 3 -> {}
            default -> presentationController.getVista().opcioNoValida();
        }
    }

    /**
     * Crea un nuevo producto.
     */
    private void creacioProducte(){

        presentationController.getVista().productName();
        String nom = scanner.nextLine();
        boolean existeix = tipusP.comprovarExistencia(nom);
        if(existeix){
            presentationController.getVista().errorNom();
        }else{
            presentationController.getVista().productBrand();
            String marca = scanner.nextLine();
            String marcamod = ModificarMarca(marca);

            presentationController.getVista().productPrice();
            float preuMax = 0;
            try {
                preuMax = scanner.nextFloat();
                scanner.nextLine();
            }catch (InputMismatchException e){
                presentationController.getVista().errorInt();
                scanner.nextLine();
                preuMax = -1;
            }

            if(preuMax < 0){
                presentationController.getVista().errorPreu();
            }else{
                char categoria;
                do{
                    presentationController.getVista().productCategories();
                    String categoriaStr = scanner.next();
                    categoria = categoriaStr.charAt(0);
                }while(categoria != 'A' && categoria != 'B' && categoria != 'C');
                String nomCat = null;
                if(categoria == 'A'){
                    nomCat = "General";
                }else if(categoria == 'B'){
                    nomCat = "Reduced Taxes";
                }else if(categoria == 'C'){
                    nomCat = "Superreduced Taxes";
                }
                JsonArray valoracions = new JsonArray();
                Producte producte = new Producte(nom, marcamod, preuMax, nomCat, valoracions);
                existeix = tipusP.afegirProducte(producte);

                if (existeix){
                    presentationController.getVista().guardat(nom, marcamod);
                }else{
                    presentationController.getVista().errorGuardar();
                }
            }

        }
    }

    /**
     * Elimina productos del catálogo y solicita confirmación al usuario antes de realizar la operación.
     * @throws InputMismatchException si la entrada no es un número.
     */
    private void eliminacioProductes(){
        presentationController.getVista().productesDisponibles();
        JsonArray productes;
        JsonObject product;
        String marca;
        String nom;
        productes = tipusP.llistarProductes();
        int i = 0;
        for (i = 0; i < productes.size(); i++) {
            product = productes.get(i).getAsJsonObject();
            nom = product.get("name").getAsString();
            marca = product.get("brand").getAsString();
            presentationController.getVista().productesLlista(i, nom, marca);
        }
        presentationController.getVista().enrere(i + 1);
        presentationController.getVista().elegirProducte();
        int opt = errorInt() - 1;
        if (opt >= 0 && opt < productes.size()) {
            product = productes.get(opt).getAsJsonObject();
            nom = product.get("name").getAsString();
            marca = product.get("brand").getAsString();
            presentationController.getVista().confirmarborrat(nom, marca);
            String confirmar = scanner.nextLine();
            if (confirmar.equalsIgnoreCase("yes")) {
                tipusP.eliminarProducte(opt);
                presentationController.getVista().borratconfirmat(nom, marca);
            } else if (confirmar.equalsIgnoreCase("no")) {
                gestioProductes();
            }
        }
    }

    /**
     * Modifica la marca de un producto para que la primera letra sea mayúscula y el resto minúsculas.
     * @param marca La marca original del producto.
     * @return La marca modificada.
     */
    private String ModificarMarca(String marca){
        if(marca == null || marca.trim().isEmpty()){
            return marca;
        }else{
            String[] paraules = marca.split(" ");

            StringBuilder resultat = new StringBuilder();

            for (String paraula : paraules) {
                if (!paraula.isEmpty()) {
                    String novaParaula = paraula.toLowerCase();
                    novaParaula = Character.toUpperCase(novaParaula.charAt(0)) + novaParaula.substring(1);

                    resultat.append(novaParaula).append(" ");
                }
            }
            return resultat.toString().trim();
        }
    }

    /**
     * Gestiona las opciones relacionadas con las tiendas, como crear, expandir o reducir el catálogo de una tienda.
     * @throws InputMismatchException si la entrada no es un número.
     */
    private void gestioDeTendes(){
        presentationController.getVista().menuTenda();
        int opcio = errorInt();;
        switch (opcio){
            case 1 -> creacioTenda();
            case 2 -> expancioCataleg();
            case 3 -> reduccioCataleg();
            case 4 -> {}
            default -> presentationController.getVista().opcioNoValida();
        }
    }

    /**
     * Crea una nueva tienda solicitando la información necesaria al usuario.
     * @throws InputMismatchException si la entrada no es un número.
     */
    private void creacioTenda(){
        presentationController.getVista().nomTenda();
        String nom = scanner.nextLine();
        boolean existeix = tipusS.comprovarExistenciaTenda(nom);
        if(existeix){
            presentationController.getVista().errorNomTenda();
        }else if(nom.isEmpty()){
            presentationController.getVista().errorNomTenda1();
        }else{
            presentationController.getVista().descripcioTenda();
            String descripcio = scanner.nextLine();

            presentationController.getVista().anyFundacio();
            int anyF = errorInt();

            if(anyF <= 0){
                presentationController.getVista().errorAny();
            }else{
                char model;
                do{
                    presentationController.getVista().businessModel();
                    String modelStr = scanner.next();
                    model = modelStr.charAt(0);
                }while(model != 'A' && model != 'B' && model != 'C');
                String nomModel = null;
                if(model == 'A'){
                    nomModel = "MAX_PROFIT";
                }else if(model == 'B'){
                    nomModel = "LOYALTY";
                }else if(model == 'C'){
                    nomModel = "SPONSORED";
                }
                JsonArray catalegVuid = new JsonArray();
                Tenda tenda = new Tenda(nom, descripcio, anyF, 0, nomModel, catalegVuid);
                existeix = tipusS.afegirTenda(tenda);

                if (existeix){
                    presentationController.getVista().tendaConfirmada(nom);
                }else{
                    presentationController.getVista().errorGuardarTenda();
                }
            }

        }
    }

    /**
     * Expande el catálogo de una tienda solicitando información sobre el producto a agregar.
     * @throws InputMismatchException si la entrada no es un número.
     */
    private void expancioCataleg(){
        presentationController.getVista().nomTenda();
        String nomT = scanner.nextLine();
        boolean existeix = tipusS.comprovarExistenciaTenda(nomT);
        if(!existeix){
            presentationController.getVista().shopExist();
        }else{
            presentationController.getVista().productName();
            String nomP = scanner.nextLine();
            existeix = tipusP.comprovarExistencia(nomP);
            if(!existeix){
                presentationController.getVista().errorNom1();
            }else{
                presentationController.getVista().preuProducte();
                float preu = scanner.nextFloat();
                float preuMax = tipusP.comprovarPreu(nomP);
                if(preu >preuMax){
                    presentationController.getVista().errorPreumax();
                }else{
                    existeix = tipusS.consistenciaPreu(nomT, nomP, preu);
                    if(!existeix){
                        presentationController.getVista().errorPreuprod();
                    }else{
                        String marca = tipusP.obtenirMarca(nomP);
                        ProducteCataleg producteCataleg = new ProducteCataleg(nomP, marca, preu);
                        tipusS.actualitzarCataleg(nomT, producteCataleg);
                        presentationController.getVista().catalegActualitzat(nomP, marca, nomT);
                    }
                }
            }
        }
    }

    /**
     * Reduce el catálogo de una tienda eliminando productos y solicita confirmación al usuario.
     * @throws InputMismatchException si la entrada no es un número.
     */
    private void reduccioCataleg(){
        presentationController.getVista().nomTenda();
        String nomT = scanner.nextLine();
        boolean existeix = tipusS.comprovarExistenciaTenda(nomT);
        if(!existeix){
            presentationController.getVista().shopExist();
        } else {
            presentationController.getVista().productesCataleg();
            JsonArray llistaCataleg = tipusS.productesCataleg(nomT);
            for(int i = 0; i < llistaCataleg.size(); i++){
                JsonObject cataleg = llistaCataleg.get(i).getAsJsonObject();
                String nom = cataleg.get("nom").getAsString();
                String marca = cataleg.get("marca").getAsString();
                presentationController.getVista().productesLlista(i + 1, nom, marca); // Mostrar índices empezando por 1 para el usuario
            }
            presentationController.getVista().enrere(llistaCataleg.size() + 1);
            presentationController.getVista().elegirProducte();
            int opt = errorInt() - 1; // Asume que errorInt() asegura un número válido

            // Comprobar si opt está en el rango correcto
            if(opt >= 0 && opt < llistaCataleg.size()){
                JsonObject cataleg = llistaCataleg.get(opt).getAsJsonObject();
                String nom = cataleg.get("nom").getAsString();
                String marca = cataleg.get("marca").getAsString();

                // Aquí asumimos que eliminarProdCataleg maneja correctamente la eliminación
                tipusS.eliminarProdCataleg(opt, nomT);
                presentationController.getVista().borratCatConf(nom, marca, nomT);
            } else {
                // Manejar selección fuera de rango o regresar a gestión de tiendas
                gestioDeTendes();
            }
        }
    }

    /**
     * Realiza una búsqueda de productos por nombre y muestra información detallada, permitiendo al usuario
     * ver valoraciones, agregar nuevas valoraciones o añadir productos al carrito.
     * @throws InputMismatchException si la entrada no es un número.
     */
    private void cercaDeProductes(){
        presentationController.getVista().query();
        String query = scanner.nextLine();
        JsonArray llistaResultats = tipusP.llistaDeResultats(query);
        if(llistaResultats.size() == 0){
            presentationController.getVista().coincidecncies();
        }else{
            int i = 0;
            for (i = 0; i < llistaResultats.size(); i++){
                JsonObject producte = llistaResultats.get(i).getAsJsonObject().getAsJsonObject();
                String nomProducte = producte.get("name").getAsString();
                String nomMarca = producte.get("brand").getAsString();
                JsonArray productesTenda = tipusS.productesTenda(nomProducte);

                presentationController.getVista().productesLlista(i, nomProducte, nomMarca);
                if(productesTenda.size() == 0){
                    presentationController.getVista().noHiHaProductes();
                }else{
                    presentationController.getVista().soldAt();
                    for(int j = 0; j < productesTenda.size(); j++){
                        JsonObject producteTenda = productesTenda.get(j).getAsJsonObject();
                        String nomTenda = producteTenda.get("nomTenda").getAsString();
                        float preuProducte = producteTenda.get("preu").getAsFloat();
                        presentationController.getVista().producteTenda(nomTenda, preuProducte);
                    }

                }
            }
            presentationController.getVista().enrere(i + 1);
            presentationController.getVista().revisar();
            int opt = errorInt();
            if(opt > i || opt <= 0){

            }else{
                JsonObject producteEscollit = llistaResultats.get(opt - 1).getAsJsonObject().getAsJsonObject();
                String nomProducte = producteEscollit.get("name").getAsString();
                String marca = producteEscollit.get("brand").getAsString();
                presentationController.getVista().menuResultats();
                opt = errorInt();
                switch (opt){
                    case 1 -> veureValoracions(nomProducte, marca);
                    case 2 -> afegirValoracio(nomProducte, marca);
                }
            }
        }
    }

    /**
     * Muestra las valoraciones de un producto, calcula la media de las estrellas y muestra la información al usuario.
     * @param nomProducte Nombre del producto.
     * @param marca Marca del producto.
     */
    private void veureValoracions(String nomProducte, String marca){
        JsonArray llistaValoracions = new JsonArray();
        llistaValoracions = tipusP.llistarValoracions(nomProducte);
        if(llistaValoracions != null){
            int sumaEstrelles = 0;
            presentationController.getVista().reviews(nomProducte, marca);
            for (int i = 0; i < llistaValoracions.size(); i++){
                JsonObject valoracio = llistaValoracions.get(i).getAsJsonObject();
                int estrelles = valoracio.get("estrelles").getAsInt();
                String comentari = valoracio.get("comentari").getAsString();
                presentationController.getVista().llistarValoracio(estrelles, comentari);
                sumaEstrelles += estrelles;
            }
            float mitjana = (float) sumaEstrelles /llistaValoracions.size();
            presentationController.getVista().mostrarMirjana(mitjana);
        }else{
            presentationController.getVista().noReview();
        }
    }

    /**
     * Permite al usuario agregar una nueva valoración a un producto.
     * @param nomProducte Nombre del producto.
     * @param marca Marca del producto.
     */
    private void afegirValoracio(String nomProducte, String marca){
        boolean estrellesValidas = false;
        int estrellesInt = 0;
        while (!estrellesValidas) {
            presentationController.getVista().numeroEstrelles();
            String estrelles = scanner.nextLine().trim();
            estrellesInt = estrelles.length();

            if (estrelles.matches("\\*{1,5}")) {
                estrellesValidas = true;
            } else {
                presentationController.getVista().errorAst();
            }
        }
        boolean comentariB = false;
        String comentari = null;
        while (!comentariB) {
            presentationController.getVista().nouComentari();
            comentari = scanner.nextLine().trim();
            if (comentari.isEmpty()) {
                presentationController.getVista().comentariBuit();
            }else{
                comentariB = true;
            }
        }
        Valoracio valoracio = new Valoracio(estrellesInt, comentari);
        tipusP.afegirValoracioJson(nomProducte, valoracio);
        presentationController.getVista().reviewFeta(nomProducte, marca);
    }


    /**
     * Muestra la lista de tiendas disponibles y sus catálogos, permitiendo al usuario explorar la información y realizar acciones.
     * @throws InputMismatchException si la entrada no es un número.
     */
    private void llistarTendes(){
        JsonArray tendes;
        JsonObject tenda;
        tendes = tipusS.llistaTendes();
        int i = 0;
        int opt = 0;
        do{
            presentationController.getVista().iniciLListaTenda();
            for (i = 0; i < tendes.size(); i++) {
                tenda = tendes.get(i).getAsJsonObject();
                String nomTenda = tenda.get("name").getAsString();
                presentationController.getVista().llistaTendes(i + 1, nomTenda);
            }
            presentationController.getVista().enrere(i + 1);
            presentationController.getVista().confirmarCataleg();

            opt = errorInt() - 1;
            if(opt >= 0 && opt < tendes.size()){
                tenda = tendes.get(opt).getAsJsonObject();
                String nomTenda = tenda.get("name").getAsString();
                int anyFundacio = tenda.get("since").getAsInt();
                String descripcio = tenda.get("description").getAsString();
                JsonArray cataleg = tenda.get("catalogue").getAsJsonArray();
                presentationController.getVista().dadesTenda(nomTenda, anyFundacio, descripcio);

                for (i = 0; i < cataleg.size(); i++){
                    JsonObject producte = cataleg.get(i).getAsJsonObject();
                    String nomProducte = producte.get("nom").getAsString();
                    String nomMarca = producte.get("marca").getAsString();
                    float preu = producte.get("preu").getAsFloat();
                    presentationController.getVista().dadesProducteTenda((i + 1), nomProducte, nomMarca, preu);
                }
                presentationController.getVista().enrere(i + 1);
                presentationController.getVista().interesTendes();
                int opt2 = errorInt() - 1;
                if(opt2 == i + 1){
                    tipusS.llistaTendes();
                }else{
                    if(opt2 >= 0 && opt2 <= cataleg.size() - 1){
                        JsonObject producte = cataleg.get(opt2).getAsJsonObject();
                        presentationController.getVista().menuLListarTendes();
                        int opt3 = errorInt();
                        submenuTendes(opt3, producte, nomTenda);
                    }
                }

            }else{
                break;
            }
        }while (opt != 3);
    }

    /**
     * Ejecuta acciones específicas del usuario para un producto seleccionado en el catálogo de una tienda.
     * @param opt Opción seleccionada por el usuario.
     * @param producte Producto seleccionado.
     * @param nomTenda Nombre de la tienda.
     */
    private void submenuTendes(int opt, JsonObject producte, String nomTenda){
        switch (opt){
            case 1 -> veureValoracions(producte.get("nom").getAsString(), producte.get("marca").getAsString());
            case 2 -> afegirValoracio(producte.get("nom").getAsString(), producte.get("marca").getAsString());
            case 3 -> afegirCarret(producte, nomTenda);
        }
    }

    /**
     * Agrega un producto al carrito de compras.
     * @param producte Producto a agregar al carrito.
     * @param nomTenda Nombre de la tienda.
     */
    private void afegirCarret(JsonObject producte, String nomTenda){
        Carret carret = new Carret(producte.get("nom").getAsString(), producte.get("marca").getAsString(), producte.get("preu").getAsFloat(), nomTenda);
        productesCarret.add(carret.toJsonObject());
        presentationController.getVista().afegitCarret(producte.get("nom").getAsString(), producte.get("marca").getAsString());

    }




    /**
     * Gestiona el contenido del carrito de compras, mostrando los productos, su precio total y permitiendo
     * al usuario finalizar la compra o vaciar el carrito.
     * @throws InputMismatchException si la entrada no es un número.
     */
    private void gestioCarret(){
        presentationController.getVista().contingutCarret();
        float preu = 0;
        JsonObject producte = null;
        for(int i = 0; i < productesCarret.size(); i++){
            producte = productesCarret.get(i).getAsJsonObject();
            presentationController.getVista().llistaCarret(producte.get("nomProducte").getAsString(), producte.get("marca").getAsString(), producte.get("nomTenda").getAsString(), producte.get("preu").getAsFloat());
            preu += producte.get("preu").getAsFloat();
        }
        presentationController.getVista().preuCarret(preu);
        presentationController.getVista().menuCarret();
        int opt = errorInt();
        switch (opt){
            case 1 -> finalitzarCompra();
            case 2 -> buidarCarret();
        }
    }

    /**
     * Finaliza la compra, actualiza los ingresos de las tiendas y limpia el carrito de compras.
     * @throws InputMismatchException si la entrada no es un número.
     */
    private void finalitzarCompra(){
        presentationController.getVista().confirmarCompra();
        String confirmar = scanner.nextLine();
        if(confirmar.equalsIgnoreCase("yes")){
            for(int i = 0; i < productesCarret.size(); i++){
                JsonObject carret = productesCarret.get(i).getAsJsonObject();
                float preu = carret.get("preu").getAsFloat();
                String nomT = carret.get("nomTenda").getAsString();
                float ingresos = tipusS.actualitzarIngresos(nomT, preu);
                presentationController.getVista().ingresosActualitzats(nomT, preu, ingresos);
                productesCarret.remove(i);
            }
            for(int i = 0; i < productesCarret.size(); i++){
                productesCarret.remove(i);
            }
        }else{
            startProgram();
        }
    }

    /**
     * Vacía el carrito de compras después de solicitar confirmación al usuario.
     * @throws InputMismatchException si la entrada no es un número.
     */
    private void buidarCarret(){
        presentationController.getVista().buidarCarret();
        String confirmacio = scanner.nextLine();

        if(confirmacio.equalsIgnoreCase("yes")){
            for(int i = 0; i < productesCarret.size(); i++){
                productesCarret.remove(i);
            }
            presentationController.getVista().carretBuidat();
        }else{
            startProgram();
        }
    }

}
