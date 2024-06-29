package Persistance;

import Business.ProducteCataleg;
import Business.Tenda;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static Persistance.ProductsApi.readJsonArrayResponse;

public class ShopsApi implements ShopsDAO{

    /**
     * Comprova si existeix una botiga amb el nom de tenda especificat.
     *
     * Aquest mètode fa una sol·licitud GET per obtenir totes les botigues,
     * i verifica si alguna d'elles té el nom especificat.
     *
     * @param nom és el nom de la botiga a verificar
     * @return true si existeix una botiga amb el nom especificat, false si no existeix.
     */
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


    /**
     * Afegeix una nova tenda al sistema amb una sol·licitud POST a l'API.
     *
     * Aquest mètode envia una sol·licitud POST a l'API per afegir una nova tenda al sistema.
     * Converteix l'objecte Tenda a un format JSON abans d'enviar-lo com a body de la sol·licitud.
     *
     * @param tenda l'objecte Tenda a afegir al sistema
     * @return true si la tenda s'ha afegit amb èxit, false si hi ha hagut algun error durant la sol·licitud
     */
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


    /**
     * Verifica la consistència del preu d'un producte en una tenda específica.
     *
     * Aquest mètode verifica si el preu d'un producte específic en una tenda coincideix amb un nou preu donat.
     * Realitza una sol·licitud GET a l'API per obtenir les tendes i el seu catàleg de productes.
     *
     * @param nomT el nom de la tenda en què es vol verificar el preu del producte
     * @param nomP el nom del producte del qual es vol verificar el preu
     * @param preuNou el nou preu a comparar amb el preu actual del producte
     * @return true si el preu del producte és consistent amb el preu nou, false altrament o si hi ha errors durant la sol·licitud
     */
    @Override
    public boolean consistenciaPreu(String nomT, String nomP, float preuNou) {
        try {
            // Crear la URL
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");

            // Abrir conexión
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para una solicitud POST
            connection.setRequestMethod("GET");
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

    /**
     * Actualitza el catàleg d'una tenda amb un nou producte específic.
     *
     * Aquest mètode modifica el catàleg d'una tenda específica amb un nou producte,
     * afegint-lo al catàleg de la tenda.
     *
     * @param nomTenda el nom de la tenda on es vol actualitzar el catàleg
     * @param producte l'objecte ProducteCataleg que representa el nou producte a afegir
     */
    @Override
    public void actualitzarCataleg(String nomTenda, ProducteCataleg producte) {
        JsonObject tendaPerActualitzar = null;
        int idTenda = -1;
        try {
            // Crear la URL
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");

            // Abrir conexión
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para una solicitud GET
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            JsonArray jsonArray = readJsonArrayResponse(connection);

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject tenda = jsonArray.get(i).getAsJsonObject();
                String name = tenda.get("name").getAsString();

                if (name.equals(nomTenda)) {
                    tendaPerActualitzar = tenda;
                    idTenda = i;
                    break;
                }
            }

            if (tendaPerActualitzar != null) {
                // Modificar el catálogo de la tienda
                JsonArray cataleg = tendaPerActualitzar.get("catalogue").getAsJsonArray();
                cataleg.add(producte.toJsonObject());

                // URL para eliminar la tienda original
                URL deleteUrl = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops/" + idTenda);
                HttpURLConnection deleteConnection = (HttpURLConnection) deleteUrl.openConnection();
                deleteConnection.setRequestMethod("DELETE");

                int deleteResponseCode = deleteConnection.getResponseCode();
                deleteConnection.disconnect();

                if (deleteResponseCode == HttpURLConnection.HTTP_OK) {
                    // URL para recrear la tienda
                    URL postUrl = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");
                    HttpURLConnection postConnection = (HttpURLConnection) postUrl.openConnection();
                    postConnection.setRequestMethod("POST");
                    postConnection.setRequestProperty("Content-Type", "application/json");
                    postConnection.setDoOutput(true);

                    OutputStream os = postConnection.getOutputStream();
                    os.write(tendaPerActualitzar.toString().getBytes());
                    os.flush();
                    os.close();

                    int postResponseCode = postConnection.getResponseCode();

                    if (postResponseCode == HttpURLConnection.HTTP_OK || postResponseCode == HttpURLConnection.HTTP_CREATED) {
                        System.out.println("El catálogo se ha actualizado correctamente.");
                    } else {
                        System.out.println("Error al actualizar el catálogo. Código de respuesta: " + postResponseCode);
                    }

                    postConnection.disconnect();
                } else {
                    System.out.println("Error al eliminar la tienda original. Código de respuesta: " + deleteResponseCode);
                }
            } else {
                System.out.println("La tienda especificada no se encontró.");
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Retorna el catàleg de productes d'una tenda específica.
     *
     * Aquest mètode obté el catàleg de productes d'una tenda específica
     * a partir del nom de la tenda proporcionat.
     *
     * @param nomT el nom de la tenda de la qual es vol obtenir el catàleg
     * @return un JsonArray amb el catàleg de productes de la tenda, o null si no s'ha trobat la tenda
     */
    @Override
    public JsonArray productesCataleg(String nomT) {
        try {
            // Crear la URL para obtener todas las tiendas
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            // Leer la respuesta utilizando readJsonArrayResponse
            JsonArray jsonArray = readJsonArrayResponse(connection);
            connection.disconnect();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject tenda = jsonArray.get(i).getAsJsonObject();
                if (tenda.has("name") && !tenda.get("name").isJsonNull()){
                    String name = tenda.get("name").getAsString();

                    if (name.equals(nomT)) {
                        return tenda.get("catalogue").getAsJsonArray();
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Elimina un producte del catàleg d'una tenda especificada.
     *
     * Aquest mètode elimina un producte del catàleg d'una tenda específica
     * a partir del número d'índex del producte i el nom de la tenda.
     *
     * @param num número d'índex del producte a eliminar
     * @param nomT nom de la tenda de la qual s'eliminarà el producte
     */
    @Override
    public void eliminarProdCataleg(int num, String nomT) {
        int posicioTenda = -1;
        JsonObject tendaPerActualitzar = null;
        try {
            // Obtener todas las tiendas
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");

            // Leer la respuesta utilizando readJsonArrayResponse
            JsonArray jsonArray = readJsonArrayResponse(connection);
            connection.disconnect();

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject tenda = jsonArray.get(i).getAsJsonObject();
                String name = tenda.get("name").getAsString();

                if (name.equals(nomT)) {
                    tendaPerActualitzar = tenda;
                    posicioTenda = i;
                    break;
                }
            }

            if (tendaPerActualitzar != null) {
                // Modificar el catálogo de la tienda
                JsonArray cataleg = tendaPerActualitzar.get("catalogue").getAsJsonArray();
                cataleg.remove(num);

                // Eliminar la tienda original
                URL deleteUrl = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops/" + posicioTenda);
                HttpURLConnection deleteConnection = (HttpURLConnection) deleteUrl.openConnection();
                deleteConnection.setRequestMethod("DELETE");

                int deleteResponseCode = deleteConnection.getResponseCode();
                deleteConnection.disconnect();

                if (deleteResponseCode == HttpURLConnection.HTTP_OK) {
                    // Re-crear la tienda con el catálogo actualizado
                    URL postUrl = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");
                    HttpURLConnection postConnection = (HttpURLConnection) postUrl.openConnection();
                    postConnection.setRequestMethod("POST");
                    postConnection.setRequestProperty("Content-Type", "application/json");
                    postConnection.setDoOutput(true);

                    OutputStream os = postConnection.getOutputStream();
                    os.write(tendaPerActualitzar.toString().getBytes());
                    os.flush();
                    os.close();

                    int postResponseCode = postConnection.getResponseCode();

                    if (postResponseCode == HttpURLConnection.HTTP_OK || postResponseCode == HttpURLConnection.HTTP_CREATED) {
                        System.out.println("El producto se ha eliminado correctamente del catálogo.");
                    } else {
                        System.out.println("Error al actualizar el catálogo. Código de respuesta: " + postResponseCode);
                    }

                    postConnection.disconnect();
                } else {
                    System.out.println("Error al eliminar la tienda original. Código de respuesta: " + deleteResponseCode);
                }
            } else {
                System.out.println("La tienda especificada no se encontró.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna una llista de botigues que contenen un producte especificat en el seu catàleg.
     *
     * @param nomProducte el nom del producte a cercar en les botigues.
     * @return un JsonArray amb la informació de les botigues que contenen el producte especificat.
     */
    @Override
    public JsonArray productesTenda(String nomProducte) {
        JsonArray resultArray = new JsonArray();
        try {
            String apiUrl = "https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops";
            URL apiURL = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) apiURL.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            JsonArray shopsArray = readJsonArrayResponse(connection);

            int responseCode = connection.getResponseCode();

            connection.disconnect();

            if (responseCode == HttpURLConnection.HTTP_OK || responseCode == HttpURLConnection.HTTP_CREATED) {
                System.out.println("La solicitud GET de tiendas fue exitosa.");

                for (int i = 0; i < shopsArray.size(); i++) {
                    JsonObject shop = shopsArray.get(i).getAsJsonObject();
                    JsonArray catalogue = shop.getAsJsonArray("catalogue");

                    for (int j = 0; j < catalogue.size(); j++) {
                        JsonObject product = catalogue.get(j).getAsJsonObject();
                        String productName = product.get("nom").getAsString();

                        if (productName.equalsIgnoreCase(nomProducte)) {
                            JsonObject productInfo = new JsonObject();
                            productInfo.addProperty("nomTenda", shop.get("name").getAsString());
                            productInfo.addProperty("preu", product.get("preu").getAsFloat());
                            resultArray.add(productInfo);
                            break;
                        }
                    }
                }

            } else {
                System.out.println("La solicitud GET de tiendas falló. Código de respuesta: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultArray;
    }

    /**
     * Retorna una llista de totes les botigues disponibles.
     *
     * Aquest mètode fa una sol·licitud GET a l'API per obtenir un llistat de totes les botigues
     * i les retorna com un JsonArray.
     *
     * @return un JsonArray amb la informació de totes les botigues.
     */
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


    /**
     * Actualitza els ingressos d'una botiga especificada sumant els nous ingressos als ingressos actuals.
     *
     * Aquest mètode obté la informació de totes les botigues, cerca la botiga especificada pel seu nom,
     * actualitza els seus ingressos sumant els nous ingressos proporcionats, i finalment recrea la botiga
     * amb els ingressos actualitzats.
     *
     * @param nomTenda el nom de la botiga els ingressos de la qual s'han d'actualitzar
     * @param nuevosIngresos els nous ingressos que s'han d'afegir als ingressos actuals de la botiga
     * @return el valor dels ingressos actualitzats després de sumar els nous ingressos
     */
    @Override
    public float actualitzarIngresos(String nomTenda, float nuevosIngresos) {
        JsonObject tendaPerActualitzar = null;
        float earningsActual = 0.0f;
        float earningsActualizado = 0.0f;
        int posicioTenda = -1;

        try {
            // Crear la URL para obtener todas las tiendas
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");

            // Abrir conexión
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para una solicitud GET
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Leer la respuesta JSON en un JsonArray
            JsonArray jsonArray = readJsonArrayResponse(connection);

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject tenda = jsonArray.get(i).getAsJsonObject();
                String name = tenda.get("name").getAsString();

                if (name.equals(nomTenda)) {
                    tendaPerActualitzar = tenda;
                    earningsActual = tenda.get("earnings").getAsFloat();
                    posicioTenda = i;
                    break;
                }
            }

            if (tendaPerActualitzar != null && posicioTenda != -1) {
                // Sumar los nuevos ingresos a los ingresos actuales
                earningsActualizado = earningsActual + nuevosIngresos;
                tendaPerActualitzar.addProperty("earnings", earningsActualizado);

                // Eliminar la tienda original
                URL deleteUrl = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops/" + posicioTenda);
                HttpURLConnection deleteConnection = (HttpURLConnection) deleteUrl.openConnection();
                deleteConnection.setRequestMethod("DELETE");
                int deleteResponseCode = deleteConnection.getResponseCode();
                deleteConnection.disconnect();

                if (deleteResponseCode == HttpURLConnection.HTTP_OK) {
                    // URL para recrear la tienda
                    URL postUrl = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");
                    HttpURLConnection postConnection = (HttpURLConnection) postUrl.openConnection();
                    postConnection.setRequestMethod("POST");
                    postConnection.setRequestProperty("Content-Type", "application/json");
                    postConnection.setDoOutput(true);

                    OutputStream os = postConnection.getOutputStream();
                    os.write(tendaPerActualitzar.toString().getBytes());
                    os.flush();
                    os.close();

                    int postResponseCode = postConnection.getResponseCode();

                    if (postResponseCode == HttpURLConnection.HTTP_OK || postResponseCode == HttpURLConnection.HTTP_CREATED) {
                        System.out.println("Los ingresos se han actualizado correctamente.");
                    } else {
                        System.out.println("Error al actualizar los ingresos. Código de respuesta: " + postResponseCode);
                    }

                    postConnection.disconnect();
                } else {
                    System.out.println("Error al eliminar la tienda original. Código de respuesta: " + deleteResponseCode);
                }
            } else {
                System.out.println("La tienda especificada no se encontró.");
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return earningsActualizado;
    }

    /**
     * Obté el nom del patrocinador d'una botiga especificada pel seu nom.
     *
     * Aquest mètode fa una sol·licitud GET per obtenir totes les botigues,
     * cerca la botiga especificada pel seu nom, i retorna el nom del seu patrocinador si existeix.
     *
     * @param nomTenda el nom de la botiga de la qual es vol obtenir el patrocinador
     * @return el nom del patrocinador de la botiga especificada, o null si no té patrocinador o no es troba la botiga
     */
    @Override
    public String obtenerSponsor(String nomTenda){
        String sponsor = null;

        try {
            // Crear la URL para obtener todas las tiendas
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");

            // Abrir conexión
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para una solicitud GET
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Leer la respuesta JSON en un JsonArray
            JsonArray jsonArray = readJsonArrayResponse(connection);

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject tenda = jsonArray.get(i).getAsJsonObject();
                String name = tenda.get("name").getAsString();

                if (name.equals(nomTenda)) {
                    if (tenda.has("sponsor")) {
                        sponsor = tenda.get("sponsor").getAsString();
                    }
                    break;
                }
            }
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return sponsor;
    }

    /**
     * Obté el número de llindar d'una botiga especificada pel seu nom.
     *
     * Aquest mètode fa una sol·licitud GET per obtenir totes les botigues,
     * cerca la botiga especificada pel seu nom, i retorna el número del llindar si existeix.
     *
     * @param nomTenda el nom de la botiga de la qual es vol obtenir el patrocinador
     * @return el número del llindar de la botiga especificada, o -1 si no té llindar o no es troba la botiga
     */
    public float obtenerThreshold(String nomTenda){
        float loyaltyThreshold = 0;

        try {
            // Crear la URL para obtener todas las tiendas
            URL url = new URL("https://balandrau.salle.url.edu/dpoo/S1-Project_115/shops");

            // Abrir conexión
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configurar la conexión para una solicitud GET
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Leer la respuesta JSON en un JsonArray
            JsonArray jsonArray = readJsonArrayResponse(connection);

            for (int i = 0; i < jsonArray.size(); i++) {
                JsonObject tenda = jsonArray.get(i).getAsJsonObject();
                String name = tenda.get("name").getAsString();

                if (name.equals(nomTenda)) {
                    if (tenda.has("loyaltyThreshold")) {
                        loyaltyThreshold = tenda.get("loyaltyThreshold").getAsFloat();
                    }else{
                        loyaltyThreshold = -1;
                    }
                    break;
                }
            }
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return loyaltyThreshold;
    }
}
