package com.example.demo.pojo;

import org.mongodb.morphia.annotations.Entity;

import java.util.Arrays;

@Entity(value = "comentarios", noClassnameStored = true)
public class Comment extends BaseEntity {

    private Family[] familia;
    private String texto;
    private Person[] persona;
    private int selfId;


    public Comment() {
        this.familia = null;
        this.texto = "";
        this.persona = null;
    }

    public Comment( Family[] familia, String texto, Person[] persona, int comentarioId) {

        this.familia = familia;
        this.texto = texto;
        this.persona = persona;
        this.selfId = comentarioId;
    }

    public Family[] getFamilia() {
        return familia;
    }

    public void setFamilia(Family[] familia) {
        this.familia = familia;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Person[] getPersona() {
        return persona;
    }

    public void setPersona(Person[] persona) {
        this.persona = persona;
    }

    public int getSelfId() {
        return selfId;
    }

    public void setSelfId(int selfId) {
        this.selfId = selfId;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "familia=" + Arrays.toString(familia) +
                ", texto='" + texto + '\'' +
                ", persona=" + Arrays.toString(persona) +
                ", selfId=" + selfId +
                ", _id=" + _id +
                '}';
    }
}
