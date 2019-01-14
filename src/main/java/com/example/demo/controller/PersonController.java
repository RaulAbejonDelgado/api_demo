package com.example.demo.controller;

import com.example.demo.pojo.Person;
import com.example.demo.pojo.ResponseMensaje;
import com.example.demo.service.PersonService;
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

/**
 * Controlador que gestiona las peticiones http
 */
@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/API/publicaciones/person")
public class PersonController {

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
    private static final String COLLECTION_NAME = "personas";

    private static ArrayList<Person> persons = null;
    private static PersonService servicioPerson = null;

    /**
     * Validator -> @see https://docs.oracle.com/javaee/7/api/javax/validation/package-summary.html
     */
    private static Validator validator;

    public PersonController() {
        super();
        try {

            servicioPerson = PersonService.getInstance();
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();

        } catch (UnknownHostException e) {

            e.printStackTrace();
        }

    }

    /**
     *Metodo listAll : Peticion GET en la uri /API/publicaciones/person
     * @return listado de todos los objetos personas en el siquiente formato
     * [{<br>
     *         "_id": "5c2c6aeedf132c28285f4e73",<br>
     *         "selfId": 8,<br>
     *         "familyId": 1,<br>
     *         "nombre": "Enhord",<br>
     *         "correo": "enhord@gmail.com",<br>
     *         "password": "****************",<br>
     *         "id": {<br>
     *             "timestamp": 1546414830,<br>
     *             "machineIdentifier": 14619436,<br>
     *             "processIdentifier": 10280,<br>
     *             "counter": 6246003,<br>
     *             "time": 1546414830000,<br>
     *             "date": "2019-01-02T07:40:30.000+0000",<br>
     *             "timeSecond": 1546414830<br>
     *         },<br>
     *         "links": [<br>
     *             {<br>
     *                 "rel": "self",<br>
     *                 "href": "http://localhost:8080/API/publicaciones/person/8",<br>
     *                 "hreflang": null,<br>
     *                 "media": null,<br>
     *                 "title": null,<br>
     *                 "type": null,<br>
     *                 "deprecation": null<br>
     *             },<br>
     *             {<br>
     *                 "rel": "Familia",<br>
     *                 "href": "http://localhost:8080/API/publicaciones/family/1",<br>
     *                 "hreflang": null,<br>
     *                 "media": null,<br>
     *                 "title": null,<br>
     *                 "type": null,<br>
     *                 "deprecation": null<br>
     *             }<br>
     *         ]<br>
     *     }]<br>
     *
     *     produciendo {"application/x-resource+json"}
     */
    @RequestMapping(method = RequestMethod.GET, produces = {"application/x-resource+json"})
    public ResponseEntity<Object> listAll() {

        ResponseEntity<Object> response = new ResponseEntity<>(persons, HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Person>> personResources;
        try {
            personResources = servicioPerson.listar();

            System.out.println("*************Pasamos por PersonController-get*************");
            response = new ResponseEntity<>(personResources, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }

    /**
     * detail : Nos devulve el detalle de una persona
     * @param id : Representa al campo selfId del objeto que entra como parametro, debe ser un numero entero
     * @return
     * [{<br>
     *      *         "_id": "5c2c6aeedf132c28285f4e73",<br>
     *      *         "selfId": 8,<br>
     *      *         "familyId": 1,<br>
     *      *         "nombre": "Enhord",<br>
     *      *         "correo": "enhord@gmail.com",<br>
     *      *         "password": "****************",<br>
     *      *         "id": {<br>
     *      *             "timestamp": 1546414830,<br>
     *      *             "machineIdentifier": 14619436,<br>
     *      *             "processIdentifier": 10280,<br>
     *      *             "counter": 6246003,<br>
     *      *             "time": 1546414830000,<br>
     *      *             "date": "2019-01-02T07:40:30.000+0000",<br>
     *      *             "timeSecond": 1546414830<br>
     *      *         },<br>
     *      *         "links": [<br>
     *      *             {<br>
     *      *                 "rel": "self",<br>
     *      *                 "href": "http://localhost:8080/API/publicaciones/person/8",<br>
     *      *                 "hreflang": null,<br>
     *      *                 "media": null,<br>
     *      *                 "title": null,<br>
     *      *                 "type": null,<br>
     *      *                 "deprecation": null<br>
     *      *             },<br>
     *      *             {<br>
     *      *                 "rel": "Familia",<br>
     *      *                 "href": "http://localhost:8080/API/publicaciones/family/1",<br>
     *      *                 "hreflang": null,<br>
     *      *                 "media": null,<br>
     *      *                 "title": null,<br>
     *      *                 "type": null,<br>
     *      *                 "deprecation": null<br>
     *      *             }<br>
     *      *         ]<br>
     *      *     }]<br>
     * Si  la busqueda es correcta devuelve estado http 200<br>
     * Si no encuentra coincidencia devuelve estado http 409<br>
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = {"application/x-resource+json"})
    public ResponseEntity<Object> detail(@PathVariable int id) {

        ArrayList<Resource<Person>> resoucesPerson;
        ResponseEntity<Object> response = new ResponseEntity<>(persons, HttpStatus.INTERNAL_SERVER_ERROR);

        try {

            resoucesPerson = servicioPerson.obtenerPorId(id);
            if (resoucesPerson.size() > 0) {

                response = new ResponseEntity<>(resoucesPerson, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            //console.log
        }


        System.out.println(id);


        return response;

    }

    /**
     * endpoint /API/publicaciones/person metodo DELETE
     * delete: Elimina el registro por un id
     * @param id parametro de entrada debe ser un entero representa al campo selfId del objeto
     * @return devuelve respuesta de estado http 200 si se ha eliminado correctamente
     * o NO_CONTENT si no ha habido coincidencia
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(persons, HttpStatus.INTERNAL_SERVER_ERROR);

        try {

            if (servicioPerson.eliminar(id)) {

                response = new ResponseEntity<>(HttpStatus.OK);

            } else {

                response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage().contains("Not found, imposible delete it")) {
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

        }

        return response;
    }

    /**
     *endpoint /API/publicaciones/person : Metodo POST
     * @param persona Objeto entrante transformado en la operacion POST
     * @return resoucesPerson objeto tipo Resource con el objeto generado del tipo person con enlaces hateos
     * Hay control de campos con javax.validation en caso de no cumplir las validaciones devolvemos en la respuesta
     * un listado de los posibles motivos por los que no se acepta la peticion<br>
     * Requisitos validaciones:<br>
 *          Min(value = 1, message = "The id must be valid")<br>
     *    private int selfId;<br>
     *     Min(value = 1, message = "The id must be valid")<br>
     *     private int familyId;<br>
     *     NotNull(message = "Name cannot be null")<br>
     *     Size(min = 5, max = 150, message= "Name must be between 5 and 150 characters")<br>
     *     private String nombre;<br>
     *     NotNull(message = "Name cannot be null")<br>
     *     private String correo;<br>
     *     NotNull(message = "password cannot be null")<br>
     *     Size(min = 5, max = 150, message= "Password must be between 5 and 150 characters")<br>
     *     private String password;<br>
     *
     *     respuestas:<br>
     *     409 -> En caso de fallos en las validaciones.<br>
     *     201 -> Objeto creado<br>
     *
     */
    @RequestMapping(method = RequestMethod.POST, produces = {"application/x-resource+json"})
    public ResponseEntity<Object> crear(@RequestBody Person persona) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Person>> resoucesPerson;
        ResponseMensaje rm = new ResponseMensaje();
        persona.setSelfId(1);
        try {

            Set<ConstraintViolation<Person>> violations = validator.validate(persona);
            String[] errores = new String[violations.size()];

            if (violations.size() > 0) {

                int contador = 0;

                for (ConstraintViolation<Person> violation : violations) {
                    //En la creacion de una persona nueva debemos omitir esta validacion ya que aun no tiene selfId

                    errores[contador] = violation.getPropertyPath() + ":" + violation.getMessage();
                    contador++;
                }

                rm.setErrores(errores);
                rm.setMensaje("error de validación");
                response = new ResponseEntity<>(rm, HttpStatus.CONFLICT);

            } else {

                resoucesPerson = servicioPerson.crear(persona);

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


    /**
     * nombre metodo : login
     * endpoint /API/publicaciones/person/login -> Metodo POST
     * Usado para las opreciones de logueo desde el cliente
     * @param persona representa al usuario que quiere hacer login, este objeto solo contendra
     *                correo y contraseña, se omiten las validaciones para evitar problemas
     * @return resoucesPerson donde en caso de hacer coincidencia devuelve el objeto persona de lo contrario devolvera
     * el objeto incompleto y se detecta en el ciente el objeto mal formado
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {"application/x-resource+json"})
    public ResponseEntity<Object> login(@RequestBody Person persona) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        Resource resoucesPerson;

        try {
            resoucesPerson = servicioPerson.loginTest(persona);
            if (resoucesPerson != null) {

                response = new ResponseEntity<>(resoucesPerson,HttpStatus.OK);

            } else {

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     *endpoint /API/publicaciones/person : Metodo PUT
     * @param persona Objeto entrante transformado en la operacion PUT
     * @param id representa el campo selfId del objeto persona
     * @return resoucesPerson objeto tipo Resource con el objeto generado del tipo person con enlaces hateos
     * Hay control de campos con javax.validation en caso de no cumplir las validaciones devolvemos en la respuesta
     * un listado de los posibles motivos por los que no se acepta la peticion
     * Requisitos validaciones:
     *    Min(value = 1, message = "The id must be valid")
     *    private int selfId;
     *     Min(value = 1, message = "The id must be valid")
     *     private int familyId;
     *     NotNull(message = "Name cannot be null")
     *     Size(min = 5, max = 150, message= "Name must be between 5 and 150 characters")
     *     private String nombre;
     *     NotNull(message = "Name cannot be null")
     *     private String correo;
     *     NotNull(message = "password cannot be null")
     *     Size(min = 5, max = 150, message= "Password must be between 5 and 150 characters")
     *     private String password;
     *
     *     respuestas:
     *     409 -> En caso de fallos en las validaciones.
     *     201 -> Objeto creado
     *     404 -> si no hay coincidencia (no hay objeto con el selfid sugerido)
     *
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = {"application/x-resource+json"})
    public ResponseEntity<Object> modificar(@RequestBody Person persona, @PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Person>> resoucesPerson;
        ResponseMensaje rm = new ResponseMensaje();
        //Si queremos permitir que en la actualizacion la creacion de usuario sin el campo selfId
        //se debe setear para que no de problemas las validaciones.
        persona.setSelfId(id);

        try {
            Set<ConstraintViolation<Person>> violations = validator.validate(persona);
            String[] errores = new String[violations.size()];

            if (violations.size() > 0) {

                int contador = 0;

                for (ConstraintViolation<Person> violation : violations) {

                    errores[contador] = violation.getPropertyPath() + ":" + violation.getMessage();
                    contador++;
                }

                rm.setErrores(errores);
                rm.setMensaje("error de validación");
                response = new ResponseEntity<>(rm, HttpStatus.CONFLICT);

            } else {
                resoucesPerson = servicioPerson.modficar(id, persona);
                if (resoucesPerson.size() == 1) {

                    response = new ResponseEntity<>(resoucesPerson, HttpStatus.OK);

                } else {

                    response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
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

        ResponseEntity<Object> response = new ResponseEntity<>(persons, HttpStatus.INTERNAL_SERVER_ERROR);

        try {

            switch (action) {

                case IMPORT_DATA:

                    servicioPerson.importar(COLLECTION_NAME);
                    break;

                case EXPORT_DATA:

                    servicioPerson.exportar(COLLECTION_NAME);
                    break;

            }

            response = new ResponseEntity<>(HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("[{Mensaje:" + e.getMessage() + "}]", HttpStatus.CONFLICT);
        }

        return response;

    }

    /**
     * endpoint /API/publicaciones/person/nombre/ : Metodo GET
     * Metodo pendiente de ser borrado, creado en su momento para alguna exigencia del cliente
     * @param nombre nombre de la persona sobre la que se quiere obtener el detalle
     * @return si ok:
     *              resoucesPerson -> objeto persona coincidente con el nombre con enlaces hateoas
     *         Si no :
     *              posibles conflictos
     */
    @RequestMapping(value = "/nombre/{nombre}", method = RequestMethod.GET, produces = {"application/x-resource+json"})
    public ResponseEntity<Object> detailByName(@PathVariable String nombre) {

        ArrayList<Resource<Person>> resoucesPerson;
        ResponseEntity<Object> response = new ResponseEntity<>(persons, HttpStatus.INTERNAL_SERVER_ERROR);

        try {

            resoucesPerson = servicioPerson.obtenerPorNombre(nombre);
            if (resoucesPerson.size() > 0) {

                response = new ResponseEntity<>(resoucesPerson, HttpStatus.OK);
            } else {
                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }




}



