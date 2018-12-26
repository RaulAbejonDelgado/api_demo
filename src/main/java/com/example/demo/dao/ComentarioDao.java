package com.example.demo.dao;

import com.example.demo.pojo.Coment;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;
import org.mongojack.WriteResult;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

import static com.example.demo.dao.MongoConector.getConnectionDbAndCollection;

public class ComentarioDao {

    private static ComentarioDao INSTANCE = null;

    private static final String DB = "publicaciones";
    private static final String COLLECTION = "comentarios";
    private static PersonDao personDao = null ;
    private static FamilyDao familyDao = null;

    public static synchronized ComentarioDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ComentarioDao();
        }
        return INSTANCE;
    }


    public ArrayList<Coment> listarTodos() throws UnknownHostException {
        ArrayList<Coment>  comentarios= new ArrayList<Coment>();
//        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
//
//        //JacksonDBCollection<Coment, String> coll = JacksonDBCollection.wrap(collection, Coment.class, String.class);
//        // Busco todos los documentos de la colección y los imprimo
//        try (DBCursor<Coment> cursor = collection.find()) {
//            while (cursor.hasNext()) {
//                comentarios.add(cursor.next());
//
//            }
//        }
//        System.out.println(comentarios);
//
//        return comentarios;
        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);


        // Busco todos los documentos de la colección y los imprimo
        try (com.mongodb.DBCursor cursor = collection.find()) {
            while (cursor.hasNext()) {

                comentarios.add(deMongoaJava((BasicDBObject) cursor.next()));
                System.out.println(comentarios);

            }
        }


        return comentarios;
    }


    public Coment obtenerPorId(int id) throws UnknownHostException {
        Coment c = new Coment();

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
        JacksonDBCollection<Coment, String> coll = JacksonDBCollection.wrap(collection, Coment.class, String.class);

        BasicDBObject query = new BasicDBObject();
        query.put("comentarioId", id);

        try (DBCursor<Coment> cursor = coll.find(query)) {
            while (cursor.hasNext()) {

                c = cursor.next();

            }
        }
        c.toString();

        return c;
    }

    public boolean eliminar(int id) throws UnknownHostException {
        boolean resul = false;
        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
        JacksonDBCollection<Coment, String> coll = JacksonDBCollection.wrap(collection, Coment.class, String.class);
        long numDocumentos = collection.getCount();

        DBObject findDoc = new BasicDBObject("comentarioId", id);
        collection.remove(findDoc);
        if (collection.getCount() < numDocumentos) {
            resul = true;
        }

        return resul;

    }


    private BasicDBObject toDBObjectFromJava(Coment c) {

        // Creamos una instancia BasicDBObject
        BasicDBObject dBObjectComent = new BasicDBObject();

        dBObjectComent.append("comentarioId", c.getComentarioId());

        dBObjectComent.append("texto", c.getTexto());

        BasicDBObject p = new BasicDBObject();

        p.append("persona", c.getPersona());

        dBObjectComent.append("persona", p);
        //dBObjectComent.append("sona", c.getPersona());


        return dBObjectComent;
    }


    //Transformo un objecto que me da MongoDB a un Objecto Java
    private Coment deMongoaJava(BasicDBObject toDBObjectComentario) {
        Family familia = new Family();
        Person persona = new Person();
        Coment c = new Coment();
        c.set_id(toDBObjectComentario.getString("_id"));
        c.setComentarioId(toDBObjectComentario.getInt("comentarioId"));
        c.setTexto(toDBObjectComentario.getString("texto"));

        BasicDBObject familiObject = new BasicDBObject();
        BasicDBObject personObject = new BasicDBObject();
        familiObject = (BasicDBObject) toDBObjectComentario.get("familia");

        personObject = (BasicDBObject) toDBObjectComentario.get("persona");

        familia.setFamilyId((familiObject.getInt("familyId")));
        familia.setNombre(familiObject.getString("nombre"));
        familia.set_id(familiObject.getString("_id"));

        System.out.println(familiObject.getString("_id"));

        persona.set_id(personObject.getString("_id"));
        persona.setPersonId(personObject.getInt("selfId"));

        c.setFamilia(familia);
        c.setPersona(persona);

        return c;
    }


    public boolean crear(Coment c) throws UnknownHostException {

        boolean result = false;
        DBCollection collection = null;
        try {
            collection = getConnectionDbAndCollection(DB, COLLECTION);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        JacksonDBCollection<Coment, String> coll = JacksonDBCollection.wrap(collection, Coment.class, String.class);

        long numDoc = collection.getCount() + 1;
        c.setComentarioId((int) numDoc);


        org.mongojack.WriteResult<Coment, String> res = coll.insert(c);
        Coment dObj = res.getSavedObject();
        c.set_id(dObj.get_id());

        System.out.println(dObj.toString());

        if (dObj != null) {

            result = true;
        }


        return result;
    }




    public boolean modificar(int id, Coment c) throws  UnknownHostException{
//        boolean resul = false;
//        Coment ce = new Coment();
//
//        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
//        JacksonDBCollection<Coment, String> coll = JacksonDBCollection.wrap(collection, Coment.class, String.class);
//
//        BasicDBObject query = new BasicDBObject();
//        query.put("comentarioId", id);
//        c.setComentarioId(id);
//        BasicDBObject datosNuevos = toDBObjectFromJava(c);
//
//        WriteResult<Coment, String> wr = coll.update(query,datosNuevos);
//        if(wr.isUpdateOfExisting()){
//            resul = true;
//            ce = obtenerPorId(c.getComentarioId());
//            c.set_id(ce.get_id());
//        }
//
//        return resul;
//
//    }

        boolean result = false;
        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
        JacksonDBCollection<Coment, String> coll = JacksonDBCollection.wrap(collection, Coment.class, String.class);
        BasicDBObject query = new BasicDBObject();
        BasicDBObject obj= new BasicDBObject();
        BasicDBObject fam= new BasicDBObject();
        BasicDBObject per= new BasicDBObject();

        Coment co = obtenerPorId(id);
        //Person po =

        co.toString();




        obj.append("comentarioId",id);
        obj.append("texto",c.getTexto() != null || !c.getTexto().contains("") ? c.getTexto() : co.getTexto());

        per.append("_id",c.getPersona().get_id().contains("") ? co.get_id() : c.getPersona().get_id() );
        per.append("selfId",c.getPersona().getselfId() == 0 ? co.getPersona().getselfId() :c.getPersona().getselfId());
        per.append("nombre",c.getPersona().getNombre() == null ? co.getPersona().getNombre() : c.getPersona().getNombre());
        per.append("familyId",c.getPersona().getFamilyId() == 0 ? co.getFamilia().getFamilyId() :  c.getFamilia().getFamilyId());


        fam.append("_id",c.getFamilia().get_id() == null ? co.getFamilia().get_id() : c.getFamilia().get_id());
        fam.append("familyId",c.getFamilia().getFamilyId() == 0 ? co.getFamilia().getFamilyId()  : c.getFamilia().getFamilyId() );
        fam.append("nombre",c.getFamilia().getNombre().contains("") ? co.getFamilia().getNombre() : c.getFamilia().getNombre());



        obj.append("familia",fam);
        obj.append("persona",per);



        query.put("comentarioId", id);
        WriteResult<Coment,String> res = coll.update(query,obj);


        if (res.getN()==1) {

            result = true;
        }
        return result;
    }



    public ArrayList<Coment> comentariosPorPersona(int id) throws UnknownHostException {
//        DBCursor<Coment> comentarios;
//        ArrayList<Coment> coment = new ArrayList<Coment>();
//
//        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
//        JacksonDBCollection<Coment, String> coll = JacksonDBCollection.wrap(collection, Coment.class, String.class);
//
//        BasicDBObject query = new BasicDBObject();
//        query.put("persona.personaId", id);


//        try (Db<Coment> cursor = coll.find(query)) {
//            while(cursor.hasNext()){
//                System.out.println(cursor.next());
//                coment.add(cursor.next());
//            }
//        }
        ArrayList<Coment>  comentarios= new ArrayList<Coment>();
        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
        BasicDBObject query = new BasicDBObject();
        query.put("persona.personaId", id);

        // Busco todos los documentos de la colección y los imprimo
        try (com.mongodb.DBCursor cursor = collection.find(query)) {
            while (cursor.hasNext()) {

                comentarios.add(deMongoaJava((BasicDBObject) cursor.next()));
                System.out.println(comentarios);

            }
        }


        return comentarios;

    }




}
