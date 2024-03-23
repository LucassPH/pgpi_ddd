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

        if (existingUser == null) {
            // El usuario no existe, devolver un estado UNAUTHORIZED con el tipo de usuario "null"
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("null");
        } else if (existingUser.getPassword().equals(userPost.getPassword())) {
            // La contraseña coincide, devolver el tipo de usuario en el cuerpo de la respuesta
            return ResponseEntity.ok(String.valueOf(existingUser.getTipo_usuario()));
        } else {
            // La contraseña no coincide, devolver un estado UNAUTHORIZED con el tipo de usuario "null"
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("null");
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

    public Productos getProductoById(String productoId){return userDAO.getProductoId(productoId);}
    public Pedidos getPedidoById(String pedidoId){return userDAO.getPedidoId(pedidoId);}
    public List<Pedidos> getPedidos(String localId){return userDAO.getPedidos(localId);}
    public List<Productos> getProductos(){return userDAO.getProductos();}
    public List<ProductosPedidos> getProductosPedidos(){return userDAO.getProductosPedidos();}
    public List<PedidoProducto> getPedidoProducto(){return userDAO.getPedidoProducto();}
    public void insertarPedido(Pedidos Pedido){ userDAO.insertarPedido(Pedido);}
    public void insertarProductosPedidos(ProductosPedidos Producto){ userDAO.insertarProductosPedidos(Producto);}
    public void insertarPedidoProducto(PedidoProducto PedidoProducto){ userDAO.insertarPedidoProducto(PedidoProducto);}
    public void actualizarPedidoAntiguo(String idPedido ){ userDAO.actualizarPedidoAntiguo(idPedido);}
    public void actualizarProducto(String idProducto, float Stock ){ userDAO.actualizarProducto(idProducto,Stock);}

}