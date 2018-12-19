package com.example.demo.pojo;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceSupport;

public class Persona extends ResourceSupport {

    private String _id;
    private String personaId;
    private String nombre;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPersonaId() {
        return personaId;
    }

    public void setPersonaId(String personaId) {
        this.personaId = personaId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
