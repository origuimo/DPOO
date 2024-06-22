package Business;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class Sponsored extends Tenda {
    private String sponsor;

    public Sponsored (String nom, String descripcio, int anyFundacio, float ingresos, JsonArray cataleg, String sponsor) {
        super(nom, descripcio, anyFundacio, ingresos, "SPONSORED", cataleg);
        this.sponsor = sponsor;
    }

    public String getSponsor() {
        return sponsor;
    }

    public void setSponsor(String sponsor) {
        this.sponsor = sponsor;
    }

    @Override
    public JsonObject toJsonObject() {
        JsonObject jsonObject = super.toJsonObject();
        jsonObject.addProperty("sponsor", sponsor);
        return jsonObject;
    }
}
