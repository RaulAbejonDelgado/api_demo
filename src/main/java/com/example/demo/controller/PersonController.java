package com.example.demo.controller;

import com.example.demo.dao.PersonDao;
import com.example.demo.dao.PersonaDao;
import com.example.demo.pojo.Person;
import com.example.demo.pojo.Persona;
import com.example.demo.pojo.ResponseMensaje;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
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
    private static PersonDao personDao = null;


//    @GetMapping
//    public String getHello() {
//        return "hello";
//    }

    public PersonController() {
        super();
        personDao = PersonDao.getInstance();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> listado() {

        ResponseEntity<Object> response = new ResponseEntity<>(persons,HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        try {
            persons = personDao.listar();
            Person person = new Person();

            System.out.println("*************Pasamos por PersonController-get*************");
            for(Person p : persons){
                Link selfLink = linkTo(PersonController.class).slash(p.getPersonId()).withSelfRel();
                p.add(selfLink);
                System.out.println(linkTo(PersonController.class).slash(p.getPersonId()).withSelfRel());
            }
           response = new ResponseEntity<>(persons,HttpStatus.OK);


        }catch (Exception e) {
            e.printStackTrace();
        }



        return response;

    }


}
