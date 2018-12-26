package com.example.demo.dao;

import com.example.demo.pojo.Family;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;
import org.mongojack.DBCursor;
import org.mongojack.JacksonDBCollection;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static com.example.demo.dao.MongoConector.getConnectionDbAndCollection;

public class FamilyDao {

    private static FamilyDao INSTANCE = null;

    private static final String DB = "publicaciones";
    private static final String COLLECTION = "familias";
    Family fo = new Family();

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


        int cantidadMiembros = fo.getPersonas() != null ? fo.getPersonas().length : 0 ; // cuento los miembros existentes
        int cantidadNuevos = f.getPersonas() != null ? f.getPersonas().length : 0;

        //creo array del tamaño de las personas y si viene null creamos hueco para 1 elemento
        BasicDBObject[] arrayPersonas = new BasicDBObject[f.getPersonas() != null ? cantidadNuevos  + cantidadMiembros: cantidadMiembros];

        //Si en la creacion de la familia no se espcifica persona se crea uno a null
        //Para evitar errores en la lectura con estructura distinta
        if(f.getPersonas() == null && fo.getPersonas() == null){

            dBObjectPersona.append("nombre", "");
            dBObjectPersona.append("selfId",0);
            dBObjectPersona.append("familyId",0);
            arrayPersonas[0] = dBObjectPersona;
            dBObjectFamily.append("personas",  arrayPersonas);

        }else{
            if(fo.getPersonas() != null ){
                for(int i = 0 ; i < fo.getPersonas().length; i ++ ){
                    BasicDBObject dBObjectExistente = new BasicDBObject();
                    dBObjectExistente.append("nombre", fo.getPersonas()[i].getNombre());
                    dBObjectExistente.append("selfId",fo.getPersonas()[i].getselfId());
                    dBObjectExistente.append("familyId",fo.getFamilyId());

                    arrayPersonas[i] = dBObjectExistente ;

                }
            }
            if(f.getPersonas() != null){
                for(int i = 0 ; i < f.getPersonas().length; i ++ ){
                    BasicDBObject dBObjectNueva = new BasicDBObject();
                    dBObjectNueva.append("nombre", f.getPersonas()[i].getNombre());
                    dBObjectNueva.append("selfId",f.getPersonas()[i].getselfId());
                    dBObjectNueva.append("familyId",f.getFamilyId());
                    arrayPersonas[fo.getPersonas().length + i] = dBObjectNueva ;

                }
            }

            dBObjectFamily.append("personas", arrayPersonas);

        }

        return dBObjectFamily;
    }


    private BasicDBObject toDBObjectToJavaCreate(Family f) {

        // Creamos una instancia BasicDBObject
        BasicDBObject dBObjectFamily = new BasicDBObject();

        dBObjectFamily.append("familyId", f.getFamilyId());

        dBObjectFamily.append("nombre", f.getNombre());

        BasicDBObject dBObjectPersona = new BasicDBObject();

        //creo array del tamaño de las personas y si viene null creamos hueco para 1 elemento
        BasicDBObject[] arrayPersonas = new BasicDBObject[f.getPersonas() != null ? f.getPersonas().length  : 1];

        //Si en la creacion de la familia no se espcifica persona se crea uno a null
        //Para evitar errores en la lectura con estructura distinta
        if(f.getPersonas() == null ){

            dBObjectPersona.append("nombre", "");
            dBObjectPersona.append("selfId",0);
            dBObjectPersona.append("familyId",0);
            arrayPersonas[0] = dBObjectPersona;
            dBObjectFamily.append("personas",  arrayPersonas);

        }else{

            for(int i = 0 ; i < f.getPersonas().length; i ++ ){
                dBObjectPersona.append("nombre", f.getPersonas()[i].getNombre());
                dBObjectPersona.append("selfId",f.getPersonas()[i].getselfId());
                dBObjectPersona.append("familyId",f.getFamilyId());

                arrayPersonas[i] = dBObjectPersona ;

            }

            dBObjectFamily.append("personas", arrayPersonas);

        }

        return dBObjectFamily;
    }


    public boolean crear(Family f) throws UnknownHostException {
        boolean resul = false;
        Family fe = new Family();

        BasicDBObject dBObjectFamily;

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);

        long numDocumentos = collection.getCount();
        f.setFamilyId((int) (numDocumentos + 1));
        f.toString();
        dBObjectFamily = toDBObjectToJavaCreate(f);

        WriteResult wr = collection.insert(dBObjectFamily);


        if(numDocumentos < collection.getCount()){

            resul = true;

        }

        return resul;

    }


    public boolean modificar(int id, Family f) throws  UnknownHostException{

        boolean resul = false;
        Family fe = new Family();

        DBCollection collection = getConnectionDbAndCollection(DB, COLLECTION);

        BasicDBObject query = new BasicDBObject();
        query.put("familyId", id);
        fo = obtenerPorId(id);
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

