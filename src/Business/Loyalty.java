package Business;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class Loyalty extends Tenda {
    private float loyaltyThreshold;

    public Loyalty (String nom, String descripcio, int anyFundacio, float ingresos, JsonArray cataleg, float loyaltyThreshold) {
        super(nom, descripcio, anyFundacio, ingresos, "LOYALTY", cataleg);
        this.loyaltyThreshold = loyaltyThreshold;
    }

    public float getLoyaltyThreshold() {
        return loyaltyThreshold;
    }

    public void setLoyaltyThreshold(float loyaltyThreshold) {
        this.loyaltyThreshold = loyaltyThreshold;
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject jsonObject = super.toJsonObject();
        jsonObject.addProperty("loyaltyThreshold", loyaltyThreshold);
        return jsonObject;
    }
}
