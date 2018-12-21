package com.example.demo.service;

import com.example.demo.controller.FamilyController;
import com.example.demo.controller.PersonController;
import com.example.demo.dao.PersonDao;
import com.example.demo.pojo.Person;
import org.springframework.hateoas.Link;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PersonService {

    private static PersonService INSTANCE = null;

    private static PersonDao personDao = null;


    public PersonService() {
        super();
        personDao = PersonDao.getInstance();
    }

    public static synchronized PersonService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonService();
        }
        return INSTANCE;
    }


    public ArrayList<Person> listar() throws UnknownHostException {
        ArrayList<Person> persons = new ArrayList<Person>();
        persons = personDao.listar();

        for (Person p : persons) {
            Link selfLink = linkTo(PersonController.class).withSelfRel();
            p.add(selfLink);
            Link detailLink = linkTo(PersonController.class).slash(p.getselfId()).withRel("Detalle");
            p.add(detailLink);
            Link familyLink = linkTo(FamilyController.class).slash(p.getFamilyId()).withRel("Familia");
            p.add(familyLink);
        }

        return persons;
    }

    public Person obtenerPorId(int id) throws UnknownHostException {

        Person p = personDao.obtenerPorId(id);

        Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
        p.add(selfLink);
        Link familyLink = linkTo(FamilyController.class).slash(p.getFamilyId()).withRel("Familia");
        p.add(familyLink);
        Link listAllLink = linkTo(PersonController.class).withRel("Listar personas");
        p.add(listAllLink);

        return p;
    }

    public boolean eliminar(int id) throws  UnknownHostException {
        boolean resul = false;
        if(personDao.eliminar(id)){
            resul = true;
        }

        return resul;
    }

    public boolean crear(Person p) throws  UnknownHostException {
        boolean resul  = false;
        if(personDao.crear(p)){
            resul = true;
        }

        return resul;
    }

    public boolean modficar(int id, Person p) throws  UnknownHostException{
        boolean resul = false;

        if(personDao.modificar(id, p)){
            resul = true;
            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
            p.add(selfLink);
        }

        return resul;
    }
}
