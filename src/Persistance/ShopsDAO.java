package Persistance;

import Business.Producte;
import Business.ProducteCataleg;
import Business.Tenda;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Interfaz que define las operaciones relacionadas con la persistencia de tiendas en el sistema.
 */
public interface ShopsDAO {


    /**
     * Verifica la existencia de una tienda en el sistema.
     * @param nom Nombre de la tienda a verificar.
     * @return true si la tienda existe, false en caso contrario.
     */
    boolean comprovarExistenciaTenda(String nom);

    /**
     * Añade una nueva tienda al sistema.
     * @param tenda Objeto Tenda que representa la tienda a añadir.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    boolean afegirTenda(Tenda tenda);

    /**
     * Verifica la consistencia del precio de un producto en una tienda.
     * @param nomT Nombre de la tienda.
     * @param nomP Nombre del producto.
     * @param preuNou Nuevo precio del producto.
     * @return true si el precio es consistente, false en caso contrario.
     */
    boolean consistenciaPreu(String nomT, String nomP, float preuNou);

    /**
     * Actualiza el catálogo de una tienda con un nuevo producto.
     * @param nomTenda Nombre de la tienda.
     * @param producte Producto a agregar al catálogo.
     */
    void actualitzarCataleg(String nomTenda, ProducteCataleg producte);

    /**
     * Obtiene la lista de productos en el catálogo de una tienda.
     * @param nomT Nombre de la tienda.
     * @return JsonArray que contiene los productos en el catálogo de la tienda.
     */
    JsonArray productesCataleg(String nomT);

    /**
     * Elimina un producto del catálogo de una tienda.
     * @param num Número del producto a eliminar.
     * @param nomT Nombre de la tienda.
     */
    void eliminarProdCataleg(int num, String nomT);

    /**
     * Obtiene la lista de productos en una tienda.
     * @param nomProducte Nombre del producto.
     * @return JsonArray que contiene los productos en la tienda.
     */
    JsonArray productesTenda(String nomProducte);

    /**
     * Obtiene la lista de todas las tiendas en el sistema.
     * @return JsonArray que contiene la información de todas las tiendas.
     */
    JsonArray llistaTendes();

    /**
     * Actualiza los ingresos de una tienda después de una compra.
     * @param nomT Nombre de la tienda.
     * @param carret Monto de la compra realizada.
     * @return Nuevo monto de ingresos de la tienda.
     */
    float actualitzarIngresos(String nomT, float carret);

    /**
     * Obtiene el sponsor de una tienda específica.
     * @param nomTenda es el nombre de la tienda que le pasamos.
     * @return retorna un String con el nombre del sponsor.
     */
    String obtenerSponsor(String nomTenda);

    float obtenerThreshold(String nomTenda);
}
