import Business.BusinessController;

/**
 * Clase principal que contiene el método main para iniciar la aplicación.
 */
public class Main {

    /**
     * Método principal que inicializa los controladores de productos y tiendas, crea un controlador de negocios
     * y ejecuta la aplicación.
     * @param args Argumentos de la línea de comandos (no utilizados en este caso).
     */
    public static void main(String[] args) {

        BusinessController businessController = new BusinessController();
        businessController.run();
    }
}
