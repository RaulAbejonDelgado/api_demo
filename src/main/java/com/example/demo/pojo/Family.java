package com.example.demo.pojo;

import org.springframework.hateoas.ResourceSupport;

public class Family extends ResourceSupport {

    private String _id;
    private int familyId;
    private String nombre;
    private Person[] personas;


    public Family() {
    }

    public Family(String _id, int familyId, String nombre, Person[] personas) {
        this._id = _id;
        this.familyId = familyId;
        this.nombre = nombre;
        this.personas = personas;
    }

    public Person[] getPersonas() {
        return personas;
    }

    public void setPersonas(Person[] personas) {
        this.personas = personas;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
