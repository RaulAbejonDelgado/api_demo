package com.example.demo.service;

import com.example.demo.controller.PersonController;
import com.example.demo.dao.PersonDao;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Person;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Key;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PersonService {

    private static PersonService INSTANCE = null;
    private static CommentService comentService = null;

    private static PersonDao personDao = null;


    public PersonService() throws UnknownHostException {
        super();
        personDao = PersonDao.getInstance();
        //comentService = ComentService.getInstance();

    }

    public static synchronized PersonService getInstance() throws UnknownHostException {
        if (INSTANCE == null) {
            INSTANCE = new PersonService();
        }
        return INSTANCE;
    }


    public ArrayList<Resource<Person>> listar() throws UnknownHostException {

        ArrayList<Person> persons = new ArrayList<Person>();
        ArrayList<Resource<Person>> resoucesPerson =new ArrayList<Resource<Person>>();
        Resource<Person> resource = null;

        ArrayList<Comment> comentariosUsuario = new ArrayList<Comment>();


        persons = (ArrayList<Person>) personDao.listar();
        for (Person p : persons){
            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
            resource = new Resource<Person>(p);

            resource.add(selfLink);
            resoucesPerson.add(resource);
        }

        return resoucesPerson;


        }

    public ArrayList<Resource<Person>> obtenerPorId(int id) throws UnknownHostException {

        Person p = personDao.obtenerPorId(id);
        ArrayList<Comment> comentariosUsuario = new ArrayList<Comment>();
        ArrayList<Resource<Person>> resoucesPerson =new ArrayList<Resource<Person>>();
        Resource<Person> resource = null;

        if(p != null){
            resource = new Resource<Person>(p);
            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
            resource.add(selfLink);
            resoucesPerson.add(resource);
        }

//        Link familyLink = linkTo(FamilyController.class).slash(p.getFamilyId()).withRel("Detalle Familia");
//        p.add(familyLink);
//        Link listAllLink = linkTo(PersonController.class).withRel("Listar personas");
//        p.add(listAllLink);
//
//        for(Coment c : comentariosUsuario){
//            Link listComents = linkTo(PublicacionController.class).slash(c.getComentarioId()).withRel("Comentarios");
//            p.add(listComents);
//        }


        return resoucesPerson;
    }

    public boolean eliminar(int id) throws  UnknownHostException {
        boolean resul = false;

        WriteResult wr = personDao.delete(personDao.obtenerPorId(id));

        if(wr.getN() == 1){
            resul = true;
        }

        return resul;
    }

    public ArrayList<Resource<Person>> crear(Person p) throws  UnknownHostException {

        boolean resul  = false;

        ArrayList<Resource<Person>> resoucesPerson =new ArrayList<Resource<Person>>();
        Resource<Person> resource = null;

        if(personDao.crear(p).getId() != null && !personDao.crear(p).getId().equals("") ){

            System.out.println(p);
            resul = true;
            resource = new Resource<Person>(p);
            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
            resource.add(selfLink);
            resoucesPerson.add(resource);

        }

        return resoucesPerson;
    }

    public ArrayList<Resource<Person>> modficar(int id, Person p) throws  UnknownHostException{
        boolean resul = false;
        ArrayList<Resource<Person>> resoucesPerson =new ArrayList<Resource<Person>>();
        Resource<Person> resource = null;

        Key<Person> personUpdate = personDao.modificar(id, p);
        System.out.println( personUpdate);
        if(personUpdate != null){
            resul = true;
            resource = new Resource<Person>(p);
            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
            resource.add(selfLink);
            resoucesPerson.add(resource);
        }

        return resoucesPerson;
    }
}
