package com.example.demo.pojo;

import org.mongodb.morphia.annotations.Entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Arrays;
import java.util.Date;

@Entity(value = "comentarios", noClassnameStored = true)
@XmlRootElement(name="Comment")
public class Comment extends BaseEntity {

    @NotNull(message = "Name cannot be null")
    @XmlElementWrapper(name="familia")//para indicarle que es un array
    @XmlElement(name="familia")
    private Family[] familia;

    @NotNull(message = "tittle cannot be null")
    @Size(min = 5, max = 100, message= "tittle must be between 5 and 100 characters")
    @XmlElement(name="titulo")
    private String titulo;

    @NotNull(message = "Name cannot be null")
    @Size(min = 5, max = 150, message= "Name must be between 5 and 150 characters")
    @XmlElement(name="texto")
    private String texto;

    @NotNull(message = "Name cannot be null")
    @XmlElementWrapper(name="persona")//para indicarle que es un array
    @XmlElement(name="persona")
    private Person[] persona;

    @XmlElement(name="selfId")
    private int selfId;

//    @NotNull(message = "Name cannot be null")
//    @XmlElement(name="fecha")
    private Date fecha;




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
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "familia=" + Arrays.toString(familia) +
                ", titulo='" + titulo + '\'' +
                ", texto='" + texto + '\'' +
                ", persona=" + Arrays.toString(persona) +
                ", selfId=" + selfId +
                ", fecha=" + fecha +
                ", _id=" + _id +
                '}';
    }
}
