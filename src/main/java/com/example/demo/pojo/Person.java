package com.example.demo.pojo;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "persons", noClassnameStored = true)
public class Person extends BaseEntity {
    private static final long serialVersionUID = 1L;


    private int selfId;
    private int familyId;
    private String nombre;

    public Person() {

        this.selfId = 0 ;
        this.familyId = 0;
        this.nombre = "";

    }


    public Person(int personId, int familyId, String nombre) {
        this.selfId = personId;
        this.familyId = familyId;
        this.nombre = nombre;
    }

    public int getFamilyId() {
        return familyId;
    }

    public void setFamilyId(int familyId) {
        this.familyId = familyId;
    }

    public int getselfId() {
        return selfId;
    }

    public void setSelfId(int personId) {
        this.selfId = personId;
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
                "selfId=" + selfId +
                ", familyId=" + familyId +
                ", nombre='" + nombre + '\'' +
                ", _id=" + _id +
                '}';
    }
}
