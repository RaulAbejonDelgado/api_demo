package com.example.demo.controller;

import com.example.demo.pojo.Index;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
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

    private static ArrayList<Resource<Index>> resources = new ArrayList<>();

    public HelloControler() {
        super();
        resources = new ArrayList<>();

    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> resourcesList() {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        System.out.println("*************Pasamos por get*************");
        try {
            if (resources.isEmpty()) {

                //creamos los enlaces hateoas
                Link publicacionesLink = linkTo(PersonController.class).withSelfRel();
                Link familiasLink = linkTo(FamilyController.class).withSelfRel();
                Link comentariosLink = linkTo(CommentsController.class).withSelfRel();

                //Creanis 3 objetos index
                Index publi = new Index("Personas");
                Index comment = new Index("Comentarios");
                Index familias = new Index("Familias");

                //Creamos los recursos existentes en la api
                Resource<Index> pR = new Resource<>(publi);
                Resource<Index> cR = new Resource<>(comment);
                Resource<Index> fR = new Resource<>(familias);

                //Seteamos en el recurso el enlace
                pR.add(publicacionesLink);
                cR.add(comentariosLink);
                fR.add(familiasLink);

                //añadimos los recursos con los enlacez hateoas en  el arrayList que devolveremos en la respuesta
                resources.add(pR);
                resources.add(cR);
                resources.add(fR);

            }

            response = new ResponseEntity<>(resources, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return response;

    }
}
