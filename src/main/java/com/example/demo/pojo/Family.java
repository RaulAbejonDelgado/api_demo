package com.example.demo.pojo;


import org.mongodb.morphia.annotations.Entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity(value = "familias", noClassnameStored = true)
public class Family extends BaseEntity {


    private int selfId;

    @NotNull(message = "Name cannot be null")
    @Size(min = 5, max = 150, message= "Name must be between 5 and 150 characters")
    private String nombre;

    @NotNull(message = "Name cannot be null")
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
