package Presentation;

public class Vista {

    /**
     * Muestra el mensaje de bienvenida y detalles de inicialización del programa.
     */
    public void missatgePresentacio() {
        System.out.println("       _  ____       __           \n" +
                "   ___| |/ ___|___  / _|_ __ ___  \n" +
                "  / _ \\ | |   / _ \\| |_| '__/ _ \\ \n" +
                " |  __/ | |__| (_) |  _| | |  __/ \n" +
                "  \\___|_|\\____\\___/|_| |_|  \\___| \n" +
                "                                  ");
        System.out.println("Bienvenido a elCofre Digital Shopping Experiences.\n");
        System.out.println("Checking API status...");
    }

    /**
     * Muestra un mensaje de error relacionado con el acceso al archivo products.json para la persistencia de productos.
     */
    public void errorPersistencia() {
        System.out.println("Error: No se puede acceder al archivo products.json.\n" +
                "Cerrando el programa...\n");
    }

    /**
     * Muestra un mensaje indicando el inicio del programa.
     */
    public void start() {
        System.out.println("Iniciando programa...\n");
    }

    /**
     * Muestra el menú principal para las Experiencias de Compra Digital.
     */
    public void menu() {
        System.out.println(" 1) Gestionar Productos\n" +
                " 2) Gestionar Tiendas\n" +
                " 3) Buscar Productos\n" +
                " 4) Listar Tiendas\n" +
                " 5) Tu Carrito\n" +
                " 6) Salir\n" +
                "Elige una Experiencia de Compra Digital: ");
    }

    /**
     * Muestra un mensaje de despedida al salir del programa.
     */
    public void exit() {
        System.out.println("¡Esperamos verte nuevamente!\n");
    }

    /**
     * Muestra un mensaje de error para una opción de usuario no válida.
     */
    public void opcioNoValida() {
        System.out.println("Opción no válida\n");
    }

    /**
     * Muestra el menú para gestionar productos.
     */
    public void menu1() {
        System.out.println(" 1) Crear un Producto\n" +
                " 2) Eliminar un Producto\n" +
                " 3) Atrás\n" +
                "Elige una opción: ");
    }

    /**
     * Solicita al usuario que ingrese el nombre del producto.
     */
    public void productName() {
        System.out.println("Por favor, ingresa el nombre del producto: ");
    }

    /**
     * Solicita al usuario que ingrese la marca del producto.
     */
    public void productBrand() {
        System.out.println("Por favor, ingresa la marca del producto: ");
    }

    /**
     * Solicita al usuario que ingrese el precio máximo de venta al público del producto.
     */
    public void productPrice() {
        System.out.println("Por favor, ingresa el precio máximo de venta al público del producto:");
    }

    /**
     * Muestra las categorías de productos disponibles y solicita al usuario que elija la categoría del producto.
     */
    public void productCategories() {
        System.out.println("El sistema admite las siguientes categorías de productos:\n" +
                "   A) General\n" +
                "   B) Impuestos Reducidos\n" +
                "   C) Impuestos Superreducidos\n" +
                "Por favor, elige la categoría del producto: ");
    }

    /**
     * Muestra un mensaje de error cuando el nombre del producto ya existe.
     */
    public void errorNom() {
        System.out.println("Este producto ya existe\n");
    }

    /**
     * Muestra un mensaje de error cuando el nombre del producto no existe.
     */
    public void errorNom1() {
        System.out.println("Este producto no existe\n");
    }

    /**
     * Muestra un mensaje de error cuando el precio introducido es superior al maximo.
     */
    public void errorPreumax() {
        System.out.println("Este precio es superior al maximo establecido\n");
    }

    /**
     * Muestra un mensaje de error cuando el precio introducido es distinto a el de otro producto igual.
     */
    public void errorPreuprod() {
        System.out.println("Precio distinto a otro ya fijado de este producto\n");
    }

    /**
     * Muestra un mensaje de error cuando el precio máximo del producto es menor que 0.
     */
    public void errorPreu() {
        System.out.println("El precio máximo debe ser 0 o superior\n");
    }

    /**
     * Muestra un mensaje indicando que el producto se ha agregado con éxito al sistema.
     */
    public void guardat(String nom, String marca) {
        System.out.println("El producto \"" + nom + "\" de \"" + marca + "\" se ha añadido al sistema.\n");
    }

