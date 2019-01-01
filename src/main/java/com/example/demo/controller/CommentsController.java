package com.example.demo.controller;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.ResponseMensaje;
import com.example.demo.service.CommentService;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Set;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/publicaciones/coments")
public class CommentsController {

    private static CommentService servicioComent = null;
    private static Validator validator ;
    private static final int IMPORT_DATA = 1;
    private static final int EXPORT_DATA = 2;
    private static final String COLLECTION_NAME = "comentarios";

    public CommentsController() throws UnknownHostException {

        super();

        servicioComent = CommentService.getInstance();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

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
        ResponseMensaje rm = new ResponseMensaje();

        ArrayList<Resource<Comment>> resoucesPerson;

        try {

            Set<ConstraintViolation<Comment>> violations = validator.validate(comentario);
            String[] errores = new String[violations.size()];

            if (violations.size() > 0) {

                int contador = 0;

                for (ConstraintViolation<Comment> violation : violations) {

                    errores[contador] = violation.getPropertyPath() + ":" + violation.getMessage();
                    contador++;
                }

                rm.setErrores(errores);
                rm.setMensaje("error de validación");
                response = new ResponseEntity<>(rm, HttpStatus.CONFLICT);

            }else{

                resoucesPerson = servicioComent.crear(comentario);
                if (resoucesPerson.size() > 0) {

                    response = new ResponseEntity<>(resoucesPerson, HttpStatus.CREATED);

                } else {

                    response = new ResponseEntity<>(HttpStatus.CONFLICT);
                }
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
        ResponseMensaje rm = new ResponseMensaje();

        try {

            Set<ConstraintViolation<Comment>> violations = validator.validate(comentario);
            String[] errores = new String[violations.size()];

            if (violations.size() > 0) {

                int contador = 0;

                for (ConstraintViolation<Comment> violation : violations) {

                    errores[contador] = violation.getPropertyPath() + ":" + violation.getMessage();
                    contador++;
                }

                rm.setErrores(errores);
                rm.setMensaje("error de validación");
                response = new ResponseEntity<>(rm, HttpStatus.CONFLICT);

            }else{

                resoucesPerson = servicioComent.modficar(id, comentario);

                if (resoucesPerson.size() > 0) {

                    response = new ResponseEntity<>(resoucesPerson, HttpStatus.OK);

                } else {

                    response = new ResponseEntity<>(resoucesPerson, HttpStatus.CONFLICT);

                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return response;

    }

    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity<Object> importExport(
            @RequestParam(name = "action", required = false, defaultValue = "-1") int action) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        try {

            switch (action){
                case IMPORT_DATA:
                    servicioComent.importar(COLLECTION_NAME);
                    break;

                case EXPORT_DATA:

                    servicioComent.exportar(COLLECTION_NAME);
                    break;

            }

            response = new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("[{Mensaje:"+e.getMessage()+"}]", HttpStatus.CONFLICT);
        }

        return response;

    }

}
