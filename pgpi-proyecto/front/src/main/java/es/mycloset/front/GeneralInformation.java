package es.mycloset.front;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToFloatConverter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Consumer;

@SpringComponent
@UIScope
public class GeneralInformation extends VerticalLayout {

    private VerticalLayout option2Cont;
    private Grid<NationalDatafileJson> grid;
    private DataService dataService;
    private Gson gson;

    public GeneralInformation(@Autowired DataService dataService) {
        this.dataService = dataService;
        this.gson = new GsonBuilder().setPrettyPrinting().create();

        // Fetch zone data from the service
        String response = dataService.getZona();
        Type listType = new TypeToken<ArrayList<NationalDatafileJson>>() {}.getType();
        ArrayList<NationalDatafileJson> zonas = gson.fromJson(response, listType);

        option2Cont = new VerticalLayout();

        // Create and configure the grid
        grid = new Grid<>();
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.setItems(zonas);
        grid.addColumn(NationalDatafileJson::getId).setHeader("id");
        grid.addColumn(NationalDatafileJson::getMscode).setHeader("MsCode");
        grid.addColumn(NationalDatafileJson::getYear).setHeader("Year");
        grid.addColumn(NationalDatafileJson::getEstcode).setHeader("EstCode");
        grid.addColumn(NationalDatafileJson::getEstimate).setHeader("Estimate");
        grid.addColumn(NationalDatafileJson::getSe).setHeader("SE");
        grid.addColumn(NationalDatafileJson::getLowercib).setHeader("LowerCIB");
        grid.addColumn(NationalDatafileJson::getUppercib).setHeader("UpperCIB");
        grid.addColumn(NationalDatafileJson::getFlag).setHeader("Flag");

        // Configure double-click action on the row
        grid.addItemDoubleClickListener(event -> {
            NationalDatafileJson selectedItem = dataService.getZonaById(event.getItem().getId());

            if (selectedItem != null) {
                openEditDialog(selectedItem, (changesSaved) -> {
                    if (changesSaved) {
                        // Reload grid data if changes were saved
                        String response2 = dataService.getZona();
                        Type listType2 = new TypeToken<ArrayList<NationalDatafileJson>>(){}.getType();
                        ArrayList<NationalDatafileJson> zonas2 = gson.fromJson(response2, listType2);

                        grid.setItems(zonas2);
                    }
                });
            } else {
                // Handle case where the item is not found
                Notification.show("Element not found", 3000, Notification.Position.MIDDLE);
            }
        });

        // Add a button column for deletion
        grid.addComponentColumn(data -> {
            Button deleteButton = new Button("Delete");
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
            deleteButton.addClickListener(event -> {
                dataService.eliminar(data.getId());
                zonas.remove(data);
                grid.setItems(zonas);
                Notification.show("Data eliminated", 3000, Notification.Position.MIDDLE);
            });
            return deleteButton;
        }).setHeader("");

        // Button to add new data
        Button newData = new Button("Add Data");
        newData.addClickListener(event -> {
            Dialog addDialog = new Dialog();
            addDialog.setCloseOnOutsideClick(false);

            // Create text fields for the new element
            TextField idField = new TextField("id");
            idField.setValue(UUID.randomUUID().toString());

            TextField msCodeField = new TextField("MsCode");
            TextField yearField = new TextField("Year");
            TextField estCodeField = new TextField("EstCode");
            TextField estimateField = new TextField("Estimate");
            TextField seField = new TextField("SE");
            TextField lowerCIBField = new TextField("LowerCIB");
            TextField upperCIBField = new TextField("UpperCIB");
            TextField flagField = new TextField("Flag");

            Binder<NationalDatafileJson> binder = new Binder<>();

            // Validation and binding for each field
            binder.forField(idField)
                    .withValidator(value -> !value.isEmpty(), "ID cannot be empty")
                    .bind(NationalDatafileJson::getId, NationalDatafileJson::setId);

            binder.forField(msCodeField)
                    .withValidator(value -> !value.isEmpty(), "MsCode cannot be empty")
                    .bind(NationalDatafileJson::getMscode, NationalDatafileJson::setMscode);

            binder.forField(yearField)
                    .withValidator(value -> !value.isEmpty(), "Year cannot be empty")
                    .withValidator(value -> value.length() == 4 && value.matches("[0-9][0-9][0-9][0-9]"),
                            "Year must be 4 numbers long")
                    .bind(NationalDatafileJson::getYear, NationalDatafileJson::setYear);

            binder.forField(estCodeField)
                    .withValidator(value -> !value.isEmpty(), "EstCode cannot be empty")
                    .bind(NationalDatafileJson::getEstcode, NationalDatafileJson::setEstcode);

            binder.forField(estimateField)
                    .withConverter(new StringToFloatConverter("It must be a number"))
                    .withValidator(value -> value != null, "Estimate cannot be empty")
                    .bind(NationalDatafileJson::getEstimate, NationalDatafileJson::setEstimate);

            binder.forField(seField)
                    .withConverter(new StringToFloatConverter("It must be a number"))
                    .withValidator(value -> value != null, "SE cannot be empty")
                    .bind(NationalDatafileJson::getSe, NationalDatafileJson::setSe);

            binder.forField(lowerCIBField)
                    .withConverter(new StringToFloatConverter("It must be a number"))
                    .withValidator(value -> value != null, "LowerCIB cannot be empty")
                    .bind(NationalDatafileJson::getLowercib, NationalDatafileJson::setLowercib);

            binder.forField(upperCIBField)
                    .withConverter(new StringToFloatConverter("It must be a number"))
                    .withValidator(value -> value != null, "UpperCIB cannot be empty")
                    .bind(NationalDatafileJson::getUppercib, NationalDatafileJson::setUppercib);


            binder.forField(flagField)
                    .withValidator(value -> !value.isEmpty(), "Flag cannot be empty")
                    .withValidator(value -> value.length() == 1 && value.matches("[a-zA-Z]"), "Must be a single letter")
                    .bind(NationalDatafileJson::getFlag, NationalDatafileJson::setFlag);

            // Button to save the new element
            Button saveButton = new Button("Save", event1 -> {
                // Validate the binder before attempting to save
                if (binder.validate().isOk()) {
                    // Create a new element using the field values
                    NationalDatafileJson newElement = new NationalDatafileJson(
                            idField.getValue(),
                            msCodeField.getValue(),
                            yearField.getValue(),
                            estCodeField.getValue(),
                            Float.parseFloat(estimateField.getValue()),
                            Float.parseFloat(seField.getValue()),
                            Float.parseFloat(lowerCIBField.getValue()),
                            Float.parseFloat(upperCIBField.getValue()),
                            flagField.getValue()
                    );

                    dataService.postnuevo(newElement);
                    System.out.println(newElement);

                    // Reload grid data when adding a new element
                    String response2 = dataService.getZona();
                    ArrayList<NationalDatafileJson> zonas2 = gson.fromJson(response2, listType);
                    grid.setItems(zonas2);

                    // Close the dialog after saving
                    addDialog.close();
                    Notification.show("Elemend added", 3000, Notification.Position.MIDDLE);
                } else {
                    Notification.show("Please correct the error in the form", 3000, Notification.Position.MIDDLE);
                }
            });

            // Button to cancel the operation
            Button cancelButton = new Button("Cancel", event2 -> addDialog.close());
            addDialog.add(idField, msCodeField, yearField, estCodeField, estimateField, seField, lowerCIBField, upperCIBField, flagField, saveButton, cancelButton);
            addDialog.open();
        });


        option2Cont.add(grid, newData);
        add(option2Cont);
    }

