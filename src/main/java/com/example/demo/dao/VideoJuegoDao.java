package com.example.demo.dao;

import com.example.demo.DataSourceConfiguration;
import com.example.demo.pojo.Videojuego;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;
import java.util.List;

public class VideoJuegoDao {

    private static VideoJuegoDao INSTANCE = null;


    @Autowired
    private Datastore datastore;

    public VideoJuegoDao() throws UnknownHostException {
        datastore = DataSourceConfiguration.getConnection();

    }

    public static synchronized VideoJuegoDao getInstance() throws UnknownHostException {
        if (INSTANCE == null) {
            INSTANCE = new VideoJuegoDao();
        }
        return INSTANCE;
    }


    public Key<Videojuego> create(Videojuego videojuego) {

        return datastore.save(videojuego);

    }

    public List readAll() {


        return datastore.createQuery(Videojuego.class).asList();
    }


    public Videojuego read(int videoJuegoId) {

        return datastore.find(Videojuego.class).field("videoJuegoId").equal(videoJuegoId).get();

    }


    public UpdateResults update(Videojuego videojuego, UpdateOperations<Videojuego> operations) {

        return datastore.update(videojuego, operations);
    }

    public WriteResult delete(Videojuego videoJuego) {


        return datastore.delete(videoJuego);
    }


    public UpdateOperations<Videojuego> createOperations() {
        return datastore.createUpdateOperations(Videojuego.class);
    }


}
