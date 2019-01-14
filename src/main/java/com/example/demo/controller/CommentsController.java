package com.example.demo.controller;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.ResponseMensaje;
import com.example.demo.service.CommentService;
import com.example.demo.service.PersonService;
import org.apache.log4j.Logger;
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
@RequestMapping("/API/publicaciones/coments")
public class CommentsController {

    private static CommentService servicioComent = null;
    private static Validator validator;
    private final static Logger LOG = Logger.getLogger(PersonController.class);


    /**
     * IMPORT_DATA - EXPORT_DATA -COLLECTION_NAME
     * Usados para la practica de exportacion importacion de datos en mongoDB
     * endpoint /API/publicaciones/person/data
     * esta practica es anterior a la practica spring batch
     * IMPORT_DATA -> Usadas en el switch case del metodo importExport representa la accion de importar
     * EXPORT_DATA -> Usadas en el switch case del metodo importExport representa la accion de exportacion
     * COLLECTION_NAME -> representa la coleccion de mongo contra la que cargara las acciones http
     *
     *
     */
    private static final int IMPORT_DATA = 1;
    private static final int EXPORT_DATA = 2;
    private static final String COLLECTION_NAME = "comentarios";

    public CommentsController() throws UnknownHostException {

        super();
        LOG.info("CommentsController -- Constructor");
        servicioComent = CommentService.getInstance();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

    }

