package Persistance;

import Business.Producte;
import Business.Valoracio;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProductsApi implements ProductsDAO{
    @Override
    public boolean comprovarExistencia(String nom) {

        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/products");
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            JsonArray jsonArray = readJsonArrayResponse(connection);

            int responseCode = connection.getResponseCode();


            connection.disconnect();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject product = jsonArray.get(i).getAsJsonObject();
                String name = product.get("name").getAsString();

                if (name.equals(nom)) {
                    return true;
                }
            }

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud GET fue exitosa.");
            } else {
                System.out.println("La solicitud GET falló. Código de respuesta: " + responseCode);
            }


        } catch (IOException e) {
            return false;
        }

        return false;
    }

    @Override
    public boolean afegirProducte(Producte producte) {
        try {
            // Crear la URL
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/products");

            // Abrir conexión
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para una solicitud POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JsonObject jsonObject = producte.toJsonObject();
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
    public JsonArray llistarProductes() {

        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/products");
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            JsonArray jsonArray = readJsonArrayResponse(connection);

            int responseCode = connection.getResponseCode();


            connection.disconnect();


            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud GET fue exitosa.");
            } else {
                System.out.println("La solicitud GET falló. Código de respuesta: " + responseCode);
            }

            return jsonArray;


        } catch (IOException e) {
            return null;
        }
    }

    static JsonArray readJsonArrayResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return new Gson().fromJson(in, JsonArray.class);
        }

    }
    @Override
    public void eliminarProducte(int numero) {
        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/products/" + numero);
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("DELETE");
            connection.setConnectTimeout(5000);

            int responseCode = connection.getResponseCode();


            connection.disconnect();


            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud DELETE fue exitosa.");
            } else {
                System.out.println("La solicitud DELETE falló. Código de respuesta: " + responseCode);
            }

        } catch (IOException e) {

        }
    }

    @Override
    public float comprovarPreu(String nom) {
        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/products");
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            JsonArray jsonArray = readJsonArrayResponse(connection);

            int responseCode = connection.getResponseCode();


            connection.disconnect();


            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud GET fue exitosa.");
            } else {
                System.out.println("La solicitud GET falló. Código de respuesta: " + responseCode);
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject product = jsonArray.get(i).getAsJsonObject();
                String name = product.get("name").getAsString();

                if (name.equals(nom)) {
                    return product.get("mrp").getAsFloat();
                }
            }

        } catch (IOException e) {
            return 0;
        }
        return 0;
    }

    @Override
    public String obtenirMarca(String nomP) {

        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/products");
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            JsonArray jsonArray = readJsonArrayResponse(connection);

            int responseCode = connection.getResponseCode();


            connection.disconnect();


            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud GET fue exitosa.");
            } else {
                System.out.println("La solicitud GET falló. Código de respuesta: " + responseCode);
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject product = jsonArray.get(i).getAsJsonObject();
                String name = product.get("name").getAsString();

                if (name.equals(nomP)) {
                    return product.get("brand").getAsString();
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    @Override
    public JsonArray llistaDeResultats(String query) {

        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/products");
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            JsonArray jsonArray = readJsonArrayResponse(connection);

            int responseCode = connection.getResponseCode();


            connection.disconnect();


            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud GET fue exitosa.");
            } else {
                System.out.println("La solicitud GET falló. Código de respuesta: " + responseCode);
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject product = jsonArray.get(i).getAsJsonObject();
                String productName = product.get("name").getAsString();
                String brandName = product.get("brand").getAsString();
                if (productName.equalsIgnoreCase(query) || productName.toLowerCase().contains(query.toLowerCase()) || brandName.equals(query)) {
                    jsonArray.add(product);
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    @Override
    public JsonArray llistarValoracions(String nomProducte) {
        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/products");
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            JsonArray jsonArray = readJsonArrayResponse(connection);

            int responseCode = connection.getResponseCode();


            connection.disconnect();


            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud GET fue exitosa.");
            } else {
                System.out.println("La solicitud GET falló. Código de respuesta: " + responseCode);
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject product = jsonArray.get(i).getAsJsonObject();
                String name = product.get("name").getAsString();

                if (name.equals(nomProducte)) {
                    return product.get("reviews").getAsJsonArray();
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    @Override
    public void afegirValoracioJson(String nomProducte, Valoracio valoracio) {
        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/products");
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            JsonArray jsonArray = readJsonArrayResponse(connection);

            int responseCode = connection.getResponseCode();


            connection.disconnect();


            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud GET fue exitosa.");
            } else {
                System.out.println("La solicitud GET falló. Código de respuesta: " + responseCode);
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject producte = jsonArray.get(i).getAsJsonObject();
                String name = producte.get("name").getAsString();
                if (name.equals(nomProducte)) {
                    JsonArray valoracion = producte.get("reviews").getAsJsonArray();
                    valoracion.add(valoracio.toJsonObject());
                    afegirProducteV(producte);

                }
            }

        } catch (IOException e) {

        }
    }

    private void afegirProducteV(JsonObject producte) {
        try {
            // URL para agregar un nuevo producto
            String id = "S1-Project_115"; // Aquí debes colocar el ID correcto de tu proyecto
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/" + id + "/products");

            // Abrir conexión
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para una solicitud POST
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Enviar el cuerpo del mensaje (nuevo producto)
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = producte.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Obtener el código de respuesta
            int responseCode = connection.getResponseCode();

            // Leer la respuesta (opcional)
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Respuesta del servidor: " + response.toString());
            }

            // Cerrar la conexión
            connection.disconnect();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud POST fue exitosa.");
            } else {
                System.out.println("La solicitud POST falló. Código de respuesta: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
