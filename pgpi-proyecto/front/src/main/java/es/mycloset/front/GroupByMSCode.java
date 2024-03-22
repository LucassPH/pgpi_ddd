package es.mycloset.front;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class GroupByMSCode extends VerticalLayout {

    private DataService dataService;

    private Grid<NationalDatafileJson> grid;

    public GroupByMSCode(@Autowired DataService dataService) {
        this.dataService = dataService;

        Label selectLabel = new Label("Select MSCode:");

        Select<String> msCodeSelect = new Select<>();
        msCodeSelect.setItems("MEASA", "MEASB", "MEASC", "MEAse", "MEASF", "MEASG", "MEASH", "MEASI", "MEASJ");

        // Listener para actualizar el Grid cuando cambia la selección
        msCodeSelect.addValueChangeListener(event -> {
            String selectedMSCode = event.getValue();
            // Obtén los datos actualizados según el MSCode seleccionado
            ArrayList<NationalDatafileJson> zonas = obtenerDatosSegunMSCode(selectedMSCode);

            // Actualiza los items del Grid con los nuevos datos
            grid.setItems(zonas);
        });


        // Inicializa el Grid sin datos
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.NONE);

        grid.addColumn(NationalDatafileJson::getId).setHeader("id");
        grid.addColumn(NationalDatafileJson::getMscode).setHeader("MsCode");
        grid.addColumn(NationalDatafileJson::getYear).setHeader("Year");
        grid.addColumn(NationalDatafileJson::getEstcode).setHeader("EstCode");
        grid.addColumn(NationalDatafileJson::getEstimate).setHeader("Estimate");
        grid.addColumn(NationalDatafileJson::getSe).setHeader("SE");
        grid.addColumn(NationalDatafileJson::getLowercib).setHeader("LowerCIB");
        grid.addColumn(NationalDatafileJson::getUppercib).setHeader("UpperCIB");
        grid.addColumn(NationalDatafileJson::getFlag).setHeader("Flag");


        add(selectLabel, msCodeSelect, grid);
    }

    // Método para obtener datos según el MSCode seleccionado
    private ArrayList<NationalDatafileJson> obtenerDatosSegunMSCode(String msCode) {
        // Llama al servicio para obtener datos según el MSCode
        ArrayList<NationalDatafileJson> datos = dataService.getDataByMsCode(msCode);
        // Retorna la lista de datos obtenidos
        return datos;
    }


}
