package com.example.demo.dao;

import com.example.demo.pojo.Person;
import com.example.demo.pojo.Persona;
import com.mongodb.*;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static com.example.demo.dao.MongoConector.getConnectionDbAndCollection;

public class PersonDao {

    private static PersonDao INSTANCE = null;
    ArrayList<Person> personas = null;
    private static PersonDao personaDao = null;


    private static final String DB = "publicaciones";
    private static final String COLLECTION = "persons";


    public PersonDao() {
        super();


    }

    public static synchronized PersonDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonDao();
        }
        return INSTANCE;
    }


    public ArrayList<Person> listar() throws UnknownHostException {

        personas = new ArrayList<Person>();
        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);

        JacksonDBCollection<Person, String> coll = JacksonDBCollection.wrap(collection, Person.class, String.class);
        // Busco todos los documentos de la colección y los imprimo
        try (DBCursor<Person> cursor = coll.find()) {
            while (cursor.hasNext()) {
                personas.add(cursor.next());

            }
        }
        System.out.println(personas);

        return personas;


    }

    public Person obtenerPorId(int id) throws UnknownHostException {
        Person p = new Person();

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
        JacksonDBCollection<Person, String> coll = JacksonDBCollection.wrap(collection, Person.class, String.class);

        BasicDBObject query = new BasicDBObject();
        query.put("personId", id);

        try (DBCursor<Person> cursor = coll.find(query)) {
            while (cursor.hasNext()) {

                p = cursor.next();

            }
        }
        p.toString();

        return p;
    }


    private BasicDBObject toDBObjectLibro(Persona p) {

        // Creamos una instancia BasicDBObject
        BasicDBObject dBObjectLibro = new BasicDBObject();

        dBObjectLibro.append("id", p.getId());
        dBObjectLibro.append("nombre", p.getNombre());

        return dBObjectLibro;
    }

    //Transformo un objecto que me da MongoDB a un Objecto Java
//    private Persona deMongoaJava(BasicDBObject toDBObjectLibro) {
//
//        Persona p = new Persona();
//        p.setId(toDBObjectLibro.getLong("id"));
//        p.setNombre(((toDBObjectLibro.getString("nombre") == null) ? String.valueOf(' ') : toDBObjectLibro.getString("nombre")));
//
//
//        return p;
//    }


}
