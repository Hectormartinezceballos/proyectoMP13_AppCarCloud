package com.example.carcloudapp.Objetos;

public class Foto {

    private String Id, Nombre, Descripcion, Carpeta, Url;

    public Foto() {

    }

    public Foto(String nombre, String descripcion, String carpeta, String url) {
        Nombre = nombre;
        Descripcion = descripcion;
        Carpeta = carpeta;
        Url = url;
    }
    public Foto(String nombre,String descripcion,String carpeta){
        Nombre = nombre;
        Descripcion = descripcion;
        Carpeta = carpeta;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getCarpeta() {
        return Carpeta;
    }

    public void setCarpeta(String carpeta) {
        Carpeta = carpeta;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }



}
