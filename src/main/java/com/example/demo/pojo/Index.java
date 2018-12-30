package com.example.demo.pojo;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

public class Index extends ResourceSupport {

    private String descripcion;

    public Index(Link link) {
    }

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
