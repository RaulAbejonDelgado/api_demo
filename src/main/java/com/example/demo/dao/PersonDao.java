package com.example.demo.dao;

import com.example.demo.DataSourceConfiguration;
import com.example.demo.pojo.Person;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;
import java.util.List;

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

    public List listar() {

        return datastore.createQuery(Person.class).asList();

    }

    public Person obtenerPorId(int id) {

        return datastore.find(Person.class).field("selfId").equal(id).get();

    }

    public WriteResult delete(Person p) throws Exception {

        if(p != null){

            return datastore.delete(p);

        }else{
            throw new Exception("Not found, imposible delete it");
        }


    }

    public Key<Person> crear(Person p) {

        p.setSelfId(listar().size() + 1);

        return datastore.save(p);

    }

    public Iterable<Key<Person>> crearPorLote(List<Person> personas){

        return datastore.save(personas);


    }

    public Key<Person> modificar(int id, Person p) {

        Key<Person> personUpdate = null ;
        Person pe = obtenerPorId(id);

        if (pe != null){

            p.setId(pe.getId());

            personUpdate =  datastore.merge(p);

        }

        return personUpdate;

    }

    public Person obtenerPorNombrePassword(Person p) {

//        Query<Person> query = datastore.createQuery(Person.class);
//        List<String> names = Arrays.asList("correo","password");
//
//        query.or(
//                query.criteria("correo").hasAnyOf(names),
//                query.criteria("password").hasAnyOf(names)
//        );

        return datastore.find(Person.class).field("correo").equal(p.getCorreo()).field("password").equal(p.getPassword()).get();
    }

    public Person obtenerPorNombre(String nombre) {

        return datastore.find(Person.class).field("nombre").equal(nombre).get();

    }
}
