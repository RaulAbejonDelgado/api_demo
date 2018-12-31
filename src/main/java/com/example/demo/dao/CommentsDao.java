package com.example.demo.dao;

import com.example.demo.DataSourceConfiguration;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.demo.dao.MongoConector.getConnectionDbAndCollection;

public class CommentsDao {

    private static CommentsDao INSTANCE = null;

    private static final String DB = "publicaciones";
    private static final String COLLECTION = "comentarios";
    private static PersonDao personDao = null ;
    private static FamilyDao familyDao = null;

    @Autowired
    private Datastore datastore;

    public static synchronized CommentsDao getInstance() throws UnknownHostException{
        if (INSTANCE == null) {
            INSTANCE = new CommentsDao();
        }
        return INSTANCE;
    }

    public CommentsDao() throws UnknownHostException{
        super();
        datastore = DataSourceConfiguration.getConnection();
    }


    public List<Comment> listarTodos() throws UnknownHostException {

        return datastore.createQuery(Comment.class).asList();
    }


    public Comment obtenerPorId(int id) throws UnknownHostException {

        return datastore.find(Comment.class).field("selfId").equal(id).get();

    }

    public WriteResult delete(Comment c) {

        return datastore.delete(c);

    }



    public Key<Comment> crear(Comment c) throws UnknownHostException {

        c.setSelfId(listarTodos().size() + 1);

        return datastore.save(c);

    }

    public Key<Comment> modificar(int id, Comment c) throws  UnknownHostException{

        Key<Comment> personUpdate = null ;
        Comment cOrigin = obtenerPorId(id);

        if (cOrigin != null){

            c.setId(cOrigin.getId());
            personUpdate =  datastore.merge(c);

        }

        return personUpdate;

    }

//
//        boolean result = false;
//        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
//        JacksonDBCollection<Coment, String> coll = JacksonDBCollection.wrap(collection, Coment.class, String.class);
//        BasicDBObject query = new BasicDBObject();
//        BasicDBObject obj= new BasicDBObject();
//        BasicDBObject fam= new BasicDBObject();
//        BasicDBObject per= new BasicDBObject();
//
//        Coment co = obtenerPorId(id);
//        //Person po =
//
//        co.toString();
//
//
//
//
//        obj.append("comentarioId",id);
//        obj.append("texto",c.getTexto() != null || !c.getTexto().contains("") ? c.getTexto() : co.getTexto());
//
//        per.append("_id",c.getPersona().get_id().contains("") ? co.get_id() : c.getPersona().get_id() );
//        per.append("selfId",c.getPersona().getselfId() == 0 ? co.getPersona().getselfId() :c.getPersona().getselfId());
//        per.append("nombre",c.getPersona().getNombre() == null ? co.getPersona().getNombre() : c.getPersona().getNombre());
//        per.append("familyId",c.getPersona().getFamilyId() == 0 ? co.getFamilia().getFamilyId() :  c.getFamilia().getFamilyId());
//
//
//        fam.append("_id",c.getFamilia().get_id() == null ? co.getFamilia().get_id() : c.getFamilia().get_id());
//        fam.append("familyId",c.getFamilia().getFamilyId() == 0 ? co.getFamilia().getFamilyId()  : c.getFamilia().getFamilyId() );
//        fam.append("nombre",c.getFamilia().getNombre().contains("") ? co.getFamilia().getNombre() : c.getFamilia().getNombre());
//
//
//
//        obj.append("familia",fam);
//        obj.append("persona",per);
//
//
//
//        query.put("comentarioId", id);
//        WriteResult<Coment,String> res = coll.update(query,obj);
//
//
//        if (res.getN()==1) {
//
//            result = true;
//        }
//        return result;
//    }
//
//
//
//    public ArrayList<Coment> comentariosPorPersona(int id) throws UnknownHostException {
////        DBCursor<Coment> comentarios;
////        ArrayList<Coment> coment = new ArrayList<Coment>();
////
////        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
////        JacksonDBCollection<Coment, String> coll = JacksonDBCollection.wrap(collection, Coment.class, String.class);
////
////        BasicDBObject query = new BasicDBObject();
////        query.put("persona.personaId", id);
//
//
////        try (Db<Coment> cursor = coll.find(query)) {
////            while(cursor.hasNext()){
////                System.out.println(cursor.next());
////                coment.add(cursor.next());
////            }
////        }
//        ArrayList<Coment>  comentarios= new ArrayList<Coment>();
//        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
//        BasicDBObject query = new BasicDBObject();
//        query.put("persona.personaId", id);
//
//        // Busco todos los documentos de la colección y los imprimo
//        try (com.mongodb.DBCursor cursor = collection.find(query)) {
//            while (cursor.hasNext()) {
//
//                comentarios.add(deMongoaJava((BasicDBObject) cursor.next()));
//                System.out.println(comentarios);
//
//            }
//        }
//
//
//        return comentarios;
//
//    }




}
