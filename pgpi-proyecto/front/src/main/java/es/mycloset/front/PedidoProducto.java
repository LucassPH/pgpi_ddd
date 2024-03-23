package es.mycloset.front;

public class PedidoProducto {
    String idPedido;
    String idProductoPedido;

    public PedidoProducto(String idPedido, String idProductoPedido) {
        this.idPedido = idPedido;
        this.idProductoPedido = idProductoPedido;
    }

    public String getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(String idPedido) {
        this.idPedido = idPedido;
    }

    public String getIdProductoPedido() {
        return idProductoPedido;
    }

    public void setIdProductoPedido(String idProductoPedido) {
        this.idProductoPedido = idProductoPedido;
    }
}
