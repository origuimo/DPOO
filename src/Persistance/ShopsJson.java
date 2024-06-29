package Persistance;

import Business.ProducteCataleg;
import Business.Tenda;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static Persistance.ProductsJson.isItem;
import static Persistance.ProductsJson.llegirArxiu;

/**
 * Clase que implementa la interfaz ShopsDAO y gestiona la persistencia de tiendas en formato JSON.
 */
public class ShopsJson implements ShopsDAO{

    /**
     * Verifica la existencia de una tienda en el sistema.
     * @param nom Nombre de la tienda a verificar.
     * @return true si la tienda existe, false en caso contrario.
     */
    @Override
    public boolean comprovarExistenciaTenda(String nom){
        Path filePath = Paths.get("src/Arxius/shops.json");
        return isItem(nom, filePath);
    }

    /**
     * Añade una nueva tienda al sistema.
     * @param tenda Objeto Tenda que representa la tienda a añadir.
     * @return true si la operación fue exitosa, false en caso contrario.
     */
    @Override
    public boolean afegirTenda(Tenda tenda) {
        String rutaArchivo = "src/Arxius/shops.json";

        JsonArray jsonArrayActual = llegirArxiu(rutaArchivo);

        jsonArrayActual.add(tenda.toJsonObject());

        try (FileWriter fileWriter = new FileWriter(rutaArchivo)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fileWriter.write(gson.toJson(jsonArrayActual));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Verifica la consistencia del precio de un producto en una tienda.
     * @param nomT Nombre de la tienda.
     * @param nomP Nombre del producto.
     * @param preuNou Nuevo precio del producto.
     * @return true si el precio es consistente, false en caso contrario.
     */
    @Override
    public  boolean consistenciaPreu(String nomT, String nomP, float preuNou){
        Path filePath = Paths.get("src/Arxius/shops.json");
        try (Reader reader = Files.newBufferedReader(filePath)) {
            Gson gson = new Gson();

            JsonArray shoplist = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < shoplist.size(); i++) {
                JsonObject shop = shoplist.get(i).getAsJsonObject();
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
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    /**
     * Actualiza el catálogo de una tienda con un nuevo producto.
     * @param nomTenda Nombre de la tienda.
     * @param producte Producto a agregar al catálogo.
     */
    @Override
    public void actualitzarCataleg(String nomTenda, ProducteCataleg producte){

        String rutaArchivo = "src/Arxius/shops.json";

        try (FileReader reader = new FileReader(rutaArchivo)) {
            Gson gson = new Gson();
            JsonArray llistaTendes = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < llistaTendes.size(); i++) {
                JsonObject tenda = llistaTendes.get(i).getAsJsonObject();
                String name = tenda.get("name").getAsString();

                if (name.equals(nomTenda)) {
                    JsonArray cataleg = tenda.get("catalogue").getAsJsonArray();
                    cataleg.add(producte.toJsonObject());
                }
            }

            try (FileWriter fileWriter = new FileWriter(rutaArchivo)) {
                fileWriter.write(gson.toJson(llistaTendes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Obtiene la lista de productos en el catálogo de una tienda.
     * @param nomT Nombre de la tienda.
     * @return JsonArray que contiene los productos en el catálogo de la tienda.
     */
    @Override
    public JsonArray productesCataleg(String nomT){
        Path filePath = Paths.get("src/Arxius/shops.json");
        JsonArray shopsList = new JsonArray();
        JsonArray cataleg = new JsonArray();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            Gson gson = new Gson();

            shopsList = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < shopsList.size(); i++) {
                JsonObject shop = shopsList.get(i).getAsJsonObject();
                String name = shop.get("name").getAsString();

                if (name.equals(nomT)) {
                    cataleg = shop.get("catalogue").getAsJsonArray();
                    return cataleg;
                }
            }
        } catch (IOException e) {
            return cataleg;
        }
        return cataleg;
    }

    /**
     * Elimina un producto del catálogo de una tienda.
     * @param num Número del producto a eliminar.
     * @param nomT Nombre de la tienda.
     */
    @Override
    public void eliminarProdCataleg(int num, String nomT) {
        String rutaArchivo = "src/Arxius/shops.json";
        JsonArray shopList;

        try (FileReader reader = new FileReader(rutaArchivo)) {
            Gson gson = new Gson();
            shopList = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < shopList.size(); i++) {
                JsonObject shop = shopList.get(i).getAsJsonObject();
                String name = shop.get("name").getAsString();

                if (name.equals(nomT)) {
                    JsonArray cataleg = shop.get("catalogue").getAsJsonArray();
                    if (num >= 0 && num < cataleg.size()) {
                        cataleg.remove(num);
                    }
                }
            }

            // Ahora escribimos la lista completa de tiendas, incluyendo la actualización, en el archivo
            try (FileWriter fileWriter = new FileWriter(rutaArchivo)) {
                Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
                fileWriter.write(gson1.toJson(shopList));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene la lista de productos en una tienda.
     * @param nomProducte Nombre del producto.
     * @return JsonArray que contiene los productos en la tienda.
     */
    @Override
    public JsonArray productesTenda(String nomProducte) {
        Path filePath = Paths.get("src/Arxius/shops.json");
        JsonArray totalProductes = new JsonArray();

        try (Reader reader = Files.newBufferedReader(filePath)) {
            Gson gson = new Gson();

            JsonArray llistaTendes = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < llistaTendes.size(); i++) {
                JsonObject tenda = llistaTendes.get(i).getAsJsonObject();
                JsonArray cataleg = tenda.get("catalogue").getAsJsonArray();
                String nomTenda = tenda.get("name").getAsString();
                for (int j = 0; j < cataleg.size(); j++){
                    JsonObject producte = cataleg.get(j).getAsJsonObject();
                    String nomP = producte.get("nom").getAsString();
                    if(nomP.equals(nomProducte)){
                        producte.addProperty("nomTenda", nomTenda);
                        totalProductes.add(producte);
                    }
                }
            }
            return totalProductes;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Obtiene la lista de todas las tiendas en el sistema.
     * @return JsonArray que contiene la información de todas las tiendas.
     */
    @Override
    public JsonArray llistaTendes() {
            Path filePath = Paths.get("src/Arxius/shops.json");
            JsonArray shopList = new JsonArray();
            try (Reader reader = Files.newBufferedReader(filePath)) {
                Gson gson = new Gson();

                shopList = gson.fromJson(reader, JsonArray.class);

            } catch (IOException e) {
                return shopList;
            }
            return shopList;
    }

    /**
     * Actualiza los ingresos de una tienda después de una compra.
     * @param nomT Nombre de la tienda.
     * @param carret Monto de la compra realizada.
     * @return Nuevo monto de ingresos de la tienda.
     */
    @Override
    public float actualitzarIngresos(String nomT, float carret) {
        Path filePath = Paths.get("src/Arxius/shops.json");
        float ingresos = 0;
        try (Reader reader = Files.newBufferedReader(filePath)) {
            Gson gson = new Gson();

            JsonArray shoplist = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < shoplist.size(); i++) {
                JsonObject shop = shoplist.get(i).getAsJsonObject();
                String name = shop.get("name").getAsString();

                if (name.equals(nomT)) {
                    ingresos = shop.get("earnings").getAsFloat();
                    ingresos += carret;

                    shop.addProperty("earnings", ingresos);
                    break;
                }
            }
            try (Writer writer = Files.newBufferedWriter(filePath)) {
                gson.toJson(shoplist, writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ingresos;
    }

    /**
     * Obtiene el sponsor de una tienda específica.
     *
     * @param nomTenda es el Nombre de la tienda de la cual se desea obtener el patrocinador.
     * @return nombre de tipo String es el nombre del patrocinador de la tienda. Devuelve null si la tienda no tiene patrocinador o si ocurre un error.
     */
    public String obtenerSponsor(String nomTenda) {
        String sponsor = null;

            Path filePath = Paths.get("src/Arxius/shops.json");

            try (Reader reader = Files.newBufferedReader(filePath)) {
                Gson gson = new Gson();

                JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

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
            }catch (Exception e) {
              e.printStackTrace();
            }

        return sponsor;
    }

    public float obtenerThreshold(String nomTenda) {
        float loyaltyThreshold = 0;

        Path filePath = Paths.get("src/Arxius/shops.json");

        try (Reader reader = Files.newBufferedReader(filePath)) {
            Gson gson = new Gson();

            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);

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
        }catch (Exception e) {
            e.printStackTrace();
        }

        return loyaltyThreshold;
    }
}