    /**
     *Metodo listAll : Peticion GET en la uri /API/publicaciones/family
     * @return listado de todos los objetos personas en el siquiente formato
     *objeto esperado:<br>
     *   [{<br>
     *   "familia": [{<br>
     *   "selfId": 2,<br>
     *   "nombre": "familia AsierRaul"<br>
     *
     *  }],<br>
     *   "texto": "Creacion de comentario de prueba",<br>
     *   "persona": [{<br>
     *   "selfId": 1,<br>
     *   "familyId": 2,<br>
     *   "nombre": "Raul"<br>
     *   }]<br>
     *     produciendo {"application/x-resource+json"}
     */
    @RequestMapping(method = RequestMethod.GET ,produces={"application/x-resource+json"})
    public ResponseEntity<Object> listAll() {

        ArrayList<Resource<Comment>> resourcesComentariosArray;
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        try {

            resourcesComentariosArray = servicioComent.listar();

            if (resourcesComentariosArray.size() > 0) {

                response = new ResponseEntity<>(resourcesComentariosArray, HttpStatus.OK);
                LOG.info(response);

            } else {

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
                LOG.warn(response);
            }

        } catch (Exception e) {

            e.printStackTrace();
            LOG.error(e.getMessage());
        }

        return response;

    }
    /**
     *
     * @param id representa el campo selfId del objeto
     * @return detalle del comentario  en el siquiente formato
     *objeto esperado:<br>
     *   [{<br>
     *   "familia": [{<br>
     *   "selfId": 2,<br>
     *   "nombre": "familia AsierRaul"<br>
     *
     *  }],<br>
     *   "texto": "Creacion de comentario de prueba",<br>
     *   "persona": [{<br>
     *   "selfId": 1,<br>
     *   "familyId": 2,<br>
     *   "nombre": "Raul"<br>
     *   }]<br>
     *     produciendo {"application/x-resource+json"}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces={"application/x-resource+json"})
    public ResponseEntity<Object> detail(@PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Comment>> resoucesComent;

        try {

            resoucesComent = servicioComent.obtenerPorId(id);

            if (resoucesComent.size() > 0) {

                response = new ResponseEntity<>(resoucesComent, HttpStatus.OK);
                LOG.info(response);

            } else {

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
                LOG.warn(response);
            }


        } catch (Exception e) {

            e.printStackTrace();
            LOG.error(e.getMessage());
        }

        return response;

    }

    /**
     * endpoint /API/publicaciones/comments metodo DELETE
     * delete: Elimina el registro por un id
     * @param id parametro de entrada debe ser un entero representa al campo selfId del objeto
     * @return devuelve respuesta de estado http 200 si se ha eliminado correctamente
     * o NO_CONTENT si no ha habido coincidencia
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();

        try {

            if (servicioComent.eliminar(id)) {

                response = new ResponseEntity<>(HttpStatus.OK);
                LOG.info(response);

            } else {

                rm.setMensaje("Error Eliminando Comentario");
                response = new ResponseEntity<>(HttpStatus.CONFLICT);
                LOG.warn(response);
            }


        } catch (Exception e) {

            e.printStackTrace();
            LOG.error(e.getMessage());

        }

        return response;
    }

    /**
     * endpoint /API/publicaciones/comments metodo POST
     * *@param familia Objeto entrante transformado en la operacion POST
     * @return resoucesPerson objeto tipo Resource con el objeto generado del tipo comment con enlaces hateos<br>
     * Hay control de campos con javax.validation en caso de no cumplir las validaciones devolvemos en la respuesta<br>
     *     NotNull(message = "Name cannot be null")<br>
     *     private Family[] familia;<br>
     *
     *     NotNull(message = "tittle cannot be null")<br>
     *     Size(min = 5, max = 100, message= "tittle must be between 5 and 100 characters")<br>>
     *     private String titulo;<br>
     *
     *     NotNull(message = "Name cannot be null")<br>
     *     Size(min = 5, max = 150, message= "Name must be between 5 and 150 characters")<br>
     *     private String texto;<br>
     *
     *     NotNull(message = "Name cannot be null")<br>
     *     private Person[] persona;<br>
     *
     */
    @RequestMapping(method = RequestMethod.POST, produces={"application/x-resource+json"})
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
                LOG.warn(response);
            } else {

                resoucesPerson = servicioComent.crear(comentario);
                if (resoucesPerson.size() > 0) {

                    response = new ResponseEntity<>(resoucesPerson, HttpStatus.CREATED);
                    LOG.info(response);
                } else {

                    response = new ResponseEntity<>(HttpStatus.CONFLICT);
                    LOG.warn(response);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
            LOG.error(e.getMessage());

        }

        return response;
    }

    /**
     *
     * endpoint /API/publicaciones/comments metodo POST
     * *@param comentario Objeto entrante transformado en la operacion PUT
     * @return resoucesPerson objeto tipo Resource con el objeto generado del tipo comment con enlaces hateos<br>
     * @param id representa a la propiedad selfId del objeto
     * Objeto:Esperado:<br>
     *
     * [{<br>
     *     "familia": [{<br>

     *         "selfId": 1,<br>
     *         "nombre": "familia AsierRaul"<br>
     *
     *     }],<br>
     *     "texto": "asier",<br>
     *     "persona": [{<br>

     *         "selfId": 1,<br>
     *         "familyId": 1,<br>
     *         "nombre": "Raul"<br>
     *
     *     }]
     *  }
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces={"application/x-resource+json"})
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
                LOG.warn(response);
            } else {

                resoucesPerson = servicioComent.modficar(id, comentario);

                if (resoucesPerson.size() > 0) {

                    response = new ResponseEntity<>(resoucesPerson, HttpStatus.OK);
                    LOG.info(response);
                } else {

                    response = new ResponseEntity<>(resoucesPerson, HttpStatus.CONFLICT);
                    LOG.warn(response);

                }
            }

        } catch (Exception e) {

            e.printStackTrace();
            LOG.error(e.getMessage());
        }

        return response;

    }

    /**
     * Para la practica de parseo de documentos xml a mongo y viceversa.
     *
     * COLLECTION_NAME : nombre de la coleccion de mongoDB
     *
     * endpoint /API/publicaciones/person/data -> Metodo GET
     * @param action:
     *              1: representa la accion de importar
     *              2: representa la accion de exportar
     * @return si el proceso se realiza correctamente respuesta de estado 200
     * en caso contrario se devuelve un mensaje con el error que se ha generado
     */
    @RequestMapping(value = "/data", method = RequestMethod.GET)
    public ResponseEntity<Object> importExport(
            @RequestParam(name = "action", required = false, defaultValue = "-1") int action) {

        ResponseEntity<Object> response;

        try {

            switch (action) {
                case IMPORT_DATA:
                    servicioComent.importar(COLLECTION_NAME);
                    break;

                case EXPORT_DATA:

                    servicioComent.exportar(COLLECTION_NAME);
                    break;

            }

            response = new ResponseEntity<>(HttpStatus.OK);
            LOG.info(response);

        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("[{Mensaje:" + e.getMessage() + "}]", HttpStatus.CONFLICT);
            LOG.error(response);
        }

        return response;

    }

    /**
     * Uri añadida para obtener los comentarios por usuario
     * @param id hace referencia a la propiedad selfId del objeto persona que esta embebido
     * @return :
     *          Si hay coincidencia: devuelve el resoucesComent con los comentarios por usuario y respuesta http 200
     *          Si no, respuesta http 404 ya que se intenta obtener un usuario inexistente
     */
    @RequestMapping(value = "/byUser/{id}", method = RequestMethod.GET, produces={"application/x-resource+json"})
    public ResponseEntity<Object> byUser(@PathVariable int id ) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Comment>> resoucesComent;

        try {

            resoucesComent = servicioComent.byUserId(id);

            if (resoucesComent.size() > 0) {

                response = new ResponseEntity<>(resoucesComent, HttpStatus.OK);
                LOG.info(response);

            } else {

                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                LOG.warn(response);
            }


        } catch (Exception e) {

            e.printStackTrace();
            LOG.error(e.getMessage());
        }

        return response;

    }

    /**
     * Uri añadida para obtener los comentarios por familia
     * @param id hace referencia a la propiedad selfId del objeto id que esta embebido
     * @return :
     *          Si hay coincidencia: devuelve el resoucesComent con los comentarios por usuario y respuesta http 200
     *          Si no, respuesta http 404 ya que se intenta obtener un usuario inexistente
     */
    @RequestMapping(value = "/byFamily/{id}", method = RequestMethod.GET, produces={"application/x-resource+json"})
    public ResponseEntity<Object> byFamily(@PathVariable int id ) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Comment>> resoucesComent;

        try {

            resoucesComent = servicioComent.byFamilyId(id);

            if (resoucesComent.size() > 0) {

                response = new ResponseEntity<>(resoucesComent, HttpStatus.OK);
                LOG.info(response);

            } else {

                response = new ResponseEntity<>(resoucesComent,HttpStatus.OK);
                LOG.warn(response);
            }


        } catch (Exception e) {

            e.printStackTrace();
            LOG.error(e.getMessage());
        }

        return response;

    }

}