    // Method to open the edit dialog
    private void openEditDialog(NationalDatafileJson selectedItem, Consumer<Boolean> dialogClosedCallback) {
        Dialog editDialog = new Dialog();
        editDialog.setCloseOnOutsideClick(false);

        // Create fields for editing
        TextField idField1 = new TextField("Id");
        idField1.setValue(selectedItem.getId());
        idField1.setEnabled(false); // Deshabilitar la edición del campo Id

        TextField msCodeField1 = new TextField("MsCode");
        msCodeField1.setValue(selectedItem.getMscode());

        TextField yearField1 = new TextField("Year");
        yearField1.setValue(selectedItem.getYear());

        TextField estCodeField1 = new TextField("EstCode");
        estCodeField1.setValue(selectedItem.getEstcode());

        TextField estimateField1 = new TextField("Estimate");
        estimateField1.setValue(selectedItem.getEstimate() != null ? String.valueOf(selectedItem.getEstimate()) : "");

        TextField seField1 = new TextField("SE");
        seField1.setValue(selectedItem.getSe() != null ? String.valueOf(selectedItem.getSe()) : "");

        TextField lowerCIBField1 = new TextField("LowerCIB");
        lowerCIBField1.setValue(selectedItem.getLowercib() != null ? String.valueOf(selectedItem.getLowercib()) : "");

        TextField upperCIBField1 = new TextField("UpperCIB");
        upperCIBField1.setValue(selectedItem.getUppercib() != null ? String.valueOf(selectedItem.getUppercib()) : "");

        TextField flagField1 = new TextField("Flag");
        flagField1.setValue(selectedItem.getFlag());

        // Button to accept the edit
        Button acceptButton1 = new Button("Accept", event1 -> {
            // Update the object with the new values
            selectedItem.setMscode(msCodeField1.getValue());
            selectedItem.setYear(yearField1.getValue());
            selectedItem.setEstcode(estCodeField1.getValue());

            if (estimateField1.getValue() != null && !estimateField1.getValue().isEmpty()) {
                selectedItem.setEstimate(Float.parseFloat(estimateField1.getValue()));
            }

            if (seField1.getValue() != null && !seField1.getValue().isEmpty()) {
                selectedItem.setSe(Float.parseFloat(seField1.getValue()));
            }

            if (lowerCIBField1.getValue() != null && !lowerCIBField1.getValue().isEmpty()) {
                selectedItem.setLowercib(Float.parseFloat(lowerCIBField1.getValue()));
            }

            if (upperCIBField1.getValue() != null && !upperCIBField1.getValue().isEmpty()) {
                selectedItem.setUppercib(Float.parseFloat(upperCIBField1.getValue()));
            }


            selectedItem.setFlag(flagField1.getValue());

            // Call the update function
            dataService.update(selectedItem.getId(), selectedItem);


            // Reload grid data and close the dialog
            editDialog.close();
            Notification.show("Data updated", 3000, Notification.Position.MIDDLE);

            // Call the callback indicating that changes were saved
            dialogClosedCallback.accept(true);
        });

        // Button to cancel the operation
        Button cancelButton1 = new Button("Cancel", event1 -> {
            // Close the dialog and call the callback indicating that editing was canceled
            editDialog.close();
            dialogClosedCallback.accept(false);
        });

        // Add fields and buttons to the dialog
        editDialog.add(idField1, msCodeField1, yearField1, estCodeField1, estimateField1, seField1, lowerCIBField1, upperCIBField1, flagField1, acceptButton1, cancelButton1);
        editDialog.open();
    }
}
