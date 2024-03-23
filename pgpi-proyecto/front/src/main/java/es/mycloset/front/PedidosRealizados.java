package es.mycloset.front;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.googlecode.gentyref.TypeToken;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class PedidosRealizados extends VerticalLayout {
    private VerticalLayout option2Cont;
    private Grid<Pedidos> grid;
    private DataService dataService;
    private Gson gson;

    public PedidosRealizados(@Autowired DataService dataService){
        this.dataService = dataService;
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        String response = dataService.getPedidos();
        Type listType = new TypeToken<ArrayList<Pedidos>>() {}.getType();
        ArrayList<Pedidos> pedidos = gson.fromJson(response, listType);
        ArrayList<Pedidos> pedidosFiltrados = new ArrayList<>();

        for (Pedidos pedido : pedidos) {
            if (pedido.getNuevo_pedido() == 0) {
                pedidosFiltrados.add(pedido);
            }
        }
        option2Cont = new VerticalLayout();

        // Create and configure the grid
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.setItems(pedidosFiltrados);
        grid.addColumn(Pedidos::getIdPedido).setHeader("IdPedido");
        grid.addColumn(Pedidos::getIdOng).setHeader("IdOng");

        option2Cont.add(grid);
        add(option2Cont);
    }
}