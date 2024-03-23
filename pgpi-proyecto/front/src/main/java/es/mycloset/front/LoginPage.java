package es.mycloset.front;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Objects;
import java.util.UUID;

@Route
public class LoginPage extends VerticalLayout {
    HttpRequest request;
    HttpClient client = HttpClient.newBuilder().build();
    HttpResponse<String> response;

    String user;

    public void postLogin(User userNuevo) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(userNuevo);
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8887/users"))
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(json))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    public int postSignup(User UserNuevo){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(UserNuevo);
            request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8887/usersp"))
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString(json))
                    .build();
            response = client.send(request, HttpResponse.BodyHandlers.ofString());


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response.statusCode();
    }
    public LoginPage() {
        addClassName("login-page");
        Image image = new Image("https://res.cloudinary.com/ddfqxbkwo/image/upload/v1682850663/logo_2_mycloset_rf3gpa.png", "Imagen");
        image.setWidth("250px");
        image.setHeight("250x");
        String idUsuario = UUID.randomUUID().toString();

        TextField username = new TextField("Usuario");
        PasswordField password = new PasswordField("Contraseña");
        Button loginButton = new Button("Login");
        loginButton.addClickListener(event -> {

            User nu = new User(idUsuario,username.getValue(), password.getValue(),1);
            postLogin(nu);
            int statusCode = response.statusCode();
            String responseBody = response.body();
            int tipoUsuario = Integer.parseInt(responseBody);
            if (statusCode == 200) {
                Notification.show("Credenciales Correctas");
                //User.setCurrentUser(nu);
                if(tipoUsuario == 1){
                    UI.getCurrent().navigate("main");
                }else{
                    UI.getCurrent().navigate("main2");
                }

            } else {
                Notification.show("Usuario o Contraseña Errónea");
            }
        });
        loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        Button registerButton = new Button("Registrar");
        registerButton.addClickListener(event -> {
            Dialog registrationDialog = new Dialog();
            registrationDialog.setCloseOnEsc(false);
            registrationDialog.setCloseOnOutsideClick(false);

            VerticalLayout dialogLayout = new VerticalLayout();
            dialogLayout.setSizeUndefined();
            dialogLayout.setPadding(true);

            TextField username2 = new TextField("Usuario");
            PasswordField password2 = new PasswordField("Contraseña");
            PasswordField password3 = new PasswordField("Confirmar Contraseña");
            Button register = new Button("Registrar",
                    event2 -> {

                        if (Objects.equals(password3.getValue(), password2.getValue())) {
                            User sign = new User(idUsuario, username2.getValue(), password2.getValue(),1);
                            int p = postSignup(sign);
                            if (p == 401) {
                                Notification.show("Nombre de Usuario no disponible");
                            } else {
                                Notification.show("Usuario Creado");
                                registrationDialog.close();
                            }
                        } else {
                            Notification.show("Las Contraseñas no Coinciden");
                        }
                    });
            register.setClassName("form-button-with-padding");

            register.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

            Button cancelButton = new Button("Cancelar");
            cancelButton.setClassName("form-button-with-padding");
            cancelButton.addClickListener(eventCancel -> registrationDialog.close());

            HorizontalLayout buttonLayout = new HorizontalLayout();
            buttonLayout.add(register);
            buttonLayout.add(cancelButton);

            dialogLayout.add(new H3("Nuevo Registro:"), username2, password2, password3, buttonLayout);
            registrationDialog.add(dialogLayout);
            registrationDialog.open();
        });


        add(image,username, password, new HorizontalLayout(loginButton, registerButton));
    }
}