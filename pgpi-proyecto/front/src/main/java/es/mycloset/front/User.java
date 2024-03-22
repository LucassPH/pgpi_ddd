package es.mycloset.front;

public class User {

    private String idUsuario;
    private String username;
    private String password;

    private int tipo_usuario;
    private static User currentUser;

    public User(String idUsuario, String username, String password, int tipo_usuario) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.password = password;
        this.tipo_usuario = tipo_usuario;
    }

    public static User getCurrentUser(){
        return currentUser;
    }
    public static void setCurrentUser(User user){
        currentUser = user;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdUsuario() {return idUsuario;}

    public void setIdUsuario(String idUsuario) {this.idUsuario = idUsuario;}

    public int getTipo_usuario() {return tipo_usuario;}

    public void setTipo_usuario(int tipo_usuario) {this.tipo_usuario = tipo_usuario;}
}