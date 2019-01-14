package com.example.demo.service;

import com.example.demo.controller.CommentsController;
import com.example.demo.controller.FamilyController;
import com.example.demo.controller.PersonController;
import com.example.demo.dao.CommentsDao;
import com.example.demo.dao.DataFlowDao;
import com.example.demo.dao.PersonDao;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Person;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Key;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class PersonService {

    private static PersonService INSTANCE = null;
    private static PersonDao personDao = null;
    private static DataFlowDao dataFlow = null;
    private static CommentsDao commentsDao = null;


    private PersonService() throws UnknownHostException {

        super();
        personDao = PersonDao.getInstance();
        commentsDao = CommentsDao.getInstance();
        dataFlow = DataFlowDao.getInstance();


    }


    public static synchronized PersonService getInstance() throws UnknownHostException {

        if (INSTANCE == null) {

            INSTANCE = new PersonService();

        }

        return INSTANCE;

    }

    /**
     * funcion : listar<br>
     * llama a la capa modelo y sobre su respuesta se agregan los enlaces hateoas<br>
     * @return  ArrayList<Resource<Person>> listado de Resources en cada resource hay un objeto persona y sus enlaces hateoas<br>
     *
     */
    public ArrayList<Resource<Person>> listar() {

        ArrayList<Person> persons;
        ArrayList<Resource<Person>> resoucesPerson = new ArrayList<>();
        Resource<Person> resource;


        persons = (ArrayList<Person>) personDao.listar();


        for (Person p : persons) {
            //oculto el password

            p.setPassword("****************");

            //ArrayList<Comment> comentarios = (ArrayList<Comment>) commentsDao.obtenerByUser(p);
            List<Comment> comentarios = commentsDao.obtenerByUser(p);

            resource = new Resource<>(p);
            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();

            Link familyLink = linkTo(FamilyController.class).slash(p.getFamilyId()).withRel("Familia");

            resource.add(selfLink);
            resource.add(familyLink);

            if (comentarios != null) {
                for (Comment c : comentarios) {
                    Link commentsLink = linkTo(CommentsController.class).slash(c.getSelfId()).withRel("Comentarios");
                    resource.add(commentsLink);
                }
            }

            resoucesPerson.add(resource);


        }

        return resoucesPerson;

    }

    /**
     *
     * @param id hace referencia al campo selfId del objeto persona, debe ser entero<br>
     * @return ArrayList<Resource<Person>> listado de Resources en cada resource hay un objeto persona y sus enlaces hateoas<br>
     */
    public ArrayList<Resource<Person>> obtenerPorId(int id) {

        ArrayList<Resource<Person>> resoucesPerson = new ArrayList<>();
        Resource<Person> resource;


        Person p = personDao.obtenerPorId(id);
        List<Comment> comentarios = commentsDao.obtenerByUser(p);

        if (p != null) {

            resource = new Resource<>(p);

            Link resourceLink = linkTo(PersonController.class).withRel("Listado personas");
            resource.add(resourceLink);
            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
            resource.add(selfLink);
            resoucesPerson.add(resource);

            Link familyLink = linkTo(FamilyController.class).slash(p.getFamilyId()).withRel("Detalle Familia");
            resource.add(familyLink);

            //Seteamos los comentarios en el detalle de la persona
            if (comentarios != null) {
                for (Comment c : comentarios) {
                    Link commentsLink = linkTo(CommentsController.class).slash(c.getSelfId()).withRel("Comentarios");
                    resource.add(commentsLink);
                }
            }

        }

        return resoucesPerson;
    }

    /**
     *
     * @param id hace referencia a la propiedad selfId del objeto persona<br>
     * @return true : Si se ha eliminado el registro conrrectamente<br>
     *          false: si no se ha eliminado el registro<br>
     * @throws Exception cuando se intenta eliminar un registro no existente mandamos al controlador la excepcion<br>
     */
    public boolean eliminar(int id) throws Exception {

        boolean resul = false;

        WriteResult wr = personDao.delete(personDao.obtenerPorId(id));

        if (wr.getN() == 1) {

            resul = true;

        }

        return resul;
    }

    /**
     *
     * @param p objeto persona
     * @return ArrayList<Resource<Person>> resoucesPerson objeto Person con enlaces hateoas
     */
    public ArrayList<Resource<Person>> crear(Person p) {

        ArrayList<Resource<Person>> resoucesPerson = new ArrayList<>();
        Resource<Person> resource;

        if (personDao.crear(p).getId() != null && !personDao.crear(p).getId().equals("")) {

            System.out.println(p);
            resource = new Resource<>(p);
            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
            resource.add(selfLink);

            Link resourceLink = linkTo(PersonController.class).withRel("Listado personas");
            resource.add(resourceLink);

            Link familyLink = linkTo(FamilyController.class).slash(p.getFamilyId()).withRel("Detalle Familia");
            resource.add(familyLink);

            resoucesPerson.add(resource);

        }

        return resoucesPerson;
    }

    /**
     *
     * @param id referencia a la propiedad selfId del objeto Person<br>
     * @param p objeto Person con los nuevos valores
     * @return ArrayList<Resource<Person>> resoucesPerson objeto Person con enlaces hateoas
     */
    public ArrayList<Resource<Person>> modficar(int id, Person p) {

        ArrayList<Resource<Person>> resoucesPerson = new ArrayList<>();
        Resource<Person> resource;
        List<Comment> comentarios = commentsDao.obtenerByUser(p);

        Key<Person> personUpdate = personDao.modificar(id, p);

        if (personUpdate != null) {

            resource = new Resource<>(p);

            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
            resource.add(selfLink);

            Link resourceLink = linkTo(PersonController.class).withRel("Listado personas");
            resource.add(resourceLink);

            Link familyLink = linkTo(FamilyController.class).slash(p.getFamilyId()).withRel("Detalle Familia");
            resource.add(familyLink);

            //Seteamos los comentarios en el detalle de la persona
            if (comentarios != null) {
                for (Comment c : comentarios) {
                    Link commentsLink = linkTo(CommentsController.class).slash(c.getSelfId()).withRel("Comentarios");
                    resource.add(commentsLink);
                }
            }

            resoucesPerson.add(resource);
        }

        return resoucesPerson;
    }

    /**
     * Esta funcion es previa a la investigacion de spring batch
     * Usada para realizar la exportacion de una coleccion que entra como parametro de tipo String
     * @param collection nombre de la coleccion sobre la que se hara la operacion de exportacion

     */
    public void exportar(String collection) throws Exception {

        dataFlow.objectExport(collection);
    }

    /**
     * Esta funcion es previa a la investigacion de spring batch
     * Usada para realizar la importacion de documentos xml a mongoDB
     * @param collectionName nombre de la coleccion sobre la que se hara la operacion de exportacion
     */
    public void importar(String collectionName) throws Exception {

        dataFlow.objectImport(collectionName);

    }

    /**
     * Nombre metodo loginTest
     * Este metodo solo sirve para la funcionamildad login del cliente
     * @param p objeto persona
     * @return resource con el objeto Person + enlaces hateoas, puede retornarse null en caso de no haber coincidencia
     */
    public Resource loginTest(Person p) {
        Person x;

        Resource<Person> resource = null;
        x =  personDao.obtenerPorNombrePassword(p);
        if(x != null) {
            
            resource = new Resource<>(x);

            Link resourceLink = linkTo(PersonController.class).withRel("Listado personas");
            resource.add(resourceLink);
            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
            resource.add(selfLink);


            Link familyLink = linkTo(FamilyController.class).slash(p.getFamilyId()).withRel("Detalle Familia");
            resource.add(familyLink);
            
       }

        return resource;
    }

    /**
     * Usada para una funcionalidad del cliente
     * @param nombre tipo String donde hace referencia a la propiedad nombre del objeto person
     * @return resource con el objeto Person + enlaces hateoas, puede retornarse null en caso de no haber coincidencia
     */
    public ArrayList<Resource<Person>> obtenerPorNombre(String nombre) {
        ArrayList<Resource<Person>> resoucesPerson = new ArrayList<>();
        Resource<Person> resource;


        Person p = personDao.obtenerPorNombre(nombre);
        List<Comment> comentarios = commentsDao.obtenerByUser(p);

        if (p != null) {

            resource = new Resource<>(p);

            Link resourceLink = linkTo(PersonController.class).withRel("Listado personas");
            resource.add(resourceLink);
            Link selfLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
            resource.add(selfLink);
            resoucesPerson.add(resource);

            Link familyLink = linkTo(FamilyController.class).slash(p.getFamilyId()).withRel("Detalle Familia");
            resource.add(familyLink);

            //Seteamos los comentarios en el detalle de la persona
            if (comentarios != null) {
                for (Comment c : comentarios) {
                    Link commentsLink = linkTo(CommentsController.class).slash(c.getSelfId()).withRel("Comentarios");
                    resource.add(commentsLink);
                }
            }

        }

        return resoucesPerson;

    }

}
