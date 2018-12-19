package com.example.demo.pojo;

public class Coment {

    private String _id;
    private int familyId;
    private String Texto;
    private int userId;


    public Coment() {
    }

    public Coment(String _id, int familyId, String texto, int userId) {
        this._id = _id;
        this.familyId = familyId;
        Texto = texto;
        this.userId = userId;
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

    public String getTexto() {
        return Texto;
    }

    public void setTexto(String texto) {
        Texto = texto;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
