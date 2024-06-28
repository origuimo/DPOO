package Business;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

/**
 * Clase que representa una tienda con el tipo de tienda "SPONSORED", que extiende de la clase Tenda.
 * Esta clase específica incluye un campo para el nombre del patrocinador.
 */
public class Sponsored extends Tenda {
    private String sponsor;

    /**
     * Constructor de la clase Sponsored.
     *
     * @param nom Nombre de la tienda.
     * @param descripcio Descripción de la tienda.
     * @param anyFundacio Año de fundación de la tienda.
     * @param ingresos Ingresos de la tienda.
     * @param cataleg Catálogo de productos de la tienda.
     * @param sponsor Nombre del patrocinador de la tienda.
     */
    public Sponsored (String nom, String descripcio, int anyFundacio, float ingresos, JsonArray cataleg, String sponsor) {
        super(nom, descripcio, anyFundacio, ingresos, "SPONSORED", cataleg);
        this.sponsor = sponsor;
    }

    /**
     * Método para obtener el nombre del patrocinador de la tienda.
     *
     * @return Nombre del patrocinador de la tienda.
     */
    public String getSponsor() {
        return sponsor;
    }

    /**
     * Método para establecer el nombre del patrocinador de la tienda.
     *
     * @param sponsor Nuevo nombre del patrocinador a establecer.
     */
    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    /**
     * Override del método toJsonObject de la clase Tenda.
     * Convierte los atributos de la tienda Sponsored a un objeto JsonObject.
     *
     * @return Objeto JsonObject que representa la tienda Sponsored.
     */
    @Override
    public JsonObject toJsonObject() {
        JsonObject jsonObject = super.toJsonObject();
        jsonObject.addProperty("sponsor", sponsor);
        return jsonObject;
    }
}
