package com.example.carcloudapp.Objetos;

public class Usuario {

    private String Nombre,Apellidos,Email,Password;

    public Usuario() {}//constructor vacio


    public Usuario(String nombre, String apellidos, String email, String password) {
        Nombre = nombre;
        Apellidos = apellidos;
        Email = email;
        Password = password;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getApellidos() {
        return Apellidos;
    }

    public void setApellidos(String apellidos) {
        Apellidos = apellidos;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }




}
