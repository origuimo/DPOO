package Persistance;

import Business.ProducteCataleg;
import Business.Tenda;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static Persistance.ProductsApi.readJsonArrayResponse;

public class ShopsApi implements ShopsDAO{
    @Override
    public boolean comprovarExistenciaTenda(String nom) {
        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                JsonArray jsonArray = readJsonArrayResponse(connection);
                connection.disconnect();

                for (int i = 0; i < jsonArray.size(); i++) {
                    JsonObject product = jsonArray.get(i).getAsJsonObject();

                    // Imprimir el objeto JSON completo para depuración
                    //System.out.println("Objeto JSON en índice " + i + ": " + product.toString());

                    // Verificar si el objeto tiene el campo "name"
                    if (product.has("name")) {
                        String name = product.get("name").getAsString();
                        if (name.equals(nom)) {
                            return true;
                        }
                    } else {
                        //System.out.println("El objeto en el índice " + i + " no tiene un campo 'name', será ignorado.");
                    }
                }
            } else {
                System.out.println("La solicitud GET falló. Código de respuesta: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return false;
    }


    @Override
    public boolean afegirTenda(Tenda tenda) {
        try {
            // Crear la URL
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");

            // Abrir conexión
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para una solicitud POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Convertir el objeto Tenda a JSON
            JsonObject jsonObject = tenda.toJsonObject();
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(jsonObject);

            // Enviar el cuerpo del mensaje
            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(jsonInputString);
                wr.flush();
            }

            // Obtener el código de respuesta
            int responseCode = connection.getResponseCode();

            connection.disconnect();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud POST fue exitosa.");
                return true;
            } else {
                System.out.println("La solicitud POST falló. Código de respuesta: " + responseCode);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public boolean consistenciaPreu(String nomT, String nomP, float preuNou) {
        try {
            // Crear la URL
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");

            // Abrir conexión
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para una solicitud POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JsonArray jsonArray = readJsonArrayResponse(connection);

            // Obtener el código de respuesta
            int responseCode = connection.getResponseCode();


            connection.disconnect();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject shop = jsonArray.get(i).getAsJsonObject();
                String name = shop.get("name").getAsString();

                if (name.equals(nomT)) {
                    JsonArray cataleg = shop.get("catalogue").getAsJsonArray();
                    for(i = 0; i < cataleg.size(); i++){
                        JsonObject producte = cataleg.get(i).getAsJsonObject();
                        String nomProd = producte.get("nom").getAsString();

                        if (nomProd.equals(nomP)){
                            float preu = producte.get("preu").getAsFloat();
                            if(preu != preuNou){
                                return false;
                            }else{
                                return true;
                            }
                        }
                    }
                    return true;
                }
            }

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud POST fue exitosa.");
                return true;
            } else {
                System.out.println("La solicitud POST falló. Código de respuesta: " + responseCode);
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void actualitzarCataleg(String nomTenda, ProducteCataleg producte) {

        try {
            // Crear la URL
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");

            // Abrir conexión
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para una solicitud POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JsonArray jsonArray = readJsonArrayResponse(connection);

            // Obtener el código de respuesta
            int responseCode = connection.getResponseCode();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject tenda = jsonArray.get(i).getAsJsonObject();
                String name = tenda.get("name").getAsString();

                if (name.equals(nomTenda)) {
                    JsonArray cataleg = tenda.get("catalogue").getAsJsonArray();
                    cataleg.add(producte.toJsonObject());

                    //FALTE ESCRIURE-HO UN ALTRE COP
                }
            }

            connection.disconnect();



            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud POST fue exitosa.");
            } else {
                System.out.println("La solicitud POST falló. Código de respuesta: " + responseCode);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public JsonArray productesCataleg(String nomT) {
        return null;
    }

    @Override
    public void eliminarProdCataleg(int num, String nomT) {

    }

    @Override
    public JsonArray productesTenda(String nomProducte) {
        return null;
    }

    @Override
    public JsonArray llistaTendes(){
        JsonArray jsonArray = new JsonArray();
        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            jsonArray = readJsonArrayResponse(connection);

            int responseCode = connection.getResponseCode();

            connection.disconnect();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud GET fue exitosa.");
            } else {
                System.out.println("La solicitud GET falló. Código de respuesta: " + responseCode);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return jsonArray;
    }





    @Override
    public float actualitzarIngresos(String nomT, float carret) {
        return 0;
    }
}
