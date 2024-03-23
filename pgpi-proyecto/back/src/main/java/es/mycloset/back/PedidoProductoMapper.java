package es.mycloset.back;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidoProductoMapper implements RowMapper<PedidoProducto> {
    @Override
    public PedidoProducto mapRow(ResultSet rs, int rowNum) throws SQLException {

        PedidoProducto pedidoProducto = new PedidoProducto();
        pedidoProducto.setIdPedido(rs.getString("idPedido"));
        pedidoProducto.setIdProductoPedido(rs.getString("idProductoPedido"));
        return pedidoProducto;
    }
}