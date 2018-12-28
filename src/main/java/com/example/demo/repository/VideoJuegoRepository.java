package com.example.demo.repository;


import java.util.Iterator;
import java.util.regex.Pattern;


import com.example.demo.pojo.Videojuego;
import com.example.demo.service.VideoJuegoService;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;


public class VideoJuegoRepository extends BasicDAO<Videojuego, ObjectId> {

//    private static VideoJuegoRepository INSTANCE = null;
//
//    public static synchronized VideoJuegoRepository getInstance() {
//        if (INSTANCE == null) {
//            INSTANCE = new VideoJuegoRepository();
//        }
//        return INSTANCE;
//    }
//




    public VideoJuegoRepository(Mongo mongo, Morphia morphia, String dbName) {
        super((MongoClient) mongo,morphia,dbName);
    }

    public Iterator<Videojuego> detalleVideojuego(int id) {
        Pattern idExp = Pattern.compile(id + ".*", Pattern.CASE_INSENSITIVE);
        return ds.find(entityClazz).filter("id", id).iterator();
    }
}