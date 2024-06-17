package Business;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Clase que representa una tienda en el sistema.
 * Contiene información como el nombre, descripción, año de fundación, ingresos, modelo de negocio y catálogo de productos de la tienda.
 */
public class Tenda {

    private String nom;
    private String descripcio;
    private int anyFundacio;

    private float ingresos;
    private String modelNegoci;
    private JsonArray cataleg;

    /**
     * Constructor para la clase Tenda.
     * @param nom Nombre de la tienda.
     * @param descripcio Descripción de la tienda.
     * @param anyFundacio Año de fundación de la tienda.
     * @param ingresos Ingresos acumulados de la tienda.
     * @param modelNegoci Modelo de negocio de la tienda.
     * @param cataleg Catálogo de productos de la tienda.
     */
    public Tenda(String nom, String descripcio, int anyFundacio, float ingresos, String modelNegoci, JsonArray cataleg) {
        this.nom = nom;
        this.descripcio = descripcio;
        this.anyFundacio = anyFundacio;
        this.ingresos = ingresos;
        this.modelNegoci = modelNegoci;
        this.cataleg = cataleg;
    }

    /**
     * Convierte la instancia de Tenda a un objeto JsonObject para su representación en formato JSON.
     * @return Objeto JsonObject que representa la información de la Tenda.
     */
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", nom);
        jsonObject.addProperty("description", descripcio);
        jsonObject.addProperty("since", anyFundacio);
        jsonObject.addProperty("earnings", ingresos);
        jsonObject.addProperty("businessModel", modelNegoci);
        jsonObject.add("catalogue", cataleg);
        return jsonObject;
    }

}
