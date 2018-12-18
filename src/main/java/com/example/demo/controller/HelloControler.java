package com.example.demo.controller;

import com.example.demo.dao.PersonaDao;
import com.example.demo.pojo.Persona;
import com.example.demo.pojo.ResponseMensaje;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.UnknownHostException;
import java.util.ArrayList;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/publicaciones/personas")
public class HelloControler {

    ArrayList<Persona> personas = new ArrayList<Persona>();
    PersonaDao personaDao = null;


//    @GetMapping
//    public String getHello() {
//        return "hello";
//    }

    public HelloControler() {
        super();
        personaDao = PersonaDao.getInstance();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> listado() {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        System.out.println("*************Pasamos por get*************");
        try {
            personas = personaDao.listar();

            if (personas != null) {
                response = new ResponseEntity<Object>(personas, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();

        }


        return response;

    }


}