    /**
     * Muestra un mensaje de error cuando no se puede agregar el nuevo producto.
     */
    public void errorGuardar() {
        System.out.println("Error: No se pudo agregar el nuevo producto\n");
    }

    /**
     * Muestra un mensaje indicando los productos disponibles actualmente.
     */
    public void productesDisponibles() {
        System.out.println("Estos son los productos disponibles actualmente:\n");
    }

    /**
     * Muestra información sobre un producto en la lista de productos.
     */
    public void productesLlista(int i, String nom, String marca) {
        System.out.println("    " + (i + 1) + ") \"" + nom + "\" de \"" + marca + "\"");
    }

    /**
     * Solicita al usuario que elija un producto para eliminar.
     */
    public void elegirProducte() {
        System.out.println("¿Cuál te gustaría eliminar?");
    }

    /**
     * Solicita al usuario confirmación para eliminar un producto.
     */
    public void confirmarborrat(String nom, String marca) {
        System.out.println("¿Estás seguro de que quieres eliminar \"" + nom + "\" de \"" + marca + "\"?");
    }

    /**
     * Muestra un mensaje indicando que el producto ha sido retirado con éxito.
     */
    public void borratconfirmat(String nom, String marca) {
        System.out.println("\"" + nom + "\" de \"" + marca + "\" se ha retirado de la venta.\n");
    }

    /**
     * Muestra un enlace para retroceder en la interfaz.
     */
    public void enrere(int i) {
        System.out.println("\n    " + i + ") Atrás\n");
    }

    /**
     * Muestra el menú para gestionar tiendas.
     */
    public void menuTenda() {
        System.out.println(" 1) Crear una Tienda\n" +
                " 2) Ampliar el Catálogo de una Tienda\n" +
                " 3) Reducir el Catálogo de una Tienda\n" +
                "\n 4) Atrás\n" +
                "Elige una opción: ");
    }

    /**
     * Solicita al usuario que ingrese el nombre de la tienda.
     */
    public void nomTenda() {
        System.out.println("Por favor, ingresa el nombre de la tienda:");
    }

    /**
     * Solicita al usuario que ingrese la descripción de la tienda.
     */
    public void descripcioTenda() {
        System.out.println("Por favor, ingresa la descripción de la tienda:");
    }

    /**
     * Solicita al usuario que ingrese el año de fundación de la tienda.
     */
    public void anyFundacio() {
        System.out.println("Por favor, ingresa el año de fundación de la tienda:");
    }

    /**
     * Muestra las opciones de modelos de negocio para la tienda y solicita al usuario que elija uno.
     */
    public void businessModel() {
        System.out.println("\nEl sistema admite los siguientes modelos de negocio:\n" +
                " A) Beneficios Máximos\n" +
                " B) Lealtad\n" +
                " C) Patrocinado\n" +
                "Por favor, elige el modelo de negocio de la tienda:");
    }

    public void loyaltiThreshold(){
        System.out.println("Please enter the shop’s loyalty threshold: ");
    }

    public void sponsor(){
        System.out.println("Please enter the shop’s sponsor: ");
    }

    /**
     * Muestra un mensaje indicando que la tienda se ha agregado con éxito a la familia elCofre.
     */
    public void tendaConfirmada(String nom) {
        System.out.println("\"" + nom + "\" ahora forma parte de la familia elCofre.\n");
    }

    /**
     * Muestra un mensaje de error cuando el nombre de la tienda ya existe.
     */
    public void errorNomTenda() {
        System.out.println("Esta tienda ya existe\n");
    }

    /**
     * Muestra un mensaje de error cuando el año de fundación de la tienda es menor que 0.
     */
    public void errorAny() {
        System.out.println("El año de fundación debe ser 0 o superior\n");
    }

    /**
     * Muestra un mensaje de error cuando no se puede agregar la nueva tienda.
     */
    public void errorGuardarTenda() {
        System.out.println("Error: No se pudo agregar la nueva tienda\n");
    }

    /**
     * Solicita al usuario que ingrese el precio del producto en esta tienda.
     */
    public void preuProducte() {
        System.out.println("Por favor, ingresa el precio del producto en esta tienda: ");
    }

    /**
     * Muestra un mensaje indicando que el producto ahora se vende en la tienda.
     */
    public void catalegActualitzat(String nomP, String marca, String nomTenda) {
        System.out.println("\"" + nomP + "\" de \"" + marca + "\" ahora se vende en \"" + nomTenda + "\".\n");
    }

