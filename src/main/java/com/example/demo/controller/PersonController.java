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
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Object> listAll() {

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
    public ResponseEntity<Object> detail(@PathVariable int id) {
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(persons, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        try {

            if (servicioPerson.eliminar(id)) {

                response = new ResponseEntity<>(HttpStatus.OK);

            } else {
                rm.setMensaje("Error Eliminando persona");

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

//    @RequestMapping( method = RequestMethod.POST)
//    public ResponseEntity<Object> crear(@RequestBody Person persona) {
//
//    }

}



