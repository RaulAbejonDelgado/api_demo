package com.example.demo.service;

import com.example.demo.dao.ComentarioDao;
import com.example.demo.dao.PersonDao;
import com.example.demo.pojo.Coment;
import com.example.demo.pojo.Person;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class ComentService {

    private static ComentService INSTANCE = null;

    private static ComentarioDao comentarioDao = null;


    public ComentService() {
        super();
        comentarioDao = ComentarioDao.getInstance();
    }

    public static synchronized ComentService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ComentService();
        }
        return INSTANCE;
    }


    public ArrayList<Coment> listar() throws UnknownHostException {
        ArrayList<Coment> comentarios = new ArrayList<Coment>();
        comentarios = comentarioDao.listarTodos();

        return comentarios;
    }

    public Coment obtenerPorId(int id) throws UnknownHostException {

        return comentarioDao.obtenerPorId(id);
    }

    public boolean eliminar(int id) throws  UnknownHostException {
        boolean resul = false;
        if(comentarioDao.eliminar(id)){
            resul = true;
        }

        return resul;
    }

    public boolean crear(Coment c) throws  UnknownHostException {
        boolean resul  = false;
        if(comentarioDao.crear(c)){
            resul = true;
        }

        return resul;
    }

    public boolean modficar(int id, Coment c) throws  UnknownHostException{
        boolean resul = false;

        if(comentarioDao.modificar(id, c)){
            resul = true;
        }

        return resul;
    }
}
