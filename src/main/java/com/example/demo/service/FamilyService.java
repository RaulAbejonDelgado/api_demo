package com.example.demo.service;

import com.example.demo.dao.FamilyDao;
import com.example.demo.dao.PersonDao;
import com.example.demo.pojo.Family;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class FamilyService {

    private static FamilyService INSTANCE = null;
    private static FamilyDao familiasDao = null;

    public static synchronized FamilyService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FamilyService();
        }
        return INSTANCE;
    }

    public FamilyService() {
        super();
        familiasDao = FamilyDao.getInstance();
    }


    public ArrayList<Family> listar() throws UnknownHostException {
        ArrayList<Family> familias = new ArrayList<Family>();

        familias = familiasDao.listarTodos();

        return familias;
    }


    public Family obtenerPorId(int id) throws UnknownHostException {

        return familiasDao.obtenerPorId(id);
    }

    public boolean eliminar(int id) throws  UnknownHostException {
        boolean resul = false;
        if(familiasDao.eliminar(id)){
            resul = true;
        }

        return resul;
    }

    public boolean crear(Family f) throws  UnknownHostException {
        boolean resul  = false;
        if(familiasDao.crear(f)){
            resul = true;
        }

        return resul;
    }

    public boolean modficar(int id, Family f) throws  UnknownHostException{
        boolean resul = false;

        if(familiasDao.modificar(id, f)){
            resul = true;
        }

        return resul;
    }


}
