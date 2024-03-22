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


}





