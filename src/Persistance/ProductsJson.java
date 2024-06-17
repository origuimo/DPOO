package Persistance;


import Business.Producte;

import Business.Valoracio;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

/**
 * Clase que implementa la interfaz ProductsDAO y gestiona la persistencia de productos en formato JSON.
 */
public class ProductsJson implements ProductsDAO {

    /**
     * Verifica la existencia de un producto en el sistema.
     * @param nom Nombre del producto a verificar.
     * @return true si el producto existe, false de lo contrario.
     */
    @Override
    public boolean comprovarExistencia(String nom) {
        Path filePath = Paths.get("src/Arxius/products.json");

        return isItem(nom, filePath);
    }

    /**
     * Método estático que verifica la existencia de un producto en un archivo JSON.
     * @param nom Nombre del producto a verificar.
     * @param filePath Ruta del archivo JSON.
     * @return true si el producto existe, false de lo contrario.
     */
    static boolean isItem(String nom, Path filePath) {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            Gson gson = new Gson();

            JsonArray productList = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < productList.size(); i++) {
                JsonObject product = productList.get(i).getAsJsonObject();
                String name = product.get("name").getAsString();

                if (name.equals(nom)) {
                    return true;
                }
            }
        } catch (IOException e) {
            return false;
        }
        return false;
    }

    /**
     * Agrega un nuevo producto al sistema.
     * @param producte Instancia del producto a ser agregado.
     * @return true si la adición es exitosa, false de lo contrario.
     */
    @Override
    public boolean afegirProducte(Producte producte) {
        String rutaArchivo = "src/Arxius/products.json";

        JsonArray jsonArrayActual = llegirArxiu(rutaArchivo);

        jsonArrayActual.add(producte.toJsonObject());

        try (FileWriter fileWriter = new FileWriter(rutaArchivo)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fileWriter.write(gson.toJson(jsonArrayActual));
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Obtiene un array JSON que representa la lista de productos en el sistema.
     * @return JsonArray que contiene la información de los productos.
     */
    @Override
    public JsonArray llistarProductes() {
        Path filePath = Paths.get("src/Arxius/products.json");
        JsonArray productList = new JsonArray();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            Gson gson = new Gson();

            productList = gson.fromJson(reader, JsonArray.class);

        } catch (IOException e) {
            return productList;
        }
        return productList;
    }

    /**
     * Elimina un producto del sistema dado su número.
     * @param numero Número que identifica al producto a ser eliminado.
     */
    @Override
    public  void eliminarProducte(int numero) {
        // Ruta del archivo JSON
        String rutaArchivo = "src/Arxius/products.json";
        JsonArray productList = new JsonArray();
        try (FileReader reader = new FileReader(rutaArchivo)) {
            Gson gson = new Gson();
            productList = gson.fromJson(reader, JsonArray.class);
            productList.remove(numero);
            try (FileWriter fileWriter = new FileWriter(rutaArchivo)) {
                Gson gson1 = new GsonBuilder().setPrettyPrinting().create();
                fileWriter.write(gson1.toJson(productList));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método estático que lee un archivo JSON y devuelve un JsonArray.
     * @param rutaArchivo Ruta del archivo JSON.
     * @return JsonArray que contiene la información del archivo.
     */
    static JsonArray llegirArxiu(String rutaArchivo) {
        try (FileReader reader = new FileReader(rutaArchivo)) {

            Type tipoJsonArray = new TypeToken<JsonArray>() {}.getType();

            return new Gson().fromJson(reader, tipoJsonArray);
        } catch (IOException e) {
            return new JsonArray();
        }
    }

    /**
     * Comprueba el precio de un producto en el sistema.
     * @param nom Nombre del producto cuyo precio se va a comprobar.
     * @return Precio del producto.
     */
    @Override
    public float comprovarPreu(String nom){
        Path filePath = Paths.get("src/Arxius/products.json");
        try (Reader reader = Files.newBufferedReader(filePath)) {
            Gson gson = new Gson();

            JsonArray productList = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < productList.size(); i++) {
                JsonObject product = productList.get(i).getAsJsonObject();
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
     * Obtiene la marca de un producto en el sistema.
     * @param nomP Nombre del producto cuya marca se va a obtener.
     * @return Marca del producto.
     */
    @Override
    public String obtenirMarca(String nomP){
        Path filePath = Paths.get("src/Arxius/products.json");
        String marca = null;
        try (Reader reader = Files.newBufferedReader(filePath)) {
            Gson gson = new Gson();

            JsonArray productList = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < productList.size(); i++) {
                JsonObject product = productList.get(i).getAsJsonObject();
                String name = product.get("name").getAsString();

                if (name.equals(nomP)) {
                    return product.get("brand").getAsString();
                }
            }
        } catch (IOException e) {
            return marca;
        }
        return marca;

    }

    /**
     * Busca productos en el sistema que coincidan con la consulta proporcionada.
     * @param query Consulta para buscar productos por nombre o marca.
     * @return JsonArray que contiene los productos que coinciden con la consulta.
     *         Si hay un error de lectura o la consulta no produce resultados, retorna null.
     */
    @Override
    public JsonArray llistaDeResultats(String query) {
        Path filePath = Paths.get("src/Arxius/products.json");
        JsonArray totalProductes = new JsonArray();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            Gson gson = new Gson();

            JsonArray productList = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < productList.size(); i++) {
                JsonObject product = productList.get(i).getAsJsonObject();
                String productName = product.get("name").getAsString();
                String brandName = product.get("brand").getAsString();
                if (productName.equalsIgnoreCase(query) || productName.toLowerCase().contains(query.toLowerCase()) || brandName.equals(query)) {
                    totalProductes.add(product);
                }
            }
            return totalProductes;
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Obtiene las valoraciones de un producto en el sistema.
     * @param nomProducte Nombre del producto del cual se obtendrán las valoraciones.
     * @return JsonArray que contiene las valoraciones del producto.
     */
    @Override
    public JsonArray llistarValoracions(String nomProducte) {
        Path filePath = Paths.get("src/Arxius/products.json");
        try (Reader reader = Files.newBufferedReader(filePath)) {
            Gson gson = new Gson();

            JsonArray productList = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < productList.size(); i++) {
                JsonObject product = productList.get(i).getAsJsonObject();
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
     * Agrega una valoración a un producto en el sistema.
     * @param nomProducte Nombre del producto al cual se le agregará la valoración.
     * @param valoracio Objeto Valoracio que contiene la valoración a agregar.
     */
    @Override
    public void afegirValoracioJson(String nomProducte, Valoracio valoracio) {
        String rutaArchivo = "src/Arxius/products.json";

        try (FileReader reader = new FileReader(rutaArchivo)) {
            Gson gson = new Gson();
            JsonArray llistaProductes = gson.fromJson(reader, JsonArray.class);

            for (int i = 0; i < llistaProductes.size(); i++) {
                JsonObject producte = llistaProductes.get(i).getAsJsonObject();
                String name = producte.get("name").getAsString();

                if (name.equals(nomProducte)) {
                    JsonArray valoracion = producte.get("reviews").getAsJsonArray();
                    valoracion.add(valoracio.toJsonObject());
                }
            }

            try (FileWriter fileWriter = new FileWriter(rutaArchivo)) {
                fileWriter.write(gson.toJson(llistaProductes));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
