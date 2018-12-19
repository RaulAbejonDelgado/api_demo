package com.example.demo.dao;

import com.example.demo.pojo.Hypermedia;
import com.example.demo.pojo.Persona;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;


import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static com.example.demo.dao.MongoConector.*;

public class PersonaDao {

    private static PersonaDao INSTANCE = null;
    ArrayList<Persona> personas = null;
    private static PersonaDao personaDao = null;


    private static final String DB = "publicaciones";
    private static final String COLLECTION = "personas";




    public PersonaDao() {
        super();


    }

    public static synchronized PersonaDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonaDao();
        }
        return INSTANCE;
    }


    public ArrayList<Persona> listar() throws UnknownHostException {

        personas= new ArrayList<Persona>();
        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);

        JacksonDBCollection <Persona,String> coll=JacksonDBCollection.wrap(collection,Persona.class,String.class);
        // Busco todos los documentos de la colección y los imprimo
        try (DBCursor<Persona> cursor = coll.find()) {
            while (cursor.hasNext()) {
                personas.add(cursor.next());

            }
        }
        System.out.println(personas);

        return personas;


    }


    private BasicDBObject toDBObjectLibro(Persona p) {

        // Creamos una instancia BasicDBObject
        BasicDBObject dBObjectLibro = new BasicDBObject();

        dBObjectLibro.append("id", p.getId());
        dBObjectLibro.append("nombre", p.getNombre());

        return dBObjectLibro;
    }

    // Transformo un objecto que me da MongoDB a un Objecto Java
//    private Persona deMongoaJava(BasicDBObject toDBObjectLibro) {
//        Hypermedia[] links;
//        Persona p = new Persona();
//        p.setId(toDBObjectLibro.getLong("id"));
//        p.setNombre(((toDBObjectLibro.getString("nombre") == null) ? String.valueOf(' ') : toDBObjectLibro.getString("nombre")));
//
//
//        return p;
//    }


}
