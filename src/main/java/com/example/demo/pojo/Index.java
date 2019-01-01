package com.example.demo.pojo;



public class Index {

    private String descripcion;

    public Index(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Index{" +
                "descripcion='" + descripcion + '\'' +
                '}';
    }
}
