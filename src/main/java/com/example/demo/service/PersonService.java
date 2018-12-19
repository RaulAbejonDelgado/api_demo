package com.example.demo.service;

import com.example.demo.dao.PersonDao;
import com.example.demo.dao.PersonaDao;
import com.example.demo.pojo.Person;

import java.net.UnknownHostException;
import java.util.ArrayList;

public class PersonService {

    private static PersonService INSTANCE = null;

    private static PersonDao personDao = null;


    public PersonService(){
        super();
        personDao = PersonDao.getInstance();
    }

    public static synchronized PersonService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new PersonService();
        }
        return INSTANCE;
    }


    public ArrayList<Person>  listar() throws UnknownHostException {
        ArrayList<Person> persons = new ArrayList<Person>();
        persons = personDao.listar();

        return persons;
    }
}
