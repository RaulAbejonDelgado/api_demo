package com.example.demo.dao;

import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static com.example.demo.dao.MongoConector.getConnectionDbAndCollection;

public class FamilyDao {

    private static FamilyDao INSTANCE = null;

    private static final String DB = "publicaciones";
    private static final String COLLECTION = "familias";

    public static synchronized FamilyDao getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FamilyDao();
        }
        return INSTANCE;
    }


    public ArrayList<Family> listarTodos() throws UnknownHostException {
        ArrayList<Family>  familias= new ArrayList<Family>();
        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);

        JacksonDBCollection<Family, String> coll = JacksonDBCollection.wrap(collection, Family.class, String.class);
        // Busco todos los documentos de la colección y los imprimo
        try (DBCursor<Family> cursor = coll.find()) {
            while (cursor.hasNext()) {
                familias.add(cursor.next());

            }
        }
        System.out.println(familias);

        return familias;
    }


    public Family obtenerPorId(int id) throws UnknownHostException {
        Family f = new Family();

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
        JacksonDBCollection<Family, String> coll = JacksonDBCollection.wrap(collection, Family.class, String.class);

        BasicDBObject query = new BasicDBObject();
        query.put("familyId", id);

        try (DBCursor<Family> cursor = coll.find(query)) {
            while (cursor.hasNext()) {

                f = cursor.next();

            }
        }
        f.toString();

        return f;
    }

    public boolean eliminar(int id) throws UnknownHostException {
        boolean resul = false;
        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
        JacksonDBCollection<Family, String> coll = JacksonDBCollection.wrap(collection, Family.class, String.class);
        long numDocumentos = collection.getCount();

        DBObject findDoc = new BasicDBObject("familyId", id);
        collection.remove(findDoc);
        if (collection.getCount() < numDocumentos) {
            resul = true;
        }

        return resul;

    }


    private BasicDBObject toDBObjectToJava(Family f) {

        // Creamos una instancia BasicDBObject
        BasicDBObject dBObjectFamily = new BasicDBObject();

        dBObjectFamily.append("familyId", f.getFamilyId());

        dBObjectFamily.append("nombre", f.getNombre());

        BasicDBObject dBObjectPersona = new BasicDBObject();

        //creo array del tamaño de las personas y si viene null creamos hueco para 1 elemento
        BasicDBObject[] arrayPersonas = new BasicDBObject[f.getPersonas() != null ? f.getPersonas().length : 1];

        //Si en la creacion de la familia no se espcifica persona se crea uno a null
        //Para evitar errores en la lectura con estructura distinta
        if(f.getPersonas() == null){

            dBObjectPersona.append("nombre", "");
            dBObjectPersona.append("selfId",0);
            dBObjectPersona.append("familyId",0);
            arrayPersonas[0] = dBObjectPersona;
            dBObjectFamily.append("personas",  arrayPersonas);

        }else{
//            for(Person p : f.getPersonas()){
//                dBObjectPersona.append("nombre", p.getNombre());
//                dBObjectPersona.append("selfId",p.getselfId());
//                dBObjectPersona.append("familyId",p.getFamilyId());
//                dBObjectFamily.append("personas", p);
//            }

            for(int i = 0 ; i > f.getPersonas().length; i ++ ){
                dBObjectPersona.append("nombre", f.getPersonas()[i].getNombre());
                dBObjectPersona.append("selfId",f.getPersonas()[i].getselfId());
                dBObjectPersona.append("familyId",f.getPersonas()[i].getFamilyId());

                arrayPersonas[i] = dBObjectPersona ;
                dBObjectFamily.append("personas", arrayPersonas);
            }

        }
        //dBObjectPersona.append("personas")
        //dBObjectFamily.append("personas", f.getPersonas() != null ? f.getPersonas() : dBObjectPersona);


        return dBObjectFamily;
    }


    public boolean crear(Family f) throws UnknownHostException {
        boolean resul = false;
        Family fe = new Family();
        // WriteResult wr = new WriteResult();
        BasicDBObject dBObjectFamily;

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);

        long numDocumentos = collection.getCount();
        f.setFamilyId((int) (numDocumentos + 1));
        f.toString();
        dBObjectFamily = toDBObjectToJava(f);
        //JacksonDBCollection<Person, String> coll = JacksonDBCollection.wrap(collection, Person.class, String.class);
        WriteResult wr = collection.insert(dBObjectFamily);
        //org.mongojack.WriteResult<Person,String> test =  coll.insert(p);

        //fe = obtenerPorId(f.getFamilyId());
        //f.set_id(fe.get_id());


        if(numDocumentos < collection.getCount()){
            //registro insertado
            resul = true;

        }

        return resul;

    }


    public boolean modificar(int id, Family f) throws  UnknownHostException{
        boolean resul = false;
        Family fe = new Family();

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);
        JacksonDBCollection<Person, String> coll = JacksonDBCollection.wrap(collection, Person.class, String.class);

        BasicDBObject query = new BasicDBObject();
        query.put("familyId", id);
        f.setFamilyId(id);
        BasicDBObject datosNuevos = toDBObjectToJava(f);

        WriteResult wr = collection.update(query,datosNuevos);
        if(wr.isUpdateOfExisting()){
            resul = true;
            fe = obtenerPorId(f.getFamilyId());
            f.set_id(fe.get_id());
        }

        return resul;







    }




}
