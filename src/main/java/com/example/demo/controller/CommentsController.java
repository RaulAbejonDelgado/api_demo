package com.example.demo.controller;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.ResponseMensaje;
import com.example.demo.service.CommentService;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.ArrayList;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/publicaciones/coments")
public class CommentsController {

    private static CommentService servicioComent = null;

    public CommentsController() throws UnknownHostException {

        super();
        servicioComent = CommentService.getInstance();

    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> listAll() {

        ArrayList<Resource<Comment>> resourcesComentariosArray;
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        try {

            resourcesComentariosArray = servicioComent.listar();

            if (resourcesComentariosArray.size() > 0) {

                response = new ResponseEntity<>(resourcesComentariosArray, HttpStatus.OK);

            } else {

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return response;

    }


    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> detail(@PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Comment>> resoucesComent;

        try {

            resoucesComent = servicioComent.obtenerPorId(id);

            if (resoucesComent.size() > 0) {

                response = new ResponseEntity<>(resoucesComent, HttpStatus.OK);

            } else {

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {

            e.printStackTrace();
        }

        return response;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> crear(@RequestBody Comment comentario) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        ArrayList<Resource<Comment>> resoucesPerson;

        try {

            resoucesPerson = servicioComent.crear(comentario);
            if (resoucesPerson.size() > 0) {

                response = new ResponseEntity<>(resoucesPerson, HttpStatus.CREATED);

            } else {

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modificar(@RequestBody Comment comentario, @PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Comment>> resoucesPerson;

        try {

            resoucesPerson = servicioComent.modficar(id, comentario);

            if (resoucesPerson.size() > 0) {

                response = new ResponseEntity<>(resoucesPerson, HttpStatus.OK);

            } else {

                response = new ResponseEntity<>(resoucesPerson, HttpStatus.CONFLICT);

            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return response;

    }

}
