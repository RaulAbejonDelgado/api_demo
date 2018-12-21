package com.example.demo.pojo;

import org.springframework.hateoas.ResourceSupport;

public class Person extends ResourceSupport {

    private String _id;
    private int selfId;
    private int familyId;
    private String nombre;

    public Person() {
        this._id="";
        this.selfId = 0 ;
        this.familyId = 0;

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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getselfId() {
        return selfId;
    }

    public void setPersonId(int personId) {
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
                "_id='" + _id + '\'' +
                ", selfId=" + selfId +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
