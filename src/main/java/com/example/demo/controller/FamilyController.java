package com.example.demo.controller;


import com.example.demo.pojo.Family;
import com.example.demo.pojo.ResponseMensaje;
import com.example.demo.service.FamilyService;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.ArrayList;

@Controller
@CrossOrigin(origins = "*")
@RequestMapping("/publicaciones/family")
public class FamilyController {

    private static FamilyService familyService = null;

    public FamilyController() {
        super();
        try {

            familyService = FamilyService.getInstance();

        } catch (UnknownHostException ue) {

            ue.printStackTrace();
        }


    }

    @RequestMapping(method = RequestMethod.GET)
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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
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

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> crear(@RequestBody Family familia) {
        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        ArrayList<Resource<Family>> resoucesPerson;


        try {
            resoucesPerson = familyService.crear(familia);
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
    public ResponseEntity<Object> modificar(@RequestBody Family familia, @PathVariable int id) {

        ResponseEntity<Object> response = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        ArrayList<Resource<Family>> resoucesFamily;

        try {
            resoucesFamily = familyService.modficar(id, familia);
            if (resoucesFamily.size() > 0) {

                response = new ResponseEntity<>(resoucesFamily, HttpStatus.OK);

            } else {

                response = new ResponseEntity<>(HttpStatus.CONFLICT);
            }


        } catch (Exception e) {

            e.printStackTrace();
        }


        return response;

    }
}
