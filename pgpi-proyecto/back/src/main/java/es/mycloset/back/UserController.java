package es.mycloset.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UserController {

    Info dataService = new Info();
    ReadJson reader = new ReadJson();

    public String Data_FILE_PATH = "Data/cp-national-datafile.json";
    public static final String Data_FILE_PATH_2 = "Data/MsCode_json.json";


    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getUsers() {
        return userService.getUsers();
    }


    @PostMapping("/users")
    public ResponseEntity<String> handleUserPost(@RequestBody User userPost) {
        return userService.handleUserPost(userPost);
    }

    @PostMapping("/usersp")
    public ResponseEntity<String> insUserPost(@RequestBody User userPost) {
        return userService.insertUserPost(userPost);
    }


    @GetMapping("/Data")
    public ArrayList<NationalDatafileJson> users() {
        ReadJson reader = new ReadJson();
        ArrayList<NationalDatafileJson> prod = reader.readJsonFile(Data_FILE_PATH);
        return prod;
    }

    @GetMapping("/Data/{id}")
    public ResponseEntity<NationalDatafileJson> getDataById(@PathVariable String id) {
        ArrayList<NationalDatafileJson> prodList = reader.readJsonFile(Data_FILE_PATH);

        // Buscar el elemento con la ID proporcionada
        Optional<NationalDatafileJson> result = prodList.stream()
                .filter(data -> data.getId().equals(id))
                .findFirst();

        // Verificar si se encontró el elemento
        if (result.isPresent()) {
            return new ResponseEntity<>(result.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/DataByMsCode/{mscode}")
    public ResponseEntity<ArrayList<NationalDatafileJson>> getDataByMsCode(@PathVariable String mscode) {
        Map<String, ArrayList<NationalDatafileJson>> dataMap = reader.readJsonFileWithSections(Data_FILE_PATH_2);

        // Obtener la lista correspondiente al mscode seleccionado
        ArrayList<NationalDatafileJson> selectedDataList = dataMap.get(mscode);

        if (selectedDataList == null) {
            // Si la clave no se encuentra en el mapa, devuelve un ResponseEntity con HttpStatus.NOT_FOUND
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(selectedDataList, HttpStatus.OK);
    }


    @PostMapping(path = "/Data", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ArrayList<NationalDatafileJson> crear(@RequestBody NationalDatafileJson newNationalDatafileJson) {
        ArrayList<NationalDatafileJson> lst = dataService.addprod(newNationalDatafileJson);
        return lst;
    }

    @PutMapping("/Data/{id}")
    public ResponseEntity<ArrayList<NationalDatafileJson>> actualizar(@PathVariable("id") String id,
                                                                      @RequestBody NationalDatafileJson NationalDatafileJsonActualizado) {
        System.out.println("nuevo NationalDatafileJson:" + NationalDatafileJsonActualizado.getId());
        ArrayList<NationalDatafileJson> lst = dataService.modificar(id, NationalDatafileJsonActualizado);


        if (lst != null) {
            return new ResponseEntity<>(lst, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/Data/{id}")
    public ResponseEntity<ArrayList<NationalDatafileJson>> eliminar(@PathVariable String id) {
        System.out.println(id);
        ArrayList<NationalDatafileJson> lst = dataService.deleteprod(id);
        if (lst != null) {
            return new ResponseEntity<>(lst, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}