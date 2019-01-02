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
@RequestMapping("/publicaciones/family")
public class FamilyController {

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
