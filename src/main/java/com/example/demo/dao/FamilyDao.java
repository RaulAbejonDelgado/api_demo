package com.example.demo.dao;

import com.example.demo.DataSourceConfiguration;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class FamilyDao {

    private static FamilyDao INSTANCE = null;
    private static PersonDao personaDao = null ;

    @Autowired
    private Datastore datastore;

    public static synchronized FamilyDao getInstance() throws UnknownHostException {

        if (INSTANCE == null) {

            INSTANCE = new FamilyDao();

        }

        return INSTANCE;
    }

    private FamilyDao() throws UnknownHostException {

        super();

        datastore = DataSourceConfiguration.getConnection();
        personaDao = PersonDao.getInstance();

    }

    public List<Family> listarTodos() {

        return datastore.createQuery(Family.class).asList();

    }


    public Family obtenerPorId(int id) {

        return datastore.find(Family.class).field("selfId").equal(id).get();

    }

    public WriteResult eliminar(Family f) {

        return datastore.delete(f);

    }

    /**
     * Para la creacion de familias debemos setear correctamente los objetos anidados en este caso personas.
     * por ello primero debemos recuperar las personas pertenecientes en caso de que existan si no existen habra que crearla
     * @param f
     * @return
     */
    public Key<Family> crear(Family f) {

        //seteamos el self id
        f.setSelfId(listarTodos().size() + 1);

        /**
         * Antes de crear una familia miramos si tiene personas,
         * si tiene personas pero no nos dan el _id lo buscamos y lo seteamos antes de la creacion de la familia
         */
        Person[] personas = f.getPersonas();
        if(personas.length > 0){

            for (Person p : personas){

                Person pe = personaDao.obtenerPorId(p.getselfId());

                if(pe != null) {
                    p.setId(pe.getId());
                    p.setFamilyId(f.getSelfId());
                }
            }
        }

        return datastore.save(f);

    }

    public Iterable<Key<Family>> crearPorLote(ArrayList<Family> familias) {

        return datastore.save(familias);

    }

    public Key<Family> modificar(int id, Family f) {

        Key<Family> familyUpdate = null;
        Family fOld = obtenerPorId(id);
        Person[] personas = f.getPersonas();
        if(personas.length > 0){

            for (Person p : personas){

                Person pe = personaDao.obtenerPorId(p.getselfId());

                if(pe != null) {
                    p.setId(pe.getId());
                    p.setFamilyId(f.getSelfId());
                }
            }
        }

        if (fOld != null) {

            f.setId(fOld.getId());
            familyUpdate = datastore.merge(f);

        }

        return familyUpdate;

    }

}
