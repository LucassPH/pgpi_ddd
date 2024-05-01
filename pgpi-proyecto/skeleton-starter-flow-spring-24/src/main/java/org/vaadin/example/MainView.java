package org.vaadin.example;

import com.googlecode.gentyref.TypeToken;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.HeaderRow;
import com.vaadin.flow.component.grid.dataview.GridListDataView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Route
public class MainView extends VerticalLayout {

    ArrayList<Producto> prod = new ArrayList<>();
    Producto AuxProd = new Producto();
    static Grid<Producto> gridProducto = new Grid<>(Producto.class);
    Button addProd = new Button("Nuevo");
    private static final String api = "http://backendcont:8887";
    HttpRequest request;
    HttpClient client = HttpClient.newBuilder().build();
    HttpResponse<String> response;

    Grid<Producto> grid = new Grid<>(Producto.class, false);
    public List<Producto> product;
    Button botonfinal;



    private String getData(String url1, String Id){
        try {
            String resource = "http://localhost:8887/Data";
            //System.out.println(resource);
            request = HttpRequest
                    .newBuilder(new URI(resource))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //System.out.println(response.body());
        } catch (URISyntaxException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

    public MainView(@Autowired GreetService service) {
        Notification not = new Notification();

        Grid.Column<Producto> nombreCol = grid.addColumn(Producto::getNombre).setHeader("Nombre").setAutoWidth(true);
        grid.addColumn(Producto::getTiempo).setHeader("Tiempo (dias)").setAutoWidth(true);
        grid.addColumn(Producto::getCantidad).setHeader("Cantidad de pedido").setAutoWidth(true);
        grid.addColumn(Producto::getCantidadres).setHeader("Cantidad restante").setAutoWidth(true);

         grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, prod) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_SUCCESS,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> this.addProd(prod));
                    button.setIcon(new Icon(VaadinIcon.PLUS));
                })).setHeader("Añadir").setAutoWidth(true);

        grid.addColumn(
                new ComponentRenderer<>(Button::new, (button, prod) -> {
                    button.addThemeVariants(ButtonVariant.LUMO_ICON,
                            ButtonVariant.LUMO_ERROR,
                            ButtonVariant.LUMO_TERTIARY);
                    button.addClickListener(e -> this.deleteProd(prod));
                    button.setIcon(new Icon(VaadinIcon.MINUS));
                })).setHeader("Eliminar").setAutoWidth(true);

        product = List.of(new Producto("prod1", 12, 1, 15), new Producto("prod2", 6, 12, 2), new Producto("prod3", 100, 5, 10));
        GridListDataView<Producto> dataView = grid.setItems(product);
        Filter filter = new Filter(dataView);

        grid.getHeaderRows().clear();
        HeaderRow headerRow = grid.appendHeaderRow();

        headerRow.getCell(nombreCol).setComponent(
                createFilterHeader("Nombre", filter::setNombre));
        botonfinal = new Button("Enviar Pedido");
        botonfinal.addClickListener(e -> not.show("Pedido realizado").addThemeVariants(NotificationVariant.LUMO_SUCCESS));


        add(grid, botonfinal);
    }

    private void addProd(Producto prodobj){
        if (prodobj.getCantidadres()>0){
            prodobj.setCantidad(prodobj.getCantidad()+1);
            prodobj.setCantidadres(prodobj.getCantidadres()-1);
            GridListDataView<Producto> dataView = grid.setItems(product);
            if (prodobj.getCantidadres() <= 10){
                Notification notification = new Notification();
                notification.addThemeVariants(NotificationVariant.LUMO_WARNING);

                Div text = new Div(
                        new Text("La cantidad restante del producto " + prodobj.getNombre() + " es baja")
                );

                Button closeButton = new Button(new Icon("lumo", "cross"));
                closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
                closeButton.setAriaLabel("Close");
                closeButton.addClickListener(event -> {
                    notification.close();
                });

                HorizontalLayout layout = new HorizontalLayout(text, closeButton);
                layout.setAlignItems(Alignment.CENTER);

                notification.add(layout);
                notification.open();
            }
        }
    }

    private void deleteProd(Producto prodobj){
        if (prodobj.getCantidad()!=0){
            prodobj.setCantidad(prodobj.getCantidad()-1);
            prodobj.setCantidadres(prodobj.getCantidadres()+1);
            GridListDataView<Producto> dataView = grid.setItems(product);
        }
    }

    private static Component createFilterHeader(String labelText, Consumer<String> filterChangeConsumer) {
        NativeLabel label = new NativeLabel(labelText);
        label.getStyle().set("padding-top", "var(--lumo-space-m)")
                .set("font-size", "var(--lumo-font-size-xs)");
        TextField textField = new TextField();
        textField.setValueChangeMode(ValueChangeMode.EAGER);
        textField.setClearButtonVisible(true);
        textField.addThemeVariants(TextFieldVariant.LUMO_SMALL);
        textField.setWidthFull();
        textField.getStyle().set("max-width", "100%");
        textField.addValueChangeListener(
                e -> filterChangeConsumer.accept(e.getValue()));
        VerticalLayout layout = new VerticalLayout(label, textField);
        layout.getThemeList().clear();
        layout.getThemeList().add("spacing-xs");

        return layout;
    }

}
