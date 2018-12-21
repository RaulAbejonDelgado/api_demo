package com.example.demo.dao;

import com.example.demo.pojo.Coment;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static com.example.demo.dao.MongoConector.getConnectionDbAndCollection;

public class ComentarioDao {

    private static ComentarioDao INSTANCE = null;

    private static final String DB = "publicaciones";
    private static final String COLLECTION = "comentarios";

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


        return dBObjectComent;
    }


    //Transformo un objecto que me da MongoDB a un Objecto Java
    private Coment deMongoaJava(BasicDBObject toDBObjectLibro) {

        Coment c = new Coment();
        c.set_id(toDBObjectLibro.getString("_id"));
        c.setComentarioId(toDBObjectLibro.getInt("comentarioId"));
        c.setTexto(toDBObjectLibro.getString("texto"));

        return c;
    }


    public boolean crear(Coment c) throws UnknownHostException {
        boolean resul = false;
        Coment ce = new Coment();
        // WriteResult wr = new WriteResult();
        BasicDBObject dBObjectFamily;

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);

        long numDocumentos = collection.getCount();
        c.setComentarioId((int) (numDocumentos + 1));
        c.toString();
        dBObjectFamily = toDBObjectFromJava(c);
        //JacksonDBCollection<Person, String> coll = JacksonDBCollection.wrap(collection, Person.class, String.class);
        WriteResult wr = collection.insert(dBObjectFamily);
        //org.mongojack.WriteResult<Person,String> test =  coll.insert(p);

        ce = obtenerPorId(c.getComentarioId());
        c.set_id(ce.get_id());


        if(numDocumentos < collection.getCount()){
            //registro insertado
            resul = true;

        }

        return resul;

    }


    public boolean modificar(int id, Coment c) throws  UnknownHostException{
        boolean resul = false;
        Coment ce = new Coment();

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
        JacksonDBCollection<Coment, String> coll = JacksonDBCollection.wrap(collection, Coment.class, String.class);

        BasicDBObject query = new BasicDBObject();
        query.put("comentarioId", id);
        c.setComentarioId(id);
        BasicDBObject datosNuevos = toDBObjectFromJava(c);

        WriteResult wr = collection.update(query,datosNuevos);
        if(wr.isUpdateOfExisting()){
            resul = true;
            ce = obtenerPorId(c.getComentarioId());
            c.set_id(ce.get_id());
        }

        return resul;

    }




}
