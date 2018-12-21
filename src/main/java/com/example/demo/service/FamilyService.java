package com.example.demo.service;

import com.example.demo.controller.FamilyController;
import com.example.demo.dao.FamilyDao;
import com.example.demo.dao.PersonDao;
import com.example.demo.pojo.Family;
import org.springframework.hateoas.Link;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class FamilyService {

    private static FamilyService INSTANCE = null;
    private static FamilyDao familiasDao = null;
    private static PersonDao personasDao = null;

    public static synchronized FamilyService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FamilyService();
        }
        return INSTANCE;
    }

    public FamilyService() {
        super();

        familiasDao = FamilyDao.getInstance();
        personasDao = PersonDao.getInstance();
    }


    public ArrayList<Family> listar() throws UnknownHostException {
        ArrayList<Family> familias = new ArrayList<Family>();

        familias = familiasDao.listarTodos();

        if (familias.size() > 0) {
            System.out.println("*************Pasamos por FamilyController-get*************");
            for (Family f : familias) {
                Link selfLink = linkTo(FamilyController.class).withSelfRel();
                f.add(selfLink);
                Link detailLink = linkTo(FamilyController.class).slash(f.getFamilyId()).withRel("Detalle familia");
                f.add(detailLink);

            }
        }

        return familias;
    }


    public Family obtenerPorId(int id) throws UnknownHostException {
        Family f = familiasDao.obtenerPorId(id);

        Link selfLink = linkTo(FamilyController.class).slash(f.getFamilyId()).withSelfRel();
        f.add(selfLink);
        Link detailLink = linkTo(FamilyController.class).withRel("Listar familias");
        f.add(detailLink);

        return f;
    }

    public boolean eliminar(int id) throws  UnknownHostException {
        boolean resul = false;
        if(familiasDao.eliminar(id)){
            resul = true;
        }

        return resul;
    }

    public boolean crear(Family f) throws  UnknownHostException {
        boolean resul  = false;

        if(familiasDao.crear(f)){
            resul = true;
            Link selfLink = linkTo(FamilyController.class).slash(f.getFamilyId()).withSelfRel();
            f.add(selfLink);
        }

        return resul;
    }

    public boolean modficar(int id, Family f) throws  UnknownHostException{
        boolean resul = false;

        if(familiasDao.modificar(id, f)){
            resul = true;
            Link selfLink = linkTo(FamilyController.class).slash(f.getFamilyId()).withSelfRel();
            f.add(selfLink);
        }

        return resul;
    }


}
