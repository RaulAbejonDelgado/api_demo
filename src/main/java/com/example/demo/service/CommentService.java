package com.example.demo.service;

import com.example.demo.controller.CommentsController;
import com.example.demo.controller.FamilyController;
import com.example.demo.controller.PersonController;
import com.example.demo.dao.CommentsDao;
import com.example.demo.dao.DataFlowDao;
import com.example.demo.dao.FamilyDao;
import com.example.demo.dao.PersonDao;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Key;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


public class CommentService {

    private static CommentService INSTANCE = null;

    private static CommentsDao comentarioDao = null;
    private static FamilyDao familyDao = null;
    private static DataFlowDao dataFlowDao = null;
    private static PersonDao personaDao = null;

    private CommentService() throws UnknownHostException {

        super();
        comentarioDao = CommentsDao.getInstance();
        dataFlowDao = DataFlowDao.getInstance();
        familyDao = FamilyDao.getInstance();
        personaDao = PersonDao.getInstance();

    }

    public static synchronized CommentService getInstance() throws UnknownHostException {

        if (INSTANCE == null) {
            INSTANCE = new CommentService();
        }

        return INSTANCE;
    }


    public ArrayList<Resource<Comment>> listar() {

        ArrayList<Comment> comentarios;
        ArrayList<Resource<Comment>> resourcesComentariosArray = new ArrayList<>();
        Resource<Comment> resourceComment;

        comentarios = (ArrayList<Comment>) comentarioDao.listarTodos();

        //seteamos las personas dentro de las familias
        for (Comment c : comentarios){
            Family[] f = c.getFamilia();
            for(Family g : f){
                Family f1 = familyDao.obtenerPorId(g.getSelfId());
                c.setFamilia(new Family[]{f1});

            }
        }

        if (comentarios.size() > 0) {

            System.out.println("*************Pasamos por PersonController-get*************");

            for (Comment c : comentarios) {

                resourceComment = new Resource<>(c);
                //completamos el objeto

                //hateoas
                Link selfLink = linkTo(CommentsController.class).withSelfRel();
                resourceComment.add(selfLink);

                Link detailLink = linkTo(CommentsController.class).slash(c.getSelfId()).withRel("Detalle comentario");
                resourceComment.add(detailLink);

                Link familyLink = linkTo(FamilyController.class).slash(c.getFamilia()[0].getSelfId()).withRel("Familias");
                resourceComment.add(familyLink);

                Link authorLink = linkTo(PersonController.class).slash(c.getPersona()[0].getselfId()).withRel("Detalle Author");
                resourceComment.add(authorLink);

                resourcesComentariosArray.add(resourceComment);

            }
        }

        return resourcesComentariosArray;
    }

    public ArrayList<Resource<Comment>> obtenerPorId(int id) {

        Comment c = comentarioDao.obtenerPorId(id);
        ArrayList<Resource<Comment>> resoucesPerson = new ArrayList<>();
        Resource<Comment> resource;

        //seteamos las personas dentro de las familias
        Family f1 = familyDao.obtenerPorId(c.getSelfId());
        c.setFamilia(new Family[]{f1});

        if (c != null) {
            resource = new Resource<>(c);
            Link selfLink = linkTo(CommentsController.class).slash(c.getSelfId()).withSelfRel();
            resource.add(selfLink);
            resoucesPerson.add(resource);
        }

        return resoucesPerson;
    }

    public boolean eliminar(int id) {

        boolean resul = false;
        Comment c = comentarioDao.obtenerPorId(id);
        WriteResult wr = comentarioDao.delete(c);

        if (wr.getN() == 1) {
            resul = true;
        }

        return resul;
    }

