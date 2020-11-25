package com.example.carcloudapp.Objetos;

public class Foto {

    private String nombre, descripcion, evento, url;

    public Foto() {

    }

    public Foto(String nombre, String descripcion, String evento, String url) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.evento = evento;
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
