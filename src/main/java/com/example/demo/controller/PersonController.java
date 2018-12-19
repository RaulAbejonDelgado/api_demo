package com.example.demo.controller;

import com.example.demo.dao.PersonDao;
import com.example.demo.dao.PersonaDao;
import com.example.demo.pojo.Person;
import com.example.demo.pojo.Persona;
import com.example.demo.pojo.ResponseMensaje;
import com.example.demo.service.PersonService;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/publicaciones/person")
public class PersonController {

    private static ArrayList<Person> persons;
    private static PersonService servicioPerson = null;

    public PersonController() {
        super();
        servicioPerson = PersonService.getInstance();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> listado() {

        ResponseEntity<Object> response = new ResponseEntity<>(persons, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        try {
            persons = servicioPerson.listar();
            Person person = new Person();
            if (persons.size() > 0) {
                System.out.println("*************Pasamos por PersonController-get*************");
                for (Person p : persons) {
                    Link selfLink = linkTo(PersonController.class).slash(p.getPersonId()).withSelfRel();
                    p.add(selfLink);
                }
            }

            response = new ResponseEntity<>(persons, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> detalle(@PathVariable int id) {
        Person p = new Person();

        ResponseEntity<Object> response = new ResponseEntity<>(persons, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        try {

            p = servicioPerson.obtenerPorId(id);
            if (p != null) {

                Link selfLink = linkTo(PersonController.class).slash(p.getPersonId()).withSelfRel();
                p.add(selfLink);
                response = new ResponseEntity<>(p, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(id);


        return response;

    }


}
