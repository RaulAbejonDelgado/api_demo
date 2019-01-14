package com.example.demo.dao;

import com.example.demo.DataSourceConfiguration;
import com.example.demo.pojo.Person;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;
import java.util.List;

/**
 * En esta version usamos el ODM MORPHIA @see https://github.com/MorphiaOrg/morphia
 */
public class PersonDao {

    private static PersonDao INSTANCE = null;

    @Autowired
    private Datastore datastore;

    private PersonDao() throws UnknownHostException {

        super();
        datastore = DataSourceConfiguration.getConnection();

    }

    public static synchronized PersonDao getInstance() throws UnknownHostException {

        if (INSTANCE == null) {

            INSTANCE = new PersonDao();

        }

        return INSTANCE;
    }

    /**
     *
     * @return List de Personas
     */
    public List listar() {

        return datastore.createQuery(Person.class).asList();

    }

    /**
     *
     * @param id referencia a la propierdad selfId del objeto comentario
     * @return objeto tipo Person
     */
    public Person obtenerPorId(int id) {

        return datastore.find(Person.class).field("selfId").equal(id).get();

    }

    /**
     *
     * @param p objeto tipo Person
     * @return WriteResult
     * @throws Exception si llega un objeto no existente en la base de datos
     */
    public WriteResult delete(Person p) throws Exception {

        if(p != null){

            return datastore.delete(p);

        }else{
            throw new Exception("Not found, imposible delete it");
        }


    }

    /**
     *
     * @param p objeto tipo Person
     * @return Key<Person> ??
     */
    public Key<Person> crear(Person p) {

        p.setSelfId(listar().size() + 1);

        return datastore.save(p);

    }

    /**
     * Usada para la practica con spring batch
     * @param personas Un listado de personas generados de la lectura de archivos xml
     * @return Iterable<Key<Person>>??
     */
    public Iterable<Key<Person>> crearPorLote(List<Person> personas){

        return datastore.save(personas);


    }

    /**
     *
     * @param id referencia a la propiedad selfid del objeto Person
     * @param p Objeto tipo Person
     * @return Key<Person>
     */
    public Key<Person> modificar(int id, Person p) {

        Key<Person> personUpdate = null ;
        Person pe = obtenerPorId(id);

        if (pe != null){

            p.setId(pe.getId());

            personUpdate =  datastore.merge(p);

        }

        return personUpdate;

    }

    /**
     * Funcionamildad para el loguo del cliente
     * @param p Objeto tipo Person
     * @return Person
     */
    public Person obtenerPorNombrePassword(Person p) {

        return datastore.find(Person.class).field("correo").equal(p.getCorreo()).field("password").equal(p.getPassword()).get();
    }

    /**
     * Funcionalidad para el cliente
     * @param nombre tipo string referencia a la propierdad nombre del objeto Person
     * @return Objeto tipo Person
     */
    public Person obtenerPorNombre(String nombre) {

        return datastore.find(Person.class).field("nombre").equal(nombre).get();

    }
}