    /**
     * Muestra los productos que vende la tienda actualmente.
     */
    public void productesCataleg() {
        System.out.println("Esta tienda vende los siguientes productos:\n");
    }

    /**
     * Muestra un mensaje indicando que el producto ya no se vende en la tienda.
     */
    public void borratCatConf(String nomP, String marca, String nomT) {
        System.out.println("\"" + nomP + "\" de \"" + marca + "\" ya no se vende en \"" + nomT + "\".\n");
    }

    /**
     * Solicita al usuario que ingrese su consulta.
     */
    public void query() {
        System.out.println("Ingresa tu consulta: ");
    }

    /**
     * Muestra el encabezado para indicar que el producto se vende en algunas tiendas.
     */
    public void soldAt() {
        System.out.println("    Se vende en:");
    }

    /**
     * Muestra un mensaje indicando que el producto no se vende actualmente en ninguna tienda.
     */
    public void noHiHaProductes() {
        System.out.println("    Este producto no se vende actualmente en ninguna tienda.\n");
    }

    /**
     * Muestra información sobre la tienda y el precio del producto en esa tienda.
     */
    public void producteTenda(String nomTenda, float preuProducte) {
        System.out.println("        - " + nomTenda + ": " + preuProducte);
    }

    /**
     * Solicita al usuario que elija un producto para revisar.
     */
    public void revisar() {
        System.out.println("¿Cuál te gustaría revisar? ");
    }

    /**
     * Muestra el menú de resultados después de buscar productos.
     */
    public void menuResultats() {
        System.out.println(" 1) Leer Reseñas\n" +
                " 2) Hacer Reseña al Producto\n" +
                " 3) Comprar Producto");
    }

    /**
     * Muestra las reseñas de un producto específico.
     */
    public void reviews(String nomP, String marca) {
        System.out.println("Estas son las reseñas para \"" + nomP + "\" de \"" + marca + "\":\n");
    }

    /**
     * Muestra una valoración y comentario específicos de una reseña.
     */
    public void llistarValoracio(int estrelles, String comentari) {
        System.out.println("    " + estrelles + "* " + comentari);
    }

    /**
     * Muestra la calificación promedio de las reseñas de un producto.
     */
    public void mostrarMirjana(float mitjana) {
        System.out.println("\n    Calificación promedio: " + mitjana + "*\n");
    }

    /**
     * Solicita al usuario que ingrese la calificación del producto (1-5 estrellas).
     */
    public void numeroEstrelles() {
        System.out.println("Por favor, califica el producto (1-5 estrellas): ");
    }

    /**
     * Solicita al usuario que agregue un comentario a su reseña.
     */
    public void nouComentari() {
        System.out.println("Por favor, agrega un comentario a tu reseña: ");
    }

    /**
     * Muestra un agradecimiento por la reseña del producto.
     */
    public void reviewFeta(String nomP, String marca) {
        System.out.println("Gracias por tu reseña de \"" + nomP + "\" de \"" + marca + "\".\n");
    }

    /**
     * Muestra la lista de tiendas en la familia elCofre.
     */
    public void iniciLListaTenda() {
        System.out.println("La familia elCofre está formada por las siguientes tiendas:\n");
    }

    /**
     * Muestra información sobre una tienda en la lista de tiendas.
     */
    public void llistaTendes(int i, String nomTenda) {
        System.out.println(i + ") " + nomTenda);
    }

    /**
     * Solicita al usuario que confirme qué catálogo quiere ver.
     */
    public void confirmarCataleg() {
        System.out.println("¿Qué catálogo te gustaría ver?\n");
    }


    /**
     * Muestra información sobre una tienda, incluyendo el nombre, año de fundación y descripción.
     * @param nom       El nombre de la tienda.
     * @param any       El año de fundación de la tienda.
     * @param descripcio La descripción de la tienda.
     */
    public void dadesTenda(String nom, int any, String descripcio) {
        System.out.println(nom + " - Desde " + any);
        System.out.println(descripcio);
    }

    /**
     * Muestra información sobre un producto en una tienda, incluyendo el nombre, marca y precio.
     * @param i     El índice del producto.
     * @param nom   El nombre del producto.
     * @param marca La marca del producto.
     * @param preu  El precio del producto.
     */
    public void dadesProducteTenda(int i, String nom, String marca, float preu) {
        System.out.println("    " + i + ") \"" + nom + "\" por \"" + marca + "\"");
        System.out.println("       Precio: " + preu);
    }

