package Persistance;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class API {

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
