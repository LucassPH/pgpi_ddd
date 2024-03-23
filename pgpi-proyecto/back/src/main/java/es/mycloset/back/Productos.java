package es.mycloset.back;

public class Productos {

    String idProducto;
    String nombre_producto;
    Float stock;

    public String getIdProducto() {
        return idProducto;
    }
    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }
    public String getNombre_producto() {
        return nombre_producto;
    }
    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }
    public Float getStock() {
        return stock;
    }
    public void setStock(Float stock) {
        this.stock = stock;
    }
}