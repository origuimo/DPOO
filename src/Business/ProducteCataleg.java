package Business;

import com.google.gson.JsonObject;


/**
 * Clase que representa un producto en el catálogo de una tienda.
 * Contiene información como el nombre, la marca y el precio del producto en el catálogo.
 */
public class ProducteCataleg {

    private String nom;

    private String marca;
    private float preu;

    private String categoria;

    /**
     * Constructor para la clase ProducteCataleg.
     * @param nom Nombre del producto en el catálogo.
     * @param marca Marca del producto en el catálogo.
     * @param preu Precio del producto en el catálogo.
     */
    public ProducteCataleg(String nom, String marca, float preu, String categoria) {
        this.nom = nom;
        this.marca = marca;
        this.preu = preu;
        this.categoria = categoria;
    }

    /**
     * Convierte la instancia de ProducteCataleg a un objeto JsonObject para su representación en formato JSON.
     * @return Objeto JsonObject que representa la información del ProducteCataleg.
     */
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("nom", nom);
        jsonObject.addProperty("marca", marca);
        jsonObject.addProperty("preu", preu);
        jsonObject.addProperty("categoria", categoria);
        return jsonObject;
    }
}
