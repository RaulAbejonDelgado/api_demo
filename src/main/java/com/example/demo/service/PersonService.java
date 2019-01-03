package com.example.demo.service;

import com.example.demo.controller.PersonController;
import com.example.demo.dao.DataFlowDao;
import com.example.demo.dao.PersonDao;
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
    private static PersonDao personDao = null;
    private static DataFlowDao dataFlow = null;

    private PersonService() throws UnknownHostException {

        super();
        personDao = PersonDao.getInstance();
        dataFlow = DataFlowDao.getInstance();

    }

    public static synchronized PersonService getInstance() throws UnknownHostException {

        if (INSTANCE == null) {

            INSTANCE = new PersonService();

        }

        return INSTANCE;

    }

    public ArrayList<Resource<Person>> listar() {

        ArrayList<Person> persons;
        ArrayList<Resource<Person>> resoucesPerson = new ArrayList<>();
        Resource<Person> resource;

        persons = (ArrayList<Person>) personDao.listar();

        for (Person p : persons) {

            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
            resource = new Resource<>(p);

            resource.add(selfLink);
            resoucesPerson.add(resource);


        }

        return resoucesPerson;

    }

    public ArrayList<Resource<Person>> obtenerPorId(int id) {

        ArrayList<Resource<Person>> resoucesPerson = new ArrayList<>();
        Resource<Person> resource;

        Person p = personDao.obtenerPorId(id);

        if (p != null) {
            resource = new Resource<>(p);
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

    public boolean eliminar(int id) {

        boolean resul = false;

        WriteResult wr = personDao.delete(personDao.obtenerPorId(id));

        if (wr.getN() == 1) {

            resul = true;

        }

        return resul;
    }

    public ArrayList<Resource<Person>> crear(Person p) {

        ArrayList<Resource<Person>> resoucesPerson = new ArrayList<>();
        Resource<Person> resource;

        if (personDao.crear(p).getId() != null && !personDao.crear(p).getId().equals("")) {

            System.out.println(p);
            resource = new Resource<>(p);
            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
            resource.add(selfLink);
            resoucesPerson.add(resource);

        }

        return resoucesPerson;
    }

    public ArrayList<Resource<Person>> modficar(int id, Person p) {

        ArrayList<Resource<Person>> resoucesPerson = new ArrayList<>();
        Resource<Person> resource;

        Key<Person> personUpdate = personDao.modificar(id, p);

        if (personUpdate != null) {

            resource = new Resource<>(p);

            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();

            resource.add(selfLink);

            resoucesPerson.add(resource);
        }

        return resoucesPerson;
    }

    public void exportar(String collection) throws Exception {

        dataFlow.objectExport(collection);
    }

    public void importar(String collectionName) throws Exception {

        dataFlow.objectImport(collectionName);

    }
}
