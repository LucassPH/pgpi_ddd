package es.mycloset.front;


import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;


@Route("main2")
public class MainPage2 extends VerticalLayout {
    VerticalLayout option1Cont;
    VerticalLayout option2Cont;

    public MainPage2() {
        Label title = new Label("Interfaz Europa");
        title.addClassName("title");
        title.addClassName("centered-content");
        add(title);

        DataService service = new DataService();

        option1Cont = new VerticalLayout();
        PedidosNuevos option1Generator = new PedidosNuevos(service);
        option1Generator.addClassName("no-space-above");
        option1Cont.add(option1Generator);

        option2Cont = new VerticalLayout();
        PedidosRealizados option2Generator = new PedidosRealizados(service);
        option2Generator.addClassName("no-space-above");
        option2Cont.add(option2Generator);
        option2Cont.setVisible(false);

        Tab option1 = new Tab("Nuevos Pedidos");
        Tab option2 = new Tab("Pedidos Realizados");

        Tabs tabs = new Tabs(option1, option2);
        tabs.addClassName("centered-content"); // Agregado para centrar las pestañas
        tabs.addSelectedChangeListener(event -> {
            this.hideContainers();
            Tab selectedTab = event.getSelectedTab();
            if (selectedTab == option1) {
                option1Cont.setVisible(true);
            }
            if (selectedTab == option2) {
                option2Cont.setVisible(true);
            }
        });
        add(tabs, option1Cont, option2Cont);
    }

    private void hideContainers() {
        option1Cont.setVisible(false);
        option2Cont.setVisible(false);
    }
}
