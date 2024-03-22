package es.mycloset.front;


import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.component.html.Label;


@Route("main")
public class MainPage extends VerticalLayout {
    VerticalLayout option1Cont;
    VerticalLayout option2Cont;

    public MainPage() {

        Label title = new Label("Ratio Number of Low Income Children at Risk of Exclusion in NZ");
        title.addClassName("title");
        title.addClassName("centered-content");
        add(title);

        DataService service = new DataService();

        option1Cont = new VerticalLayout();
        GeneralInformation option1Generator = new GeneralInformation(service);
        option1Generator.addClassName("no-space-above");
        option1Cont.add(option1Generator);

        option2Cont = new VerticalLayout();
        GroupByMSCode option2Generator = new GroupByMSCode(service);
        option2Generator.addClassName("no-space-above");
        option2Cont.add(option2Generator);
        option2Cont.setVisible(false);

        Tab option1 = new Tab("General Information");
        Tab option2 = new Tab("Group By MSCode");

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
