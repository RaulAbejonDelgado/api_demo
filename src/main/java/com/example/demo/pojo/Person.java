package com.example.demo.pojo;

import org.springframework.hateoas.ResourceSupport;

public class Person extends ResourceSupport {

    private String _id;
    private int personId;
    private String nombre;

    public Person() {
    }

    public Person(String _id, int personId, String nombre) {
        this._id = _id;
        this.personId = personId;
        this.nombre = nombre;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Person{" +
                "_id='" + _id + '\'' +
                ", personId=" + personId +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
