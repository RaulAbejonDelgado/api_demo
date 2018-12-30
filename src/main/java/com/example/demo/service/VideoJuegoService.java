package com.example.demo.service;

import com.example.demo.dao.VideoJuegoDao;
import com.example.demo.pojo.Videojuego;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;
import java.util.List;

public class VideoJuegoService {

    private static VideoJuegoService INSTANCE = null;
    private static VideoJuegoDao videoJuegoDao = null;

    @Autowired
    private Datastore datastore;

    public VideoJuegoService() throws UnknownHostException {
        //datastore = DataSourceConfiguration.getConnection();
        videoJuegoDao = VideoJuegoDao.getInstance();

    }

    public static synchronized VideoJuegoService getInstance() throws UnknownHostException {
        if (INSTANCE == null) {
            INSTANCE = new VideoJuegoService();
        }
        return INSTANCE;
    }


    public Key<Videojuego> create(Videojuego videojuego) {

        return videoJuegoDao.create(videojuego);

        //return datastore.save(videojuego);

    }

    public List readAll() {


        return videoJuegoDao.readAll();
    }


    public Videojuego read(int videoJuegoId) {

        return videoJuegoDao.read(videoJuegoId);

    }


    public boolean update(Videojuego v, int id) throws Exception {


        //return datastore.update(videojuego, operations);
        boolean resul = false;
        Videojuego vn = new Videojuego();
        vn = videoJuegoDao.read(id);
        UpdateOperations up = videoJuegoDao.createOperations();
        //titulo
        if (v.getTitulo() == null) {
            up.set("titulo", vn.getTitulo());
        } else {
            up.set("titulo", v.getTitulo());
        }

        //precio
        if (v.getPrecio() == 0) {
            up.set("precio", vn.getPrecio());
        } else {
            up.set("precio", v.getPrecio());
        }

        //videoJuegoId
        if (v.getVideoJuegoId() == 0) {
            up.set("videoJuegoId", vn.getVideoJuegoId());
        } else {
            up.set("videoJuegoId", v.getVideoJuegoId());
        }

        if (v.getId() == null) {
            up.set("_id", vn.getId());
        } else {
            up.set("_id", v.getId());
        }
        //UpdateResults ur = videoJuegoService.update(vn, up);
        UpdateResults ur = videoJuegoDao.update(vn, up);
        if(ur.getUpdatedCount() == 1){
            resul = true;
        }

        return resul;

    }

    public boolean delete(Videojuego videoJuego) {

        WriteResult wr = null;
        boolean resul = false ;

        wr = videoJuegoDao.delete(videoJuego);

        if (wr.getN() == 1) {
            resul = true;
        }

        return resul;
    }


    public UpdateOperations<Videojuego> createOperations() {

        return datastore.createUpdateOperations(Videojuego.class);
    }


}
