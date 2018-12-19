package com.example.demo.pojo;

public class Family {

    private String _id;
    private int familyId;
    private String nombre;


    public Family() {
    }

    public Family(String _id, int familyId, String nombre) {
        this._id = _id;
        this.familyId = familyId;
        this.nombre = nombre;
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
