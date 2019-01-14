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

    /**
     *
     * @return List<Family>
     */
    public List<Family> listarTodos() {

        return datastore.createQuery(Family.class).asList();

    }

    /**
     *
     * @param id referencia a la propierdad selfId del objeto Family
     * @return Family
     */
    public Family obtenerPorId(int id) {

        return datastore.find(Family.class).field("selfId").equal(id).get();

    }

    /**
     *
     * @param f Objeto tipo Family
     * @return WriteResult
     */
    public WriteResult eliminar(Family f) {

        return datastore.delete(f);

    }

    /**
     * Para la creacion de familias debemos setear correctamente los objetos anidados en este caso personas.
     * por ello primero debemos recuperar las personas pertenecientes en caso de que existan si no existen habra que crearla
     * @param f objeto tipo Familia
     * @return Key<Family>
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

    /**
     *
     * @param familias  tipo ArrayList<Family>
     * @return Iterable<Key<Family>>
     */
    public Iterable<Key<Family>> crearPorLote(ArrayList<Family> familias) {

        return datastore.save(familias);

    }

    /**
     *
     * @param id referencia a la propiedad selfId del campo Family
     * @param f Objeto tipo Family
     * @return Key<Family>
     */
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
            /**
             *  inicialmente use merge pero los objetos embebidos no los actualizaba
             *  ahora seteo el id de la db con el objeto entrante y hago un save
             *  dando el mismo resultado que el uptade ya que mongo al detectar el mismo id actualiza
             */

            familyUpdate = datastore.save(f);

        }

        return familyUpdate;

    }

}
