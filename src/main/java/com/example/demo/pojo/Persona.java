package com.example.demo.pojo;

public class Persona {

    long id;
    String nombre;
    private Hypermedia[] links;

    public Hypermedia[] getLinks() {
        return links;
    }

    public void setLinks(Hypermedia[] links) {
        this.links = links;
    }

    public Persona() {
    }

    public Persona(long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
