package com.example.demo.controller;

import com.example.demo.pojo.Coment;
import com.example.demo.pojo.ResponseMensaje;
import com.example.demo.pojo.Videojuego;
import com.example.demo.service.ComentService;
import com.example.demo.service.VideoJuegoService;
import com.mongodb.WriteResult;
import com.mongodb.client.result.UpdateResult;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.ArrayList;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/publicaciones/videojuegos")
public class VideoJuegoController {

    private static ArrayList<Videojuego> videoJuegos = null;
    private static VideoJuegoService videoJuegoService = null;

    public VideoJuegoController() throws UnknownHostException {
        super();
        videoJuegoService = VideoJuegoService.getInstance();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> listAll() {

        ResponseEntity<Object> response = new ResponseEntity<>(videoJuegos, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        try {

            videoJuegos = (ArrayList<Videojuego>) videoJuegoService.readAll();

            response = new ResponseEntity<>(videoJuegos, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> detail(@PathVariable int id) {
        Videojuego v = new Videojuego();

        ResponseEntity<Object> response = new ResponseEntity<>(videoJuegos, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        try {

            v = videoJuegoService.read(id);
            if (v != null) {

                response = new ResponseEntity<>(v, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(id);

        return response;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(videoJuegos, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        try {
            Videojuego v = new Videojuego();
            v = videoJuegoService.read(id);
            WriteResult wr = videoJuegoService.delete(v);
            if (wr.getN() == 1) {
                response = new ResponseEntity<>(HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> crear(@RequestBody Videojuego v) {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();

        try {

            if (videoJuegoService.create(v).getId() != null) {
                response = new ResponseEntity<>(v, HttpStatus.CREATED);

            } else {
                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modificar(@RequestBody Videojuego v, @PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();

        try {
            Videojuego vn = new Videojuego();
            vn = videoJuegoService.read(id);
            UpdateOperations up = videoJuegoService.createOperations();
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
            UpdateResults ur = videoJuegoService.update(vn, up);
            if(ur.getUpdatedCount() == 1){
                response = new ResponseEntity<>(v, HttpStatus.OK);
            }else{
                response = new ResponseEntity<>("Error", HttpStatus.CONFLICT);
            }




        } catch (Exception e) {

            e.printStackTrace();
        }


        return response;

    }


}



