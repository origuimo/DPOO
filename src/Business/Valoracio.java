package Business;

import com.google.gson.JsonObject;

/**
 * Clase que representa una valoración de un producto en el sistema.
 * Contiene información sobre el número de estrellas y un comentario asociado.
 */
public class Valoracio {


    private int estrelles;
    private String comentari;

    /**
     * Constructor para la clase Valoracio.
     * @param estrelles Número de estrellas otorgadas en la valoración.
     * @param comentari Comentario asociado a la valoración.
     */
    public Valoracio(int estrelles, String comentari) {
        this.estrelles = estrelles;
        this.comentari = comentari;
    }

    /**
     * Convierte la instancia de Valoracio a un objeto JsonObject para su representación en formato JSON.
     * @return Objeto JsonObject que representa la información de la Valoracio.
     */
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("estrelles", estrelles);
        jsonObject.addProperty("comentari", comentari);
        return jsonObject;
    }
}
