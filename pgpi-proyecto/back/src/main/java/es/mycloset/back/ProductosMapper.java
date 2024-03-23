package es.mycloset.back;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductosMapper implements RowMapper<Productos> {
    @Override
    public Productos mapRow(ResultSet rs, int rowNum) throws SQLException {

        Productos producto = new Productos();
        producto.setIdProducto(rs.getString("idProducto"));
        producto.setNombre_producto(rs.getString("nombre_producto"));
        producto.setStock(rs.getFloat("stock"));
        return producto;
    }
}