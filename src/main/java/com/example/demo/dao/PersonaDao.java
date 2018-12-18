package com.example.demo.dao;

import com.example.demo.pojo.Persona;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

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
        personas= new ArrayList<Persona>();

    }

    public static synchronized PersonaDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonaDao();
        }
        return INSTANCE;
    }


    public ArrayList<Persona> listar() throws UnknownHostException {

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);


        // Busco todos los documentos de la colección y los imprimo
        try (DBCursor cursor = collection.find()) {
            while (cursor.hasNext()) {

                personas.add(deMongoaJava((BasicDBObject) cursor.next()));
                System.out.println(personas);

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
    private Persona deMongoaJava(BasicDBObject toDBObjectLibro) {

        System.out.println(toDBObjectLibro);
        Persona p = new Persona();
        p.setId(toDBObjectLibro.getLong("id"));
        p.setNombre(((toDBObjectLibro.getString("nombre") == null) ? String.valueOf(' ') : toDBObjectLibro.getString("nombre")));


        return p;
    }


}
