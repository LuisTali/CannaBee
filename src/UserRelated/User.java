package UserRelated;

public class User {
    private int id;
    private String nombre;
    private String email;
    private String password;
    private boolean isAdmin;

    public User() {
    }

    public User(String nombre, String email, String password, boolean isAdmin) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public void setId(int id){
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean o){
        isAdmin = o;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin(){
        return isAdmin;
    }

    public String toString(){
        return "ID: " + getId() + ", Nombre: " + getNombre() + ", Email: " + getEmail() + ", Password: " + getPassword();
    }

    public String toTextField(){
        return "Bienvenido " + getNombre();
    }
}
