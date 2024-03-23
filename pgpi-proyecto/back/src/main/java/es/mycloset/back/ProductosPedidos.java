package es.mycloset.back;

public class ProductosPedidos {
    String idProductoPedido;
    String nombre_producto;
    Float cantidad;


    public String getIdProductoPedido() {
        return idProductoPedido;
    }

    public void setIdProductoPedido(String idProductoPedido) {
        this.idProductoPedido = idProductoPedido;
    }

    public String getNombre_producto() {
        return nombre_producto;
    }

    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }
}