    /**
     * Muestra un mensaje solicitando al usuario que elija una tienda de interés.
     */
    public void interesTendes() {
        System.out.println("¿En cuál estás interesado?");
    }

    /**
     * Muestra un menú para listar opciones relacionadas con tiendas.
     */
    public void menuLListarTendes() {
        System.out.println("\n   1) Leer Reseñas\n" +
                "   2) Reseñar Producto\n" +
                "   3) Agregar al Carrito\n" +
                "Elige una opción: ");
    }

    /**
     * Muestra un mensaje indicando que un producto ha sido añadido al carrito.
     * @param nomP  El nombre del producto.
     * @param marca La marca del producto.
     */
    public void afegitCarret(String nomP, String marca) {
        System.out.println("1x \"" + nomP + "\" por \"" + marca + "\" ha sido añadido a tu carrito.\n");
    }

    /**
     * Muestra el contenido del carrito.
     */
    public void contingutCarret() {
        System.out.println("Tu carrito contiene los siguientes artículos:\n");
    }

    /**
     * Muestra la lista de productos en el carrito, incluyendo nombre, marca, tienda y precio.
     * @param nomP  El nombre del producto.
     * @param marca La marca del producto.
     * @param nomT  El nombre de la tienda.
     * @param preu  El precio del producto.
     */
    public void llistaCarret(String nomP, String marca, String nomT, float preu) {
        System.out.println("    - \"" + nomP + "\" por \"" + marca + "\" en \"" + nomT + "\"\n       Precio: " + preu + "\n");
    }

    /**
     * Muestra el precio total del carrito.
     *
     * @param preu El precio total del carrito.
     */
    public void preuCarret(float preu) {
        System.out.println("Total: " + preu + "\n");
    }

    /**
     * Muestra un menú para acciones relacionadas con el carrito.
     */
    public void menuCarret() {
        System.out.println("1) Pagar\n" +
                " 2) Vaciar carrito\n" +
                " 3) Atrás\n" +
                "Elige una opción:");
    }

    /**
     * Solicita confirmación antes de realizar la compra.
     */
    public void confirmarCompra() {
        System.out.println("\n¿Estás seguro de que deseas realizar la compra? ");
    }

    /**
     * Muestra información actualizada sobre los ingresos de una tienda después de una compra.
     * @param nomT     El nombre de la tienda.
     * @param preu     El precio de la compra.
     * @param ingresos Los ingresos históricos de la tienda.
     */
    public void ingresosActualitzats(String nomT, float preu, float ingresos) {
        System.out.println("\"" + nomT + "\" ha ganado " + preu + " para un total histórico de " + ingresos + ".");
    }

    /**
     * Solicita confirmación antes de vaciar el carrito.
     */
    public void buidarCarret() {
        System.out.println("¿Estás seguro de que deseas vaciar tu carrito? ");
    }

    /**
     * Muestra un mensaje indicando que el carrito ha sido vaciado.
     */
    public void carretBuidat() {
        System.out.println("Tu carrito ha sido vaciado.\n");
    }

    /**
     * Muestra un mensaje de error cuando se espera una entrada numérica.
     */
    public void errorInt() {
        System.out.println("Error, debes introducir un número");
    }

    /**
     * Muestra un mensaje indicando que la tienda no existe.
     */
    public void shopExist() {
        System.out.println("La tienda no existe\n");
    }

    /**
     * Muestra un mensaje indicando que no hay reseñas para un producto.
     */
    public void noReview() {
        System.out.println("No hay reseñas para este producto\n");
    }

    /**
     * Muestra un mensaje indicando que no hay coincidencias con la consulta.
     */
    public void coincidecncies() {
        System.out.println("No hay coincidencias con esta consulta\n");
    }

    public void errorApi(){
        System.out.println("Error: The API isn’t available.\n");
    }

    public void errorNomTenda1(){
        System.out.println("Error: Es necesario añadir un nombre a la tienda\n");
    }

    public void errorAst(){
        System.out.println("Introdueix un número d'estrelles vàlid (de 1 a 5 estrelles).\n");
    }

    public void comentariBuit(){
        System.out.println("El comentario no puede estar vacio\n");
    }

    public void noTendesAvailable() { System.out.println("No hay tiendas disponibles");
    }
}

