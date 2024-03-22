package es.mycloset.back;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class ReadJson {

    public ArrayList<NationalDatafileJson> readJsonFile(String fichero) {
        try {
            // Lee el fichero que le pasemos y lo carga en un reader
            Reader reader = Files.newBufferedReader(Paths.get(fichero));
            // Convierte el array JSON a un arraylist de datos
            ArrayList<NationalDatafileJson> prods = new Gson().fromJson(reader, new TypeToken<ArrayList<NationalDatafileJson>>() {}.getType());
            reader.close(); // Cierra el reader
            return prods;

        } catch (Exception ex) {
            ex.printStackTrace();
            return new ArrayList<>(); // Si no ha leído nada, devuelve un array vacío
        }
    }

    public Map<String, ArrayList<NationalDatafileJson>> readJsonFileWithSections(String fichero) {
        try {
            // Lee el fichero que le pasemos y lo carga en un reader
            Reader reader = Files.newBufferedReader(Paths.get(fichero));
            // Convierte el JSON a un mapa de claves a listas de NationalDatafileJson
            Map<String, ArrayList<NationalDatafileJson>> dataMap = new Gson().fromJson(reader, new TypeToken<HashMap<String, ArrayList<NationalDatafileJson>>>() {}.getType());
            reader.close(); // Cierra el reader
            return dataMap;

        } catch (Exception ex) {
            ex.printStackTrace();
            return new HashMap<>(); // Si no ha leído nada, devuelve un mapa vacío
        }
    }
    public boolean writeJsonFile(String fichero, ArrayList<NationalDatafileJson> users) {
        try {
            // Abre el fichero para escritura
            Writer writer = Files.newBufferedWriter(Paths.get(fichero));

            // Escribe el contenido del arraylist en el fichero
            writer.write(new Gson().toJson(users));

            writer.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
}
