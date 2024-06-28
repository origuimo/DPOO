package Business;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

/**
 * Clase que representa una tienda con el tipo de tienda "LOYALTY", que extiende de la clase Tenda.
 * Esta clase específica incluye un umbral de fidelización de usuarios.
 */
public class Loyalty extends Tenda {
    private float loyaltyThreshold;

    /**
     * Constructor de la clase Loyalty.
     *
     * @param nom Nombre de la tienda.
     * @param descripcio Descripción de la tienda.
     * @param anyFundacio Año de fundación de la tienda.
     * @param ingresos Ingresos de la tienda.
     * @param cataleg Catálogo de productos de la tienda.
     * @param loyaltyThreshold Umbral de fidelización de la tienda.
     */
    public Loyalty (String nom, String descripcio, int anyFundacio, float ingresos, JsonArray cataleg, float loyaltyThreshold) {
        super(nom, descripcio, anyFundacio, ingresos, "LOYALTY", cataleg);
        this.loyaltyThreshold = loyaltyThreshold;
    }

    /**
     * Método para obtener el umbral de fidelización de la tienda.
     *
     * @return Umbral de fidelización actual de la tienda.
     */
    public float getLoyaltyThreshold() {
        return loyaltyThreshold;
    }

    /**
     * Método para establecer el umbral de fidelización de la tienda.
     *
     * @param loyaltyThreshold Nuevo umbral de fidelización a establecer.
     */
    public void setLoyaltyThreshold(float loyaltyThreshold) {
        this.loyaltyThreshold = loyaltyThreshold;
    }

    /**
     * Override del método toJsonObject de la clase Tenda.
     * Convierte los atributos de la tienda Loyalty a un objeto JsonObject.
     *
     * @return Objeto JsonObject que representa la tienda Loyalty.
     */
    @Override
    public JsonObject toJsonObject() {
        JsonObject jsonObject = super.toJsonObject();
        jsonObject.addProperty("loyaltyThreshold", loyaltyThreshold);
        return jsonObject;
    }
}