    public ArrayList<Resource<Comment>> crear(Comment c) {

        Key<Comment> commentKey = comentarioDao.crear(c);
        ArrayList<Resource<Comment>> resoucesPerson = new ArrayList<>();
        Resource<Comment> resource;

        //seteamos las personas dentro de las familias
        //Family f1 = familyDao.obtenerPorId(c.getSelfId());
        Family[] f1 = c.getFamilia();
        Family f2 = new Family();
        Person p2 = new Person();

        Person[] pArray = c.getPersona();

        for(Person p : pArray){
            p2 = personaDao.obtenerPorId(p.getselfId());
        }
        for(Family f : f1){

             f2 = familyDao.obtenerPorId(f.getSelfId());

        }
        c.setPersona(new Person[]{p2});
        c.setFamilia(new Family[]{f2});

        if (commentKey.getId() != null) {

            resource = new Resource<>(c);
            Link selfLink = linkTo(CommentsController.class).slash(c.getSelfId()).withSelfRel();
            resource.add(selfLink);
            Link familyLink = linkTo(FamilyController.class).slash(c.getSelfId()).withRel("Familia");
            resource.add(familyLink);
            Link userLink = linkTo(PersonController.class).slash(c.getPersona()[0].getselfId()).withRel("Autor");
            resource.add(userLink);

            resoucesPerson.add(resource);
        }

        return resoucesPerson;
    }

    public ArrayList<Resource<Comment>> modficar(int id, Comment c) {

        ArrayList<Resource<Comment>> resoucesPerson = new ArrayList<>();
        Resource<Comment> resource;

        Key<Comment> commentKey = comentarioDao.modificar(id, c);
        //seteamos las personas dentro de las familias
        //Family f1 = familyDao.obtenerPorId(c.getSelfId());
        Family[] f1 = c.getFamilia();
        Family f2 = new Family();
        Person p2 = new Person();

        Person[] pArray = c.getPersona();

        for(Person p : pArray){
            p2 = personaDao.obtenerPorId(p.getselfId());
        }
        for(Family f : f1){

            f2 = familyDao.obtenerPorId(f.getSelfId());

        }
        c.setPersona(new Person[]{p2});
        c.setFamilia(new Family[]{f2});


        if (commentKey.getId() != null && !commentKey.getId().equals("")) {

            resource = new Resource<>(c);

            Link selfLink = linkTo(CommentsController.class).slash(c.getSelfId()).withSelfRel();
            resource.add(selfLink);

            Link familyLink = linkTo(FamilyController.class).slash(c.getSelfId()).withRel("Familia");
            resource.add(familyLink);

            Link userLink = linkTo(PersonController.class).slash(c.getPersona()[0].getselfId()).withRel("Autor");
            resource.add(userLink);

            resoucesPerson.add(resource);
        }

        return resoucesPerson;

    }

    public void exportar(String collection) throws Exception {

        dataFlowDao.objectExport(collection);

    }

    public void importar(String collectionName) throws Exception {

        dataFlowDao.objectImport(collectionName);

    }

    public ArrayList<Resource<Comment>> byUserId(int id) {
        Person p = personaDao.obtenerPorId(id);
        ArrayList<Comment> comentariosPorPersona = new ArrayList<>();
        ArrayList<Resource<Comment>> resourcesComentariosArray = new ArrayList<>();
        Resource<Comment> resourceComment;
        comentariosPorPersona = (ArrayList<Comment>) comentarioDao.obtenerByUser(p);

        for(Comment c: comentariosPorPersona){
            resourceComment = new Resource<>(c);
            resourcesComentariosArray.add(resourceComment);
        }

        return resourcesComentariosArray;


    }

    public ArrayList<Resource<Comment>> byFamilyId(int id) {

        Family f = familyDao.obtenerPorId(id);
        ArrayList<Comment> comentariosPorFamilia = new ArrayList<>();
        ArrayList<Resource<Comment>> resourcesComentariosArray = new ArrayList<>();
        Resource<Comment> resourceComment;
        comentariosPorFamilia = (ArrayList<Comment>) comentarioDao.obtenerByFamily(f);

        for(Comment c: comentariosPorFamilia){
            resourceComment = new Resource<>(c);
            resourcesComentariosArray.add(resourceComment);
        }

        return resourcesComentariosArray;


    }

}
