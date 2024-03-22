package es.mycloset.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;
    public List<User> getUsers() {
        return userDAO.getUsers();
    }


    public ResponseEntity<String> handleUserPost(User userPost) {


        User existingUser = userDAO.getUserByUsername(userPost.getUsername());
        System.out.println(existingUser);
        if (existingUser == null) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("null");
        } else if (existingUser.getPassword().equals(userPost.getPassword())) {
            return ResponseEntity.ok(existingUser.getUsername());
        } else {
            System.out.println("2");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("null"); /* DEVUELVEN LA MISMA EXCEPCION
                PARA NO SABER SI ES EL USUARIO O LA CONTRA LO INCORRECTO*/
        }
    }
    public ResponseEntity<String> insertUserPost(User userPost) {
        User existingUser = userDAO.getUserByUsername(userPost.getUsername());
        if (existingUser == null) {
            User createdUser = userDAO.insertUser(userPost.getIdUsuario(),userPost.getUsername(), userPost.getPassword(), userPost.getTipo_usuario());
            return ResponseEntity.ok("bien");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("mal");
        } /*SI USUArIO NO EXISTE LO INSERTA SI EXISTE DEVUELVE EXCEPCION*/
    }
}