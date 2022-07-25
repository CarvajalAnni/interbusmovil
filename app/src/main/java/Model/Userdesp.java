package Model;

public class Userdesp {
    String email;
    String clave;
    String rol;

    public Userdesp() {
    }

    public Userdesp(String email, String clave, String rol) {
        this.email = email;
        this.clave = clave;
        this.rol = rol;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}
