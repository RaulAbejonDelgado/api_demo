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

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/publicaciones/person")
@RestController
public class PersonController {

    private static ArrayList<Person> persons = null;
    private static PersonService servicioPerson = null;

    private static Validator validator ;

    public PersonController()   {
        super();
        try {

            servicioPerson = PersonService.getInstance();
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();

        }catch (UnknownHostException e){

            e.printStackTrace();
        }

    }

    @RequestMapping(method = RequestMethod.GET)
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
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
        }


        System.out.println(id);


        return response;

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> delete(@PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(persons, HttpStatus.INTERNAL_SERVER_ERROR);

        try {

            if (servicioPerson.eliminar(id)) {

                response = new ResponseEntity<>(HttpStatus.OK);

            } else {

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

    @RequestMapping( method = RequestMethod.POST)
    public ResponseEntity<Object> crear(@RequestBody Person persona) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Person>> resoucesPerson;
        ResponseMensaje rm = new ResponseMensaje();

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

            }else{

                resoucesPerson = servicioPerson.crear(persona);

                if (resoucesPerson.size() == 1) {

                    response = new ResponseEntity<>(resoucesPerson, HttpStatus.CREATED);
                } else {

                    response = new ResponseEntity<>( HttpStatus.CONFLICT);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Object> modificar(@RequestBody Person persona, @PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        ArrayList<Resource<Person>> resoucesPerson;
        ResponseMensaje rm = new ResponseMensaje();

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

            }else{
                resoucesPerson = servicioPerson.modficar(id, persona);
                if(resoucesPerson.size() == 1){

                    response = new ResponseEntity<>(resoucesPerson, HttpStatus.OK);

                }else{

                    response = new ResponseEntity<>( HttpStatus.CONFLICT);
                }
            }

        }catch (Exception e) {

            e.printStackTrace();
        }

        return response;

    }

}



