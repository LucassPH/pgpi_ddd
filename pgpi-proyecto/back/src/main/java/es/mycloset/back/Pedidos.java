package es.mycloset.back;

public class Pedidos {
    String idPedido;
    String idOng;
    int nuevo_pedido;

    public String getIdPedido() {
        return idPedido;
    }
    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }
    public String getIdOng() {
        return idOng;
    }
    public void setIdOng(String idOng) {
        this.idOng = idOng;
    }
    public int getNuevo_pedido() {
        return nuevo_pedido;
    }
    public void setNuevo_pedido(int nuevo_pedido) {
        this.nuevo_pedido = nuevo_pedido;
    }
}