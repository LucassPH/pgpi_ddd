package org.vaadin.example;

import com.vaadin.flow.component.grid.dataview.GridListDataView;

public class Filter {

    private final GridListDataView<Producto> dataView;

    private String nombre;

    public Filter(GridListDataView<Producto> dataView) {
        this.dataView = dataView;
        this.dataView.addFilter(this::test);
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.dataView.refreshAll();
    }

    public boolean test(Producto producto) {

        return matches(producto.getNombre(), nombre);
    }

    private boolean matches(String value, String searchTerm) {
        return searchTerm == null || searchTerm.isEmpty()
                || value.toLowerCase().contains(searchTerm.toLowerCase());
    }
}
