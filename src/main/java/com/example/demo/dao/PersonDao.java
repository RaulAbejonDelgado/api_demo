package com.example.demo.dao;

import com.example.demo.DataSourceConfiguration;
import com.example.demo.pojo.Person;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class PersonDao {

    private static PersonDao INSTANCE = null;
    ArrayList<Person> personas = null;
    private static PersonDao personaDao = null;


    @Autowired
    private Datastore datastore;


    private static final String DB = "publicaciones";
    private static final String COLLECTION = "persons";


    public PersonDao() throws UnknownHostException {
        super();
        datastore = DataSourceConfiguration.getConnection();


    }

    public static synchronized PersonDao getInstance() throws UnknownHostException {
        if (INSTANCE == null) {
            INSTANCE = new PersonDao();
        }
        return INSTANCE;
    }


    public List listar() throws UnknownHostException {

        return datastore.createQuery(Person.class).asList();


    }

    public Person obtenerPorId(int id) throws UnknownHostException {

        return datastore.find(Person.class).field("selfId").equal(id).get();

    }

    public WriteResult delete(Person p) {

        return datastore.delete(p);

    }





    public Key<Person> crear(Person p) throws UnknownHostException {

        p.setSelfId(listar().size() + 1);

        return datastore.save(p);



    }


    public Key<Person> modificar(int id, Person p) throws  UnknownHostException{
        Key<Person> personUpdate = null ;
        Person pe = obtenerPorId(id);
        if (pe != null){
            p.setId(pe.getId());
            personUpdate =  datastore.merge(p);
        }

        return personUpdate;


    }


}
