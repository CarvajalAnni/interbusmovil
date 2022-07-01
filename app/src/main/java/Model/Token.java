package Model;

public class Token {
     String Nombre;
     String Correo;
     String Token;

    public Token() {
    }


    public Token(String nombre, String correo, String token) {
        Nombre = nombre;
        Correo = correo;
        Token = token;
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
