package es.mycloset.back;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OngMapper implements RowMapper<Ong> {
    @Override
    public Ong mapRow(ResultSet rs, int rowNum) throws SQLException {

        Ong ong = new Ong();
        ong.setIdOng(rs.getString("idOng"));
        ong.setNombre_ong(rs.getString("nombre_ong"));
        ong.setDireccion(rs.getString("direccion"));



        return ong;

    }
}
