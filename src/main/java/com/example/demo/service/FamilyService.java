package com.example.demo.service;

import com.example.demo.controller.FamilyController;
import com.example.demo.controller.PersonController;
import com.example.demo.dao.DataFlowDao;
import com.example.demo.dao.FamilyDao;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Key;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Gestiona las operaciones entre el controlador y la capa modelo
 */
public class FamilyService {

    private static FamilyService INSTANCE = null;
    private static FamilyDao familiasDao = null;
    private static DataFlowDao dataFlowDao = null;

    public static synchronized FamilyService getInstance() throws UnknownHostException {

        if (INSTANCE == null) {

            INSTANCE = new FamilyService();

        }

        return INSTANCE;
    }

    private FamilyService() throws UnknownHostException {

        super();

        familiasDao = FamilyDao.getInstance();
        dataFlowDao = DataFlowDao.getInstance();

    }

    /**
     *
     * @return ArrayList<Resource<Family>> con los objetos Family y sus enlaces hateoas
     */
    public ArrayList<Resource<Family>> listar() {

        ArrayList<Family> familias;
        ArrayList<Resource<Family>> resourcesFamilyArray = new ArrayList<>();
        Resource<Family> resourceFamily;


        familias = (ArrayList<Family>) familiasDao.listarTodos();
        Person[] personasFamilia;

        if (familias.size() > 0) {

            System.out.println("*************Pasamos por FamilyController-get*************");

            for (Family f : familias) {

                resourceFamily = new Resource<>(f);
                Link selfLink = linkTo(FamilyController.class).withSelfRel();
                resourceFamily.add(selfLink);

                Link detailLink = linkTo(FamilyController.class).slash(f.getSelfId()).withRel("Detalle familia");
                resourceFamily.add(detailLink);

                personasFamilia = f.getPersonas();

                if (personasFamilia != null && personasFamilia.length > 0) {

                    for (Person p : personasFamilia) {

                        //Aqui añadimos el enlace en la seccion de enlaces del propio objeto
                        Link childElemetLink = linkTo(PersonController.class).slash(p.getselfId()).withRel("Detalle hijos");
                        resourceFamily.add(childElemetLink);

                    }
                }

                resourcesFamilyArray.add(resourceFamily);

            }
        }

        return resourcesFamilyArray;
    }

    /**
     *
     * @param id referencia a la propiedad selfId del objeto familia
     * @return ArrayList<Resource<Family>> con el objeto familias con sus enlaces hateoas
     */
    public ArrayList<Resource<Family>> obtenerPorId(int id) {

        Family f = familiasDao.obtenerPorId(id);
        Resource<Family> resourceFamily = new Resource<>(f);
        ArrayList<Resource<Family>> resourcesFamilyArray = new ArrayList<>();

        Link selfLink = linkTo(FamilyController.class).slash(f.getSelfId()).withSelfRel();
        resourceFamily.add(selfLink);

        Link detailLink = linkTo(FamilyController.class).withRel("Listar familias");
        resourceFamily.add(detailLink);

        Person[] personasFamilia;

        personasFamilia = f.getPersonas();

        for (Person p : personasFamilia) {

            //Aqui añadimos el enlace en la seccion de enlaces del propio objeto
            Link childElemetLink = linkTo(PersonController.class).slash(p.getselfId()).withRel("hijos");
            resourceFamily.add(childElemetLink);

        }

        resourcesFamilyArray.add(resourceFamily);

        return resourcesFamilyArray;
    }

    /**
     *
     * @param id referencia a la propiedad selfId del objeto familia
     * @return true : en caso de eliminacion correcta
     *         false: si no ha sido posible
     */
    public boolean eliminar(int id) {

        boolean resul = false;

        Family f = familiasDao.obtenerPorId(id);
        WriteResult wr = familiasDao.eliminar(f);

        if (wr.getN() == 1) {
            resul = true;
        }

        return resul;
    }

    /**
     *
     * @param f objeto familia
     * @return ArrayList<Resource<Family>> con el objeto familia y enlaces hateoas
     */
    public ArrayList<Resource<Family>> crear(Family f) {

        Resource<Family> familyresource;
        ArrayList<Resource<Family>> familyresourceArray = new ArrayList<>();

        if (familiasDao.crear(f).getId() != null && !familiasDao.crear(f).getId().equals("")) {

            familyresource = new Resource<>(f);
            Link selfLink = linkTo(FamilyController.class).slash(f.getSelfId()).withSelfRel();
            familyresource.add(selfLink);

            for (Person p : f.getPersonas()) {

                Link familyPersonLink = linkTo(PersonController.class).slash(p.getselfId()).withRel("Miembros familia");
                familyresource.add(familyPersonLink);

            }

            familyresourceArray.add(familyresource);

        }

        return familyresourceArray;
    }

    /**
     *
     * @param id referencia a la propiedad selfId del objeto Family
     * @param f Objeto tipo Family
     * @return ArrayList<Resource<Family>> con el objeto Family modificado mas enlaces hateoas
     */
    public ArrayList<Resource<Family>> modficar(int id, Family f) {

        ArrayList<Resource<Family>> resoucesFamily = new ArrayList<>();
        Key<Family> familyUpdate = familiasDao.modificar(id, f);
        Resource<Family> resource;

        if (familyUpdate != null) {

            resource = new Resource<>(f);

            Link selfLink = linkTo(FamilyController.class).slash(f.getSelfId()).withSelfRel();

            resource.add(selfLink);

            for (Person p : f.getPersonas()) {

                Link familyPersonLink = linkTo(PersonController.class).slash(p.getselfId()).withRel("Miembros familia");
                resource.add(familyPersonLink);

            }

            resoucesFamily.add(resource);

        }

        return resoucesFamily;
    }

    /**
     * Esta funcion es previa a la investigacion de spring batch
     * Usada para realizar la exportacion de una coleccion que entra como parametro de tipo String
     * @param collection nombre de la coleccion sobre la que se hara la operacion de exportacion

     */
    public void exportar(String collection) throws Exception {

        dataFlowDao.objectExport(collection);
    }

    /**
     * Esta funcion es previa a la investigacion de spring batch
     * Usada para realizar la importacion de documentos xml a mongoDB
     * @param collectionName nombre de la coleccion sobre la que se hara la operacion de exportacion
     */
    public void importar(String collectionName) throws Exception {

        dataFlowDao.objectImport(collectionName);

    }
}
