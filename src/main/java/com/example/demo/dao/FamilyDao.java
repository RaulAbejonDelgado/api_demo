package com.example.demo.dao;

import com.example.demo.DataSourceConfiguration;
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
import java.util.List;

import static com.example.demo.dao.MongoConector.getConnectionDbAndCollection;

public class FamilyDao {

    private static FamilyDao INSTANCE = null;
    Family fo = new Family();

    @Autowired
    private Datastore datastore;

    public static synchronized FamilyDao getInstance() throws UnknownHostException {

        if (INSTANCE == null) {
            INSTANCE = new FamilyDao();
        }

        return INSTANCE;
    }

    public FamilyDao() throws UnknownHostException {

        super();
        datastore = DataSourceConfiguration.getConnection();
    }


    public List<Family> listarTodos() throws UnknownHostException {

        return datastore.createQuery(Family.class).asList();
    }


    public Family obtenerPorId(int id) throws UnknownHostException {

        return datastore.find(Family.class).field("selfId").equal(id).get();
    }

    public WriteResult eliminar(Family f) throws UnknownHostException {

        return datastore.delete(f);

    }


    public Key<Family> crear(Family f) throws UnknownHostException {

        //seteamos el self id
        f.setSelfId(listarTodos().size() + 1);

        if(f.getPersonas().length != 0 ){
            for(Person p : f.getPersonas()){
                //seteamos la familia en las persoans relacionadas
                //todo setear el objectId de los miembros
                p.setFamilyId(f.getSelfId());
            }
        }

        return datastore.save(f);

    }



    public Key<Family> modificar(int id, Family f) throws  UnknownHostException{

        Key<Family> familyUpdate = null ;
        Family fOld = obtenerPorId(id);

        if (fOld != null){

            f.setId(fOld.getId());
            familyUpdate =  datastore.merge(f);

        }

        return familyUpdate;

    }

}

