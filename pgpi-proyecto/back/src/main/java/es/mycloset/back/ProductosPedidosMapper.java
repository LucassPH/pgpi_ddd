package es.mycloset.back;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductosPedidosMapper implements RowMapper<ProductosPedidos> {
    @Override
    public ProductosPedidos mapRow(ResultSet rs, int rowNum) throws SQLException {

        ProductosPedidos productosPedidos = new ProductosPedidos();
        productosPedidos.setIdProductoPedido(rs.getString("idProductoPedido"));
        productosPedidos.setNombre_producto(rs.getString("nombre_producto"));
        productosPedidos.setCantidad(rs.getString("cantidad"));
        return productosPedidos;
    }
}