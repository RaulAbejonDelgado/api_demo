package com.example.demo.controller;

import com.example.demo.pojo.ResponseMensaje;
import com.example.demo.pojo.Videojuego;
import com.example.demo.service.VideoJuegoService;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.ArrayList;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


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

    @RequestMapping(method = RequestMethod.GET, produces={"application/x-resource+json"})
    ResponseEntity<Object> listAll() {

        ResponseEntity<Object> response = new ResponseEntity<>(videoJuegos, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        Resource<Videojuego> resource = null;
        ArrayList<Resource<Videojuego>> arrayResource = new ArrayList<Resource<Videojuego>>();
        try {

            videoJuegos = (ArrayList<Videojuego>) videoJuegoService.readAll();
            if(videoJuegos != null ){
                for (Videojuego v : videoJuegos) {
                    Link selfLink = linkTo(VideoJuegoController.class).slash(v.getVideoJuegoId()).withSelfRel();

                    resource = new Resource<Videojuego>(v, selfLink);
                    arrayResource.add(resource);
                }
                response = new ResponseEntity<>(arrayResource,HttpStatus.OK);
            }else{
                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }



        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces={"application/x-resource+json"})
    ResponseEntity<Object> detail(@PathVariable int id){

        Videojuego v = videoJuegoService.read(id);
        Resource<Videojuego> resource = null;
        ArrayList<Resource<Videojuego>> arrayResource = new ArrayList<Resource<Videojuego>>();
        ResponseEntity response = new ResponseEntity<>(v,HttpStatus.BAD_REQUEST);

        try {

            if(v != null){
                resource = new Resource<Videojuego>(v);

                //creacion de enlaces hateoas
                Link selfLink = linkTo(VideoJuegoController.class).slash(v.getVideoJuegoId()).withSelfRel();
                Link testLink = linkTo(VideoJuegoController.class).withRel("Todos los video juegos");

                //añadimos los enlaces al recurso
                resource.add(selfLink);
                resource.add(testLink);

                //añadimos el rescurso a un arrayList ya que si no, no se forma correctamente, la estructura json
                arrayResource.add(resource);

                response = new ResponseEntity<>(arrayResource,HttpStatus.OK);


            }else{

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        }catch (Exception e){

            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable int id) {
        boolean resul = false;
        ResponseEntity<Object> response = new ResponseEntity<>(videoJuegos, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        try {

            Videojuego v = videoJuegoService.read(id);

            if (v != null){
                resul = videoJuegoService.delete(v);

                if (resul = true){
                    response = new ResponseEntity<>(HttpStatus.OK);

                } else {
                    response = new ResponseEntity<>(HttpStatus.CONFLICT);
                }
            }else{
                response = new ResponseEntity<>(HttpStatus.CONFLICT);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping(method = RequestMethod.POST, produces={"application/x-resource+json"})
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

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces={"application/x-resource+json"})
    public ResponseEntity<Object> modificar(@RequestBody Videojuego v, @PathVariable int id) {

        boolean resul = false;
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();

        try {

            resul = videoJuegoService.update(v, id);
            if(resul){

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



