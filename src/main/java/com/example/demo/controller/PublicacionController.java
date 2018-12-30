package com.example.demo.controller;

import com.example.demo.pojo.Coment;
import com.example.demo.pojo.ResponseMensaje;
import com.example.demo.service.ComentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/publicaciones/coments")
public class PublicacionController {

  /*  private static ArrayList<Coment> comentarios;
    private static ComentService servicioComent = null;

    public PublicacionController() {
        super();
        servicioComent = ComentService.getInstance();
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> listAll() {

        ResponseEntity<Object> response = new ResponseEntity<>(comentarios, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        try {
            comentarios = servicioComent.listar();

            response = new ResponseEntity<>(comentarios, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> detail(@PathVariable int id) {
        Coment c = new Coment();

        ResponseEntity<Object> response = new ResponseEntity<>(comentarios, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        try {

            c = servicioComent.obtenerPorId(id);
            if (c != null) {

                response = new ResponseEntity<>(c, HttpStatus.OK);
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

        ResponseEntity<Object> response = new ResponseEntity<>(comentarios, HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();
        try {

            if (servicioComent.eliminar(id)) {

                response = new ResponseEntity<>(HttpStatus.OK);

            } else {
                rm.setMensaje("Error Eliminando Comentario");

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<Object> crear(@RequestBody Coment comentario) {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();

        try {

            if (servicioComent.crear(comentario)) {


                response = new ResponseEntity<>(comentario, HttpStatus.CREATED);
            } else {

                response = new ResponseEntity<>( HttpStatus.CONFLICT);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modificar(@RequestBody Coment comentario, @PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();

        try {

            if(servicioComent.modficar(id, comentario )){

                response = new ResponseEntity<>(comentario, HttpStatus.OK);
            }



        }catch (Exception e) {

            e.printStackTrace();
        }




        return response;

    }
*/

}



