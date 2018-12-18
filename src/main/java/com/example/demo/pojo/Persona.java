package com.example.demo.pojo;

public class Persona {

    private String _id;
    private long id;
    private String nombre;
    private Hypermedia[] links;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Hypermedia[] getLinks() {
        return links;
    }

    public void setLinks(Hypermedia[] links) {
        this.links = links;
    }

    public Persona() {
    }

    public Persona(long id, String nombre, Hypermedia[] links ) {
        this.id = id;
        this.nombre = nombre;
        this.links = links;
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
