package Presentation;

public class PresentationController {
    /**
     * Conexión de nuestro controlador de persistencia a la clase donde almacenamos
     * todos los sout's que debemos hacer a lo largo del código
     */
    private final Vista vista;

    /**
     * Constructor de la clase PresentationController
     */
    public PresentationController() {
        this.vista = new Vista();
    }

    /**
     * Conexión con la Vista
     * @return uiController
     */
    public Vista getVista() {
        return vista;
    }
}
