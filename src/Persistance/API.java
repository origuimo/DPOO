package Persistance;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Clase que contiene un método para verificar la disponibilidad de la API.
 */
public class API {

    /**
     * Verifica la disponibilidad de la API.
     *
     * @return true si la API está disponible (código de respuesta HTTP 200 OK), false en caso de que no se haya conetado correctamente.
     */
    public static boolean checkApi(){
        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo");
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            int responseCode = connection.getResponseCode();

            return responseCode == HttpURLConnection.HTTP_OK;

        } catch (IOException e) {
            return false;
        }
    }
}
