package com.example.demo.service;

import com.example.demo.DataSourceConfiguration;
import com.example.demo.pojo.Videojuego;
import com.example.demo.repository.VideoJuegoRepository;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class VideoJuegoService {

    private static VideoJuegoService INSTANCE = null;
    private static VideoJuegoRepository videoJuegoRepository;

    @Autowired
    private Datastore datastore;

    public VideoJuegoService() throws UnknownHostException {
        datastore = DataSourceConfiguration.getConnection();


    }

    public static synchronized VideoJuegoService getInstance() throws UnknownHostException {
        if (INSTANCE == null) {
            INSTANCE = new VideoJuegoService();
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
//        return datastore.get(Videojuego.class, videoJuegoId);
//        Query q = datastore.createQuery(Videojuego.class).filter("videoJuegoId", videoJuegoId);

        return datastore.find(Videojuego.class).field("videoJuegoId").equal(videoJuegoId).get();

        //datastore.createQuery(Videojuego.class,read(videoJuegoId));
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
