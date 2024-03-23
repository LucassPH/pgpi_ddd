package es.mycloset.front;

public class Ong {

    String idOng;
    String nombre_ong;
    String direccion;


    public Ong(String idOng, String nombre_ong, String direccion) {
        this.idOng = idOng;
        this.nombre_ong = nombre_ong;
        this.direccion = direccion;
    }

    public String getIdOng() {
        return idOng;
    }

    public void setIdOng(String idOng) {
        this.idOng = idOng;
    }

    public String getNombre_ong() {
        return nombre_ong;
    }

    public void setNombre_ong(String nombre_ong) {
        this.nombre_ong = nombre_ong;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
