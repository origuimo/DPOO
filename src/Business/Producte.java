package Business;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Clase que representa un producto en el sistema.
 * Contiene información como el nombre, la marca, el precio y la categoría del producto, así como las valoraciones asociadas.
 */
public class Producte {

    private String name;
    private String brand;
    private float mrp;
    private String category;

    private JsonArray valoracions;

    /**
     * Constructor para la clase Producte.
     * @param name Nombre del producto.
     * @param brand Marca del producto.
     * @param mrp Precio de venta recomendado (MRP) del producto.
     * @param category Categoría a la que pertenece el producto.
     * @param valoracions Valoraciones asociadas al producto.
     */
    public Producte(String name, String brand, float mrp, String category, JsonArray valoracions) {
        this.name = name;
        this.brand = brand;
        this.mrp = mrp;
        this.category = category;
        this.valoracions = valoracions;
    }

    /**
     * Convierte la instancia de Producte a un objeto JsonObject para su representación en formato JSON.
     * @return Objeto JsonObject que representa la información del Producte.
     */
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name);
        jsonObject.addProperty("brand", brand);
        jsonObject.addProperty("mrp", mrp);
        jsonObject.addProperty("category", category);
        jsonObject.add("reviews", valoracions);
        return jsonObject;
    }
}
