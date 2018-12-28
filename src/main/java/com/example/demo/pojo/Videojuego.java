package com.example.demo.pojo;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;



@Entity(value = "videojuegos", noClassnameStored = true)
public class Videojuego extends BaseEntity{
    private static final long serialVersionUID = 1L;

    private String titulo;
    private int precio;
    private int videoJuegoId;

    public Videojuego() {

        this.titulo = "";
        this.precio = 0 ;
        this.videoJuegoId = 0;
    }

    public Videojuego(String titulo, int precio, int videoJuegoId) {
        this.titulo = titulo;
        this.precio = precio;
        this.videoJuegoId = videoJuegoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getVideoJuegoId() {
        return videoJuegoId;
    }

    public void setVideoJuegoId(int videoJuegoId) {
        this.videoJuegoId = videoJuegoId;
    }

    @Override
    public String toString() {
        return "Videojuego{" +
                "titulo='" + titulo + '\'' +
                ", precio=" + precio +
                ", videoJuegoId=" + videoJuegoId +
                ", id=" + _id +
                '}';
    }
}
