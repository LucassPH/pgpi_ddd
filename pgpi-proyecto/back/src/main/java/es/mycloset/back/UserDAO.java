package es.mycloset.back;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> getUsers() {
        String sql = "SELECT * FROM usuario";

        List<User> usrs = jdbcTemplate.query(
                sql,
                new UserMapper());
        return usrs;
    }
    public User insertUser(String idUsuario,String username, String password, int tipoUsuario) {
        String sql = "INSERT INTO usuario (idUsuario,nombre_usuario, contrasena, tipoUsuario) VALUES (?,?,?, ?)";
        User ins = new User();
        ins.setUsername(username);
        ins.setPassword(password);
        int rowsInserted = jdbcTemplate.update(sql, idUsuario,username, password, tipoUsuario);
        if (rowsInserted > 0) {
            System.out.println("Usuario insertado");
        }
    return ins;}

    public User getUserByUsername(String username) {
            String sql = "SELECT * FROM usuario WHERE nombre_usuario = ?";

            List<User> users = jdbcTemplate.query(
                    sql,
                    new Object[]{username},
                    new UserMapper());

            if (users.isEmpty()) {
                return null;
            } else {
                return users.get(0);
            }
        }
    public List<Pedidos> getPedidos(String idOng){
        String sql = "SELECT * FROM pedido where idOng = ?";
        return jdbcTemplate.query(
                sql,
                new Object[]{idOng},
                new PedidosMapper());
    }
    public List<Productos> getProductos(){
        String sql = "SELECT * FROM productos";
        return jdbcTemplate.query(
                sql,
                new Object[]{},
                new ProductosMapper());
    }
    public List<ProductosPedidos> getProductosPedidos(){
        String sql = "SELECT * FROM productopedido";
        return jdbcTemplate.query(
                sql,
                new Object[]{},
                new ProductosPedidosMapper());
    }
    public List<PedidoProducto> getPedidoProducto(){
        String sql = "Select * FROM pedidoproducto";
        return jdbcTemplate.query(
                sql,
                new PedidoProductoMapper());
    }

    public Pedidos getPedidoId(String idPedido){
        String sql = "Select * FROM pedido where idPedido = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{idPedido},
                new PedidosMapper());
    }
    public Productos getProductoId(String idProducto){
        String sql = "Select * FROM productos where idProducto = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{idProducto},
                new ProductosMapper());
    }

    public void insertarPedido(Pedidos pedido){
        String sql ="INSERT INTO pedido (idPedido, idOng, nuevo_pedido) VALUES (?,?,?)";
        jdbcTemplate.update(sql, pedido.getIdPedido(), pedido.getIdOng(), pedido.getNuevo_pedido());
    }
    public void insertarProductosPedidos(ProductosPedidos productosPedidos){
        String sql ="INSERT INTO productopedido (idPedido,nombre_producto, cantidad) VALUES (?,?,?)";
        jdbcTemplate.update(sql, productosPedidos.getIdProductoPedido(), productosPedidos.getNombre_producto(), productosPedidos.getCantidad());
    }
    public void insertarPedidoProducto(PedidoProducto pedidoProducto){
        String sql ="INSERT INTO pedidoproducto (idPedido,idProductoPedido) VALUES (?,?)";
        jdbcTemplate.update(sql, pedidoProducto.getIdPedido(), pedidoProducto.getIdProductoPedido());
    }

    public void actualizarPedidoAntiguo(String idPedido){
        String sql ="UPDATE pedido SET nuevo_pedido = 0 where idPedido = ?";
        jdbcTemplate.update(sql, idPedido);
    }
    public void actualizarProducto(String idProducto, float stock){
        String sql ="UPDATE productos SET stock = ? where idProducto = ?";
        jdbcTemplate.update(sql, idProducto, stock);
    }
}