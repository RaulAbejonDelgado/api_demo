package com.example.demo.dao;

import com.example.demo.pojo.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
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

    public boolean eliminar(int id) throws UnknownHostException {
        boolean resul = false;
        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
        JacksonDBCollection<Person, String> coll = JacksonDBCollection.wrap(collection, Person.class, String.class);
        long numDocumentos = collection.getCount();

        DBObject findDoc = new BasicDBObject("personId", id);
        collection.remove(findDoc);
        if (collection.getCount() < numDocumentos) {
            resul = true;
        }

        return resul;

    }


    private BasicDBObject toDBObjectPerson(Person p) {

        // Creamos una instancia BasicDBObject
        BasicDBObject dBObjectPerson = new BasicDBObject();

        dBObjectPerson.append("personId", p.getPersonId());

        dBObjectPerson.append("nombre", p.getNombre());

        //dBObjectPerson.append("familyId", p.getFamilyId());

        return dBObjectPerson;
    }


    public boolean crear(Person p) throws UnknownHostException {
        boolean resul = false;
        Person pe = new Person();
       // WriteResult wr = new WriteResult();
        BasicDBObject dBObjectPerson;

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);

        long numDocumentos = collection.getCount();
        p.setPersonId((int) (numDocumentos + 1));
        p.toString();
        dBObjectPerson = toDBObjectPerson(p);
        //JacksonDBCollection<Person, String> coll = JacksonDBCollection.wrap(collection, Person.class, String.class);
        WriteResult wr = collection.insert(dBObjectPerson);
        //org.mongojack.WriteResult<Person,String> test =  coll.insert(p);

        pe = obtenerPorId(p.getPersonId());
        p.set_id(pe.get_id());


        if(numDocumentos < collection.getCount()){
            //registro insertado
            resul = true;

        }

        return resul;

    }


    public boolean modificar(int id, Person p) throws  UnknownHostException{
        boolean resul = false;
        Person pe = new Person();

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
        JacksonDBCollection<Person, String> coll = JacksonDBCollection.wrap(collection, Person.class, String.class);

        BasicDBObject query = new BasicDBObject();
        query.put("personId", id);
        p.setPersonId(id);
        BasicDBObject datosNuevos = toDBObjectPerson(p);

        WriteResult wr = collection.update(query,datosNuevos);
        if(wr.isUpdateOfExisting()){
            resul = true;
            pe = obtenerPorId(p.getPersonId());
            p.set_id(pe.get_id());
        }

        return resul;







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
