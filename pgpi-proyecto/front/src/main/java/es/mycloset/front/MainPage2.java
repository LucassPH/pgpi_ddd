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
    }

    private void hideContainers() {
    }
}
