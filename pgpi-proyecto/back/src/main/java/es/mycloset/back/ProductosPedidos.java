package es.mycloset.back;

public class ProductosPedidos {
    String idProductoPedido;
    String nombre_producto;
    String cantidad;


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

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}
