package com.example.demo.dao;

import com.example.demo.DataSourceConfiguration;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.mongodb.WriteResult;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class DataFlowDao {

    private static DataFlowDao INSTANCE = null;
    private static PersonDao personDao = null;
    private static final String PERSONAS = "personas";
    private static final String FAMILIAS = "familias";
    private static final String COMENTARIOS = "comentarios";
    String dinamicSlash = "//" ;
    File directorio = null;

    private DataFlowDao() throws UnknownHostException {

        super();
        personDao = PersonDao.getInstance();

    }

    public static synchronized DataFlowDao getInstance() throws UnknownHostException {

        if (INSTANCE == null) {

            INSTANCE = new DataFlowDao();

        }

        return INSTANCE;
    }

    public void objectExport(String collection) throws Exception, IOException {


        //seteamos la ruta donde se guardan las colecciones exportadas
        directorio = new File(System.getProperty("user.dir")+dinamicSlash+collection+dinamicSlash);


        if(!System.getProperty("os.name").equals("Linux")){

            dinamicSlash = "\\";

        }

        directorio.mkdir();
        switch (collection){

            case PERSONAS:

                extractPersons();
                break;

            case FAMILIAS:

                extractFamilys();
                break;

            case COMENTARIOS:

                extractComments();
                break;

            default:

                throw new Exception("Collection not found");
        }
    }

    /**
     * Extraccoin de la coleccion personas a archivos xml
     * @throws IOException
     */
    public void extractPersons() throws IOException{
        PersonDao personaDao = null;

        personaDao = PersonDao.getInstance();

        ArrayList<Person> personas = (ArrayList<Person>) personaDao.listar();

        for(Person p : personas){
            // 1. Write Object to XML String
            String xmlPerson = write2XMLString(p);
            //String pathFilePerson = System.getProperty("user.dir") +"\\personas\\"+ p.getNombre()+".xml";
            String pathFilePerson = directorio + dinamicSlash + p.getNombre()+".xml";

            write2XMLFile(p, pathFilePerson);
        }
    }

    /**
     * Extraccion de la coleccion familias a archivos xml
     * @throws IOException
     */

    public void extractFamilys() throws IOException {
        FamilyDao familyDaoDao = null;

        familyDaoDao = FamilyDao.getInstance();

        ArrayList<Family> familias = (ArrayList<Family>) familyDaoDao.listarTodos();

        for(Family f : familias){
            // 1. Write Object to XML String
            String xmlPerson = write2XMLString(f);
            //String pathFilePerson = System.getProperty("user.dir") +"\\personas\\"+ p.getNombre()+".xml";
            String pathFilePerson = directorio + dinamicSlash + f.getNombre()+".xml";

            write2XMLFile(f, pathFilePerson);
        }
    }

    /**
     * Extraccion de la coleccion comentarios a archivos xml
     * @throws IOException
     */

    public void extractComments() throws IOException {
        CommentsDao commentDao = null;

        commentDao = CommentsDao.getInstance();

        ArrayList<Comment> familias = (ArrayList<Comment>) commentDao.listarTodos();

        for(Comment c : familias){
            // 1. Write Object to XML String
            String xmlPerson = write2XMLString(c);
            //String pathFilePerson = System.getProperty("user.dir") +"\\personas\\"+ p.getNombre()+".xml";
            String pathFilePerson = directorio + dinamicSlash +"comentario_"+c.getSelfId()+".xml";

            write2XMLFile(c, pathFilePerson);
        }
    }

    /*
     * Convert Object to XML String
     */
    public static String write2XMLString(Object object)
            throws JsonProcessingException {

        XmlMapper xmlMapper = new XmlMapper();
        // use the line of code for pretty-print XML on console. We should remove it in production.
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        return xmlMapper.writeValueAsString(object);
    }

    /*
     * Write Object to XML file
     */
    public static void write2XMLFile(Object object, String pathFile)
            throws JsonGenerationException, JsonMappingException, IOException {

        XmlMapper xmlMapper = new XmlMapper();
        // use the line of code for pretty-print XML on console. We should remove it in production.
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        xmlMapper.writeValue(new File(pathFile), object);
    }



    public void personImport() {

        //List<Person> personas = datastore.createQuery(Person.class).asList();

    }
}
