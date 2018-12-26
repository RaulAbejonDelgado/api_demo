package com.example.demo.pojo;

import org.springframework.hateoas.ResourceSupport;

public class Coment extends ResourceSupport {

    private String _id;
    private Family familia;
    private String texto;
    private Person persona;
    private int comentarioId;


    public Coment() {
        this.familia = new Family();
        this.texto = "";
        this.persona = new Person();
    }

    public Coment(String _id, Family familia, String texto, Person persona, int comentarioId) {
        this._id = _id;
        this.familia = familia;
        this.texto = texto;
        this.persona = persona;
        this.comentarioId = comentarioId;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Family getFamilia() {
        return familia;
    }

    public void setFamilia(Family familia) {
        this.familia = familia;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Person getPersona() {
        return persona;
    }

    public void setPersona(Person persona) {
        this.persona = persona;
    }

    public int getComentarioId() {
        return comentarioId;
    }

    public void setComentarioId(int comentarioId) {
        this.comentarioId = comentarioId;
    }
}
