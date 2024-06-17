package Persistance;

import Business.Producte;

import java.util.ArrayList;
import java.util.LinkedList;

import Business.Valoracio;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Interfaz que define las operaciones relacionadas con la persistencia de productos en el sistema.
 */
public interface ProductsDAO {

    /**
     * Verifica la existencia de un producto en el sistema.
     * @param nom Nombre del producto a verificar.
     * @return true si el producto existe, false de lo contrario.
     */
    boolean comprovarExistencia(String nom);

    /**
     * Agrega un nuevo producto al sistema.
     * @param producte Instancia del producto a ser agregado.
     * @return true si la adición es exitosa, false de lo contrario.
     */
    boolean afegirProducte(Producte producte);

    /**
     * Obtiene un array JSON que representa la lista de productos en el sistema.
     * @return JsonArray que contiene la información de los productos.
     */
    JsonArray llistarProductes();

    /**
     * Elimina un producto del sistema dada su posicion.
     * @param numero Número que identifica al producto a ser eliminado.
     */
    void eliminarProducte(int numero);

    /**
     * Comprueba el precio de un producto en el sistema.
     * @param nom Nombre del producto cuyo precio se va a comprobar.
     * @return Precio del producto.
     */
    float comprovarPreu(String nom);

    /**
     * Obtiene la marca de un producto en el sistema.
     * @param nomP Nombre del producto cuya marca se va a obtener.
     * @return Marca del producto.
     */
    String obtenirMarca(String nomP);

    /**
     * Realiza una búsqueda en el sistema y devuelve un array JSON con los resultados.
     * @param query Término de búsqueda.
     * @return JsonArray que contiene la lista de resultados de búsqueda.
     */
    JsonArray llistaDeResultats(String query);

    /**
     * Obtiene una lista de valoraciones para un producto específico en el sistema.
     * @param nomProducte Nombre del producto cuyas valoraciones se van a listar.
     * @return JsonArray que contiene la información de las valoraciones del producto.
     */
    JsonArray llistarValoracions(String nomProducte);

    /**
     * Agrega una nueva valoración a un producto en el sistema.
     * @param nomProducte Nombre del producto al que se agrega la valoración.
     * @param valoracio Valoración a ser agregada.
     */
    void afegirValoracioJson(String nomProducte, Valoracio valoracio);
}
