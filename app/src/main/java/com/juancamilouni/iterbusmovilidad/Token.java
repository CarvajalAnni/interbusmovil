package com.juancamilouni.iterbusmovilidad;

public class Token {
    private String Nombre;
    private String Id;
    private String Correo;
    private String Token;



    public Token() {
    }

    public Token(String nombre, String id, String correo, String token) {
        Nombre = nombre;
        Token = token;
        Id = id;
        Correo = correo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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
                "Nombre='" + Nombre + '\'' +
                ", Id='" + Id + '\'' +
                ", Correo='" + Correo + '\'' +
                ", Token='" + Token + '\'' +
                '}';
    }
}
