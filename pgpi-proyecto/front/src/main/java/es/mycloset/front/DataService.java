package es.mycloset.front;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.googlecode.gentyref.TypeToken;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class DataService implements Serializable {


    private final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();
    HttpRequest request;
    HttpClient client = HttpClient.newBuilder().build();
    HttpResponse<String> response;


    public String getZona() {
        try {
            String resource = "http://localhost:8887/Data";
            //System.out.println(resource);
            request = HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //System.out.println(response.body());
        } catch (URISyntaxException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

    public NationalDatafileJson getZonaById(String id) {
        try {
            String resource = "http://localhost:8887/Data/" + id;
            request = HttpRequest.newBuilder(new URI(resource))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create();

                return gson.fromJson(response.body(), NationalDatafileJson.class);
            } else {
                // Manejar el caso en que la respuesta no sea exitosa (código de estado diferente de 200)
                System.err.println("Error al obtener datos. Código de estado: " + response.statusCode());
                return null; // o lanzar una excepción según sea necesario
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            // Manejar las excepciones de manera adecuada
            e.printStackTrace();
            return null; // o lanzar una excepción según sea necesario
        }
    }

    public List<NationalDatafileJson> postnuevo(NationalDatafileJson Nueva) {
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8887/Data"))
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(gson.toJson(Nueva)))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Obtener la lista completa desde la respuesta
        return gson.fromJson(response.body(), new com.googlecode.gentyref.TypeToken<ArrayList<NationalDatafileJson>>(){}.getType());
    }


    public boolean eliminar(String id) {
        try {
            String resource = "http://localhost:8887/Data/" + id;
            request = HttpRequest.newBuilder(new URI(resource))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == HttpStatus.OK.value();
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void update(String id, NationalDatafileJson nationalDatafileJson) {
        try {
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8887/Data/" + id))
                    .header("Content-Type", "application/json")
                    .method("PUT", HttpRequest.BodyPublishers.ofString(gson.toJson(nationalDatafileJson)))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<NationalDatafileJson> getDataByMsCode(String mscode) {
        try {
            String resource = "http://localhost:8887/DataByMsCode/" + mscode;
            HttpRequest request = HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Imprimir la respuesta JSON antes de la deserialización
            System.out.println("Respuesta JSON recibida: " + response.body());

            try {
                // Intentar deserializar la respuesta JSON
                ArrayList<NationalDatafileJson> data = gson.fromJson(response.body(), new TypeToken<ArrayList<NationalDatafileJson>>() {}.getType());
                return data;

            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                System.out.println("Error de deserialización: " + e.getMessage());
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

        // Si hay un error, devolver una lista vacía o manejar según tus necesidades
        return new ArrayList<>();
    }
}