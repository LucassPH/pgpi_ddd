package es.mycloset.back;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PedidosMapper implements RowMapper<Pedidos> {
    @Override
    public Pedidos mapRow(ResultSet rs, int rowNum) throws SQLException {

        Pedidos pedido = new Pedidos();
        pedido.setIdPedido(rs.getString("idPedido"));
        pedido.setIdOng(rs.getString("idOng"));
        pedido.setNuevo_pedido(rs.getInt("nuevo_pedido"));
        return pedido;
    }
}