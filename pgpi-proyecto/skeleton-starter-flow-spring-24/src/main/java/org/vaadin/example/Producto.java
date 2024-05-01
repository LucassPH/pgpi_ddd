package org.vaadin.example;

public class Producto {
    String nombre;
    int tiempo;
    int cantidad;
    int cantidadres;

    public Producto() {
    }

    public Producto(String nombre, int tiempo, int cantidad, int cantidadres) {
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.cantidad = cantidad;
        this.cantidadres = cantidadres;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadres() {
        return cantidadres;
    }

    public void setCantidadres(int cantidadres) {
        this.cantidadres = cantidadres;
    }
}
