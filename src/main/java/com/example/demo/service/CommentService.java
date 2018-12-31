package com.example.demo.service;

import com.example.demo.controller.CommentsController;
import com.example.demo.controller.FamilyController;
import com.example.demo.controller.PersonController;
import com.example.demo.dao.CommentsDao;
import com.example.demo.dao.FamilyDao;
import com.example.demo.dao.PersonDao;
import com.example.demo.pojo.Comment;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Key;
import org.springframework.hateoas.Resource;

import java.net.UnknownHostException;
import java.util.ArrayList;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


public class CommentService {

    private static CommentService INSTANCE = null;

    private static CommentsDao comentarioDao = null;
    private static PersonDao personaDao = null;
    private static FamilyDao familyDao = null;


    public CommentService() throws UnknownHostException {

        super();
        comentarioDao = CommentsDao.getInstance();
        personaDao = PersonDao.getInstance();
        familyDao = FamilyDao.getInstance();
    }

    public static synchronized CommentService getInstance() throws UnknownHostException {

        if (INSTANCE == null) {
            INSTANCE = new CommentService();
        }

        return INSTANCE;
    }


    public ArrayList<Resource<Comment>> listar() throws UnknownHostException {

        ArrayList<Comment> comentarios = new ArrayList<Comment>();
        ArrayList<Resource<Comment>> resourcesComentariosArray = new ArrayList<Resource<Comment>>();
        Resource<Comment> resourceComment = null;
        comentarios = (ArrayList<Comment>) comentarioDao.listarTodos();

        if (comentarios.size() > 0) {
            System.out.println("*************Pasamos por PersonController-get*************");

            for (Comment c : comentarios) {
                resourceComment = new Resource(c);
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

    public ArrayList<Resource<Comment>> obtenerPorId(int id) throws UnknownHostException {

        Comment c = comentarioDao.obtenerPorId(id);
        ArrayList<Resource<Comment>> resoucesPerson =new ArrayList<Resource<Comment>>();
        Resource<Comment> resource = null;
        if(c != null){
            resource = new Resource<Comment>(c);
            Link selfLink = linkTo(CommentsController.class).slash(c.getSelfId()).withSelfRel();
            resource.add(selfLink);
            resoucesPerson.add(resource);
        }

        return resoucesPerson ;
    }

    public boolean eliminar(int id) throws  UnknownHostException {

        boolean resul = false;
        Comment c = comentarioDao.obtenerPorId(id);
        WriteResult wr = comentarioDao.delete(c);

        if(wr.getN() == 1){
            resul = true;
        }

        return resul;
    }

    public ArrayList<Resource<Comment>> crear(Comment c) throws  UnknownHostException {
        boolean resul  = false;
        Key<Comment> commentKey = comentarioDao.crear(c);
        ArrayList<Resource<Comment>> resoucesPerson =new ArrayList<Resource<Comment>>();
        Resource<Comment> resource = null;

        if(commentKey.getId() != null && !commentKey.equals("")){
            resource = new Resource(c);
            resul = true;
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

    public ArrayList<Resource<Comment>> modficar(int id, Comment c) throws  UnknownHostException{
        boolean resul = false;
        ArrayList<Resource<Comment>> resoucesPerson =new ArrayList<Resource<Comment>>();
        Resource<Comment> resource = null;

        Key<Comment> commentKey = comentarioDao.modificar(id, c);

        if(commentKey.getId() != null && !commentKey.getId().equals("")){
            resource = new Resource(c);
            resul = true;
            Link selfLink = linkTo(CommentsController.class).slash(c.getSelfId()).withSelfRel();
            resource.add(selfLink);
            resoucesPerson.add(resource);
        }

        return resoucesPerson;
    }
//
//    public ArrayList<Coment> obtenerComentPorUsuario(int id) throws  UnknownHostException {
//        ArrayList<Coment> comentarios = new ArrayList<Coment>();
//
//        comentarios = comentarioDao.comentariosPorPersona(id);
//
//        return comentarios;
//    }
}
