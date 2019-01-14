package com.example.demo.controller;


import com.example.demo.pojo.Family;
import com.example.demo.pojo.ResponseMensaje;
import com.example.demo.service.FamilyService;
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
@RequestMapping("/API/publicaciones/family")
public class FamilyController {

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
    private static final String COLLECTION_NAME = "familias";

    private static FamilyService familyService = null;

    private static Validator validator;


    public FamilyController() {
        super();
        try {

            familyService = FamilyService.getInstance();
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();

        } catch (UnknownHostException ue) {

            ue.printStackTrace();
        }


    }

    /**
     *Metodo listAll : Peticion GET en la uri /API/publicaciones/family
     * @return listado de todos los objetos personas en el siquiente formato
     *objeto esperado:<br>
     *{<br>
     *"nombre":"familia drohne5",<br>
     *"personas":[<br>
     *{<br>
     *"nombre":"drohne",<br>
     *"selfId":9<br>
     *}<br>
     *]<br>
     *}<br
     *     produciendo {"application/x-resource+json"}
     */

    @RequestMapping(method = RequestMethod.GET, produces={"application/x-resource+json"})
    public ResponseEntity<Object> listAll() {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        ArrayList<Resource<Family>> resourcesFamilyArray;
        try {
            resourcesFamilyArray = familyService.listar();


            response = new ResponseEntity<>(resourcesFamilyArray, HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }

    /**
     *
     * @param id representa el campo selfId del objeto
     * @return listado del detalle de la personas en el siquiente formato
     *objeto esperado:<br>
     *{<br>
     *"nombre":"familia drohne5",<br>
     *"personas":[<br>
     *{<br>
     *"nombre":"drohne",<br>
     *"selfId":9<br>
     *}<br>
     *]<br>
     *}<br
     *     produciendo {"application/x-resource+json"}
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces={"application/x-resource+json"})
    public ResponseEntity<Object> detail(@PathVariable int id) {

        Family f = new Family();
        ArrayList<Resource<Family>> resoucesPerson;

        ResponseEntity<Object> response = new ResponseEntity<>(f, HttpStatus.INTERNAL_SERVER_ERROR);

        try {

            resoucesPerson = familyService.obtenerPorId(id);
            if (resoucesPerson.size() > 0) {

                response = new ResponseEntity<>(resoucesPerson, HttpStatus.OK);

            } else {

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println(id);


        return response;

    }

    /**
     * endpoint /API/publicaciones/family metodo DELETE
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

            if (familyService.eliminar(id)) {

                response = new ResponseEntity<>(HttpStatus.OK);

            } else {
                rm.setMensaje("Error Eliminando familia");

                response = new ResponseEntity<>(rm, HttpStatus.CONFLICT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     *endpoint /API/publicaciones/family : Metodo POST
     * @param familia Objeto entrante transformado en la operacion POST
     * @return resoucesPerson objeto tipo Resource con el objeto generado del tipo person con enlaces hateos<br>
     * Hay control de campos con javax.validation en caso de no cumplir las validaciones devolvemos en la respuesta<br>
     *     private int selfId;
     *
     *     NotNull(message = "Name cannot be null")<br>
     *     Size(min = 5, max = 150, message= "Name must be between 5 and 150 characters")<br>
     *     private String nombre;<br>
     *
     *     NotNull(message = "Name cannot be null")
     *     private Person[] personas;
     * un listado de los posibles motivos por los que no se acepta la peticion<br>
     *     respuestas:<br>
     *            409 -> En caso de fallos en las validaciones.<br>
     *            201 -> Objeto creado<br>
     *
     */
    @RequestMapping(method = RequestMethod.POST, produces={"application/x-resource+json"})
    public ResponseEntity<Object> crear(@RequestBody Family familia) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();

        ArrayList<Resource<Family>> resoucesPerson;

        try {

            Set<ConstraintViolation<Family>> violations = validator.validate(familia);
            String[] errores = new String[violations.size()];

            if (violations.size() > 0) {

                int contador = 0;

                for (ConstraintViolation<Family> violation : violations) {

                    errores[contador] = violation.getPropertyPath() + ":" + violation.getMessage();
                    contador++;
                }

                rm.setErrores(errores);
                rm.setMensaje("error de validación");
                response = new ResponseEntity<>(rm, HttpStatus.CONFLICT);

            } else {

                resoucesPerson = familyService.crear(familia);
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
     *
     * @param familia familia con los nuevos valores a modificar
     * @param id representa al campo selfId de la familia , valor por el cual actualizaramos el objeto
     * @return resoucesFamily
     *    requisitos de validaciones<br>
     *
     *     NotNull(message = "Name cannot be null")<br>
     *     Size(min = 5, max = 150, message= "Name must be between 5 and 150 characters")<br>
     *     private String nombre;<br>
     *
     *     NotNull(message = "Name cannot be null")<br>
     *     private Person[] personas;<br>
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces={"application/x-resource+json"})
    public ResponseEntity<Object> modificar(@RequestBody Family familia, @PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ResponseMensaje rm = new ResponseMensaje();

        ArrayList<Resource<Family>> resoucesFamily;

        try {

            Set<ConstraintViolation<Family>> violations = validator.validate(familia);
            String[] errores = new String[violations.size()];

            if (violations.size() > 0) {

                int contador = 0;

                for (ConstraintViolation<Family> violation : violations) {

                    errores[contador] = violation.getPropertyPath() + ":" + violation.getMessage();
                    contador++;
                }

                rm.setErrores(errores);
                rm.setMensaje("error de validación");
                response = new ResponseEntity<>(rm, HttpStatus.CONFLICT);

            } else {

                resoucesFamily = familyService.modficar(id, familia);
                if (resoucesFamily.size() > 0) {

                    response = new ResponseEntity<>(resoucesFamily, HttpStatus.OK);

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

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        try {

            switch (action) {
                case IMPORT_DATA:

                    familyService.importar(COLLECTION_NAME);
                    break;

                case EXPORT_DATA:

                    familyService.exportar(COLLECTION_NAME);
                    break;

            }

            response = new ResponseEntity<>(HttpStatus.OK);


        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("[{Mensaje:" + e.getMessage() + "}]", HttpStatus.CONFLICT);
        }

        return response;

    }
}
