package com.example.demo.service;

import com.example.demo.controller.FamilyController;
import com.example.demo.controller.PersonController;
import com.example.demo.dao.FamilyDao;
import com.example.demo.dao.PersonDao;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import org.springframework.hateoas.Link;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class FamilyService {

    private static FamilyService INSTANCE = null;
    private static FamilyDao familiasDao = null;
    private static PersonDao personasDao = null;

    public static synchronized FamilyService getInstance() throws UnknownHostException {
        if (INSTANCE == null) {
            INSTANCE = new FamilyService();
        }
        return INSTANCE;
    }

    public FamilyService() throws UnknownHostException {
        super();

        familiasDao = FamilyDao.getInstance();
        personasDao = PersonDao.getInstance();
    }


//    public ArrayList<Family> listar() throws UnknownHostException {
//        ArrayList<Family> familias = new ArrayList<Family>();
//
//        familias = familiasDao.listarTodos();
//        Person[] personasFamilia ;
//
//        if (familias.size() > 0) {
//            System.out.println("*************Pasamos por FamilyController-get*************");
//            for (Family f : familias) {
//                Link selfLink = linkTo(FamilyController.class).withSelfRel();
//                f.add(selfLink);
//                Link detailLink = linkTo(FamilyController.class).slash(f.getFamilyId()).withRel("Detalle familia");
//                f.add(detailLink);
//                personasFamilia = f.getPersonas();
//                if (personasFamilia.length > 0){
//                    for(Person p : personasFamilia){
//
//                        //Aqui añadimos el enlace en el objeto anidado
//                        Link childLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
//                        p.add(childLink);
//
//                        //Aqui añadimos el enlace en la seccion de enlaces del propio objeto
//                        Link childElemetLink = linkTo(PersonController.class).slash(p.getselfId()).withRel("Detalle hijos");
//                        f.add(childElemetLink);
//
//                    }
//                }
//
//
//            }
//        }
//
//        return familias;
//    }
//
//
//    public Family obtenerPorId(int id) throws UnknownHostException {
//        Family f = familiasDao.obtenerPorId(id);
//
//        Link selfLink = linkTo(FamilyController.class).slash(f.getFamilyId()).withSelfRel();
//        f.add(selfLink);
//        Link detailLink = linkTo(FamilyController.class).withRel("Listar familias");
//        f.add(detailLink);
//        Person[] personasFamilia ;
//
//        personasFamilia = f.getPersonas();
//        for(Person p : personasFamilia){
//
//            //Aqui añadimos el enlace en el objeto anidado
//            Link childLink = linkTo(PersonController.class).slash(p.getselfId()).withSelfRel();
//            p.add(childLink);
//
//            //Aqui añadimos el enlace en la seccion de enlaces del propio objeto
//            Link childElemetLink = linkTo(PersonController.class).slash(p.getselfId()).withRel("hijos");
//            f.add(childElemetLink);
//        }
//
//
//
//        return f;
//    }
//
//    public boolean eliminar(int id) throws  UnknownHostException {
//        boolean resul = false;
//        if(familiasDao.eliminar(id)){
//            resul = true;
//        }
//
//        return resul;
//    }
//
//    public boolean crear(Family f) throws  UnknownHostException {
//        boolean resul  = false;
//
//        if(familiasDao.crear(f)){
//            resul = true;
//            Link selfLink = linkTo(FamilyController.class).slash(f.getFamilyId()).withSelfRel();
//            f.add(selfLink);
//        }
//
//        return resul;
//    }
//
//    public boolean modficar(int id, Family f) throws  UnknownHostException{
//        boolean resul = false;
//
//        if(familiasDao.modificar(id, f)){
//            resul = true;
//            Link selfLink = linkTo(FamilyController.class).slash(f.getFamilyId()).withSelfRel();
//            f.add(selfLink);
//        }
//
//        return resul;
//    }


}
