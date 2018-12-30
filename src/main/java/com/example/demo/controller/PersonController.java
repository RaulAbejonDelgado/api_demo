package com.example.demo.controller;

import com.example.demo.pojo.Person;
import com.example.demo.pojo.Videojuego;
import com.example.demo.service.PersonService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
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

    private static ArrayList<Person> persons = null;
    private static PersonService servicioPerson = null;

    public PersonController()   {
        super();
        try {

            servicioPerson = PersonService.getInstance();

        }catch (UnknownHostException e){

            e.printStackTrace();
        }


    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> listAll() {

        ResponseEntity<Object> response = new ResponseEntity<>(persons, HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Person>> personResources = new ArrayList<Resource<Person>>();
        try {
            personResources = servicioPerson.listar();

                System.out.println("*************Pasamos por PersonController-get*************");
                response = new ResponseEntity<>(personResources, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> detail(@PathVariable int id) {
        Person p = new Person();
        ArrayList<Resource<Person>> resoucesPerson =new ArrayList<Resource<Person>>();
        ResponseEntity<Object> response = new ResponseEntity<>(persons, HttpStatus.INTERNAL_SERVER_ERROR);

        try {

            resoucesPerson = servicioPerson.obtenerPorId(id);
            if (resoucesPerson.size() > 0) {

                response = new ResponseEntity<>(resoucesPerson, HttpStatus.OK);
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

        try {

            if (servicioPerson.eliminar(id)) {

                response = new ResponseEntity<>(HttpStatus.OK);

            } else {

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<Object> crear(@RequestBody Person persona) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Person>> resoucesPerson =new ArrayList<Resource<Person>>();

        try {
            resoucesPerson = servicioPerson.crear(persona);

            if (resoucesPerson.size() == 1) {

                response = new ResponseEntity<>(resoucesPerson, HttpStatus.CREATED);
            } else {

                response = new ResponseEntity<>( HttpStatus.CONFLICT);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modificar(@RequestBody Person persona, @PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Person>> resoucesPerson =new ArrayList<Resource<Person>>();
        try {
            resoucesPerson = servicioPerson.modficar(id, persona);
            if(resoucesPerson.size() == 1){

                response = new ResponseEntity<>(resoucesPerson, HttpStatus.OK);

            }else{

                response = new ResponseEntity<>( HttpStatus.CONFLICT);
            }

        }catch (Exception e) {

            e.printStackTrace();
        }

        return response;

    }


}



