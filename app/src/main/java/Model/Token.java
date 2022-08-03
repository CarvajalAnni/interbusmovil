package Model;

public class Token {
     String Nombre;
     String Correo;
     String Rol;
     String Token;


    public Token() {
    }


    public Token(String nombre, String correo, String rol, String token) {
        Nombre = nombre;
        Correo = correo;
        Token = token;
        Rol= rol;
    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String rol) {
        Rol = rol;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    @Override
    public String toString() {
        return "Token{" +
                  Nombre + '\'' +
                  Correo + '\'' +
                 Token + '\'' +
                '}';
    }
}
