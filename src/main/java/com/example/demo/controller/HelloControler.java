package com.example.demo.controller;

import com.example.demo.pojo.Index;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/")
public class HelloControler {

    private static ArrayList<Index> resources = null;


    public HelloControler(){
        super();
        resources = new ArrayList<Index>();

    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> resourcesList() {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        System.out.println("*************Pasamos por get*************");
        try {
            if(resources.isEmpty()){

                resources.add(new Index(linkTo(PublicacionController.class).toString()));
                resources.add(new Index(linkTo(FamilyController.class).toString()));
                resources.add(new Index(linkTo(PersonController.class).toString()));

            }

                response = new ResponseEntity<Object>(resources, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();

        }


        return response;

    }
}
