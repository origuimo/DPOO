package Business;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Clase que representa un artículo en el carrito de compras.
 * Contiene información sobre el nombre del producto, la marca, el precio y el nombre de la tienda.
 */
public class Carret {

    private String nomProducte;
    private String marcaProducte;
    private float preuProducte;
    private String nomBotiga;
    private String categoria;


    /**
     * Constructor para la clase Carret.
     * @param nomProducte Nombre del producto en el carrito.
     * @param marcaProducte Marca del producto en el carrito.
     * @param preuProducte Precio del producto en el carrito.
     * @param nomBotiga Nombre de la tienda asociada al producto en el carrito.
     */
    public Carret(String nomProducte, String marcaProducte, float preuProducte, String nomBotiga, String categoria) {
        this.nomProducte = nomProducte;
        this.marcaProducte = marcaProducte;
        this.preuProducte = preuProducte;
        this.nomBotiga = nomBotiga;
        this.categoria = categoria;
    }

    /**
     * Convierte la instancia de Carret a un objeto JsonObject para su representación en formato JSON.
     * @return Objeto JsonObject que representa la información del Carret.
     */
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nomProducte", nomProducte);
        jsonObject.addProperty("marca", marcaProducte);
        jsonObject.addProperty("preu", preuProducte);
        jsonObject.addProperty("nomTenda", nomBotiga);
        jsonObject.addProperty("categoria", categoria);
        return jsonObject;
    }
}
