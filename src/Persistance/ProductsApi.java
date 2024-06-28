package Persistance;

import Business.Producte;
import Business.Valoracio;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;


public class ProductsApi implements ProductsDAO {

    /**
     * Comprova l'existència d'un producte donat el seu nom.
     *
     * @param nom és el nom del producte a comprovar.
     * @return true si el producte existeix, false si no existeix.
     */
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
                System.out.println("La sol·licitud GET va ser exitosa.");
            } else {
                System.out.println("La sol·licitud GET va fallar. Codi de resposta: " + responseCode);
            }
        } catch (IOException e) {
            return false;
        }

        return false;
    }

    /**
     * Afegeix un nou producte a l'API.
     *
     * @param producte és el producte a afegir.
     * @return true si el producte s'ha afegit correctament, false si no ha pogut.
     */
    @Override
    public boolean afegirProducte(Producte producte) {
        try {
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/products");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            JsonObject jsonObject = producte.toJsonObject();
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(jsonObject);

            try (DataOutputStream wr = new DataOutputStream(connection.getOutputStream())) {
                wr.writeBytes(jsonInputString);
                wr.flush();
            }

            int responseCode = connection.getResponseCode();
            connection.disconnect();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La sol·licitud POST va ser exitosa.");
                return true;
            } else {
                System.out.println("La sol·licitud POST va fallar. Codi de resposta: " + responseCode);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Llista tots els productes de l'API.
     *
     * @return un JsonArray amb tots els productes trobats.
     */
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
                System.out.println("La sol·licitud GET va ser exitosa.");
            } else {
                System.out.println("La sol·licitud GET va fallar. Codi de resposta: " + responseCode);
            }

            return jsonArray;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Llegeix la resposta JSON d'una connexió i la converteix en un JsonArray.
     *
     * @param connection la connexió a http necesaria.
     * @return el JsonArray resultant d'aquetsa connexió.
     * @throws IOException si passa algun error de lectura.
     */
    static JsonArray readJsonArrayResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            return new Gson().fromJson(in, JsonArray.class);
        }
    }

    /**
     * Elimina un producte de l'API donat el seu número.
     *
     * @param numero és el número del producte a eliminar.
     */
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
                System.out.println("La sol·licitud DELETE va ser exitosa.");
            } else {
                System.out.println("La sol·licitud DELETE va fallar. Codi de resposta: " + responseCode);
            }
        } catch (IOException e) {
            // Maneig d'errors (si cal)
        }
    }

    /**
     * Comprova el preu d'un producte donat el seu nom.
     *
     * @param nom és el nom del producte que volem saber el seu preu.
     * @return el preu del producte de tipus float.
     */
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
                System.out.println("La sol·licitud GET va ser exitosa.");
            } else {
                System.out.println("La sol·licitud GET va fallar. Codi de resposta: " + responseCode);
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

    /**
     * Obté la marca d'un producte donat el seu nom de producte.
     *
     * @param nomP és el nom del producte.
     * @return la marca del producte actual.
     */
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
                System.out.println("La sol·licitud GET va ser exitosa.");
            } else {
                System.out.println("La sol·licitud GET va fallar. Codi de resposta: " + responseCode);
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

    /**
     * Obté la categoria d'un producte donat el seu nom del producte.
     *
     * @param nomP és el nom del producte.
     * @return la categoria del producte actual.
     */
    @Override
    public String obtenirCategoria(String nomP) {
        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/products");
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            JsonArray jsonArray = readJsonArrayResponse(connection);
            int responseCode = connection.getResponseCode();
            connection.disconnect();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La sol·licitud GET va ser exitosa.");
            } else {
                System.out.println("La sol·licitud GET va fallar. Codi de resposta: " + responseCode);
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject product = jsonArray.get(i).getAsJsonObject();
                String name = product.get("name").getAsString();

                if (name.equals(nomP)) {
                    return product.get("category").getAsString();
                }
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    /**
     * Llista els productes que compleixen amb la consulta escrita anteriorment.
     *
     * @param query la consulta de cerca que li escribim per pantalla.
     * @return una JsonArray amb els productes que compleixen amb la consulta.
     */
    @Override
    public JsonArray llistaDeResultats(String query) {
        JsonArray resultsArray = new JsonArray();
        try {
            URL apiURL = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/products");
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            JsonArray jsonArray = readJsonArrayResponse(connection);
            int responseCode = connection.getResponseCode();
            connection.disconnect();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La sol·licitud GET va ser exitosa.");
            } else {
                System.out.println("La sol·licitud GET va fallar. Codi de resposta: " + responseCode);
            }

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject product = jsonArray.get(i).getAsJsonObject();
                String productName = product.get("name").getAsString();
                String brandName = product.get("brand").getAsString();

                if (productName.equalsIgnoreCase(query) || productName.toLowerCase().contains(query.toLowerCase()) || brandName.equals(query)) {
                    resultsArray.add(product);
                }
            }
        } catch (IOException e) {
            return null;
        }
        return resultsArray;
    }

    /**
     * Llista les valoracions d'un producte donat el seu nom.
     *
     * @param nomProducte és el nom del producte.
     * @return una JsonArray amb les valoracions del producte que li passem.
     */
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
                System.out.println("La sol·licitud GET va ser exitosa.");
            } else {
                System.out.println("La sol·licitud GET va fallar. Codi de resposta: " + responseCode);
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

    /**
     * Afegeix una valoració a un producte donat el seu nom.
     *
     * @param nomProducte és el nom del producte.
     * @param valoracio és la valoració a afegir.
     */
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
                System.out.println("La sol·licitud GET va ser exitosa.");
            } else {
                System.out.println("La sol·licitud GET va fallar. Codi de resposta: " + responseCode);
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
            // Maneig d'errors (si cal)
        }
    }

    /**
     * Afegeix un producte a l'API amb valoracions.
     *
     * @param producte és el producte a afegir amb valoració.
     */
    private void afegirProducteV(JsonObject producte) {
        try {
            String id = "S1-Project_115";
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/" + id + "/products");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = producte.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = connection.getResponseCode();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println("Resposta del servidor: " + response.toString());
            }

            connection.disconnect();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La sol·licitud POST va ser exitosa.");
            } else {
                System.out.println("La sol·licitud POST va fallar. Codi de resposta: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
