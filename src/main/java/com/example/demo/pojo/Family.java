package com.example.demo.pojo;


import org.mongodb.morphia.annotations.Entity;

@Entity(value = "familias", noClassnameStored = true)
public class Family extends BaseEntity {

    private int selfId;
    private String nombre;
    private Person[] personas;


    public Family() {
        this.selfId = 0;
        this.nombre = "";

    }

    public Family(int selfId, String nombre, Person[] personas) {

        this.selfId = selfId;
        this.nombre = nombre;
        this.personas = personas;
    }

    public Person[] getPersonas() {
        return personas;
    }

    public void setPersonas(Person[] personas) {
        this.personas = personas;
    }

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
