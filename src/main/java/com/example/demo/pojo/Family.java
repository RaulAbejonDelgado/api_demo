package com.example.demo.pojo;


import org.mongodb.morphia.annotations.Entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Family")
@Entity(value = "familias", noClassnameStored = true)
public class Family extends BaseEntity {

    @XmlElement(name="selfId")
    private int selfId;

    @NotNull(message = "Name cannot be null")
    @Size(min = 5, max = 150, message= "Name must be between 5 and 150 characters")
    @XmlElement(name="nombre")
    private String nombre;

    @NotNull(message = "Name cannot be null")
    @XmlElementWrapper(name="personas")//para indicarle que es un array
    @XmlElement(name="personas")
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
