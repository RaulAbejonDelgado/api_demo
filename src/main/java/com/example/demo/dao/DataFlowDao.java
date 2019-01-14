package com.example.demo.dao;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * Esta Practica fue realizada antes de investigar srping Batch
 * Clase usada Para el ejercicio de parseo e importacion de archivos xml a mongoDB y viceversa
 */
public class DataFlowDao {

    private static DataFlowDao INSTANCE = null;
    private static PersonDao personDao = null;
    private static final String PERSONAS = "personas";
    private static final String FAMILIAS = "familias";
    private static final String COMENTARIOS = "comentarios";
    private String dinamicSlash = "//";
    private File directorio = null;

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

    /**
     * Controla la acciones referentes a las exportaciones
     * @param collection Tipo String nombre de la coleccion
     */
    public void objectExport(String collection) throws Exception {

        //seteamos la ruta donde se guardan las colecciones exportadas
        directorio = new File(System.getProperty("user.dir") + dinamicSlash + collection + dinamicSlash);

        if (!System.getProperty("os.name").equals("Linux")) {

            dinamicSlash = "\\";

        }

        switch (collection) {

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
     * Controla la acciones referentes a las importaciones
     * @param collection tipo String nombre de la coleccion
     */
    public void objectImport(String collection) throws Exception {

        //seteamos la ruta donde se guardan las colecciones exportadas
        directorio = new File(System.getProperty("user.dir") + dinamicSlash + collection + dinamicSlash);

        if (!System.getProperty("os.name").equals("Linux")) {

            dinamicSlash = "\\";

        }

        switch (collection) {

            case PERSONAS:

                importPersons();
                break;

            case FAMILIAS:

                importFamilys();
                break;

            case COMENTARIOS:

                importComments();
                break;

            default:

                throw new Exception("Collection not found");
        }

    }

    /**
     *Metodo que lee archivos xml de la carpeta personas y crea los objetos atraves del dao que llama a morphia
     * @throws UnknownHostException Se lanza para indicar que la dirección IP de un host no se pudo determinar.
     * @throws JAXBException para controlar expeciones con archivos
     */
    private void importPersons() throws UnknownHostException, JAXBException {

        File[] archivos = new File[directorio.listFiles().length];

        personDao = PersonDao.getInstance();

        archivos = listarFicherosPorCarpeta(directorio);

        for (File f : archivos) {

            JAXBContext jaxbContext = JAXBContext.newInstance(Person.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Person pe = (Person) jaxbUnmarshaller.unmarshal(f);
            personDao.crear(pe);
        }
    }

    /**
     * Metodo que lee archivos xml de la carpeta Familias y crea los objetos atraves del dao que llama a morphia
     * @throws UnknownHostException Se lanza para indicar que la dirección IP de un host no se pudo determinar.
     * @throws JAXBException para controlar expeciones con archivos
     */
    private void importFamilys() throws UnknownHostException, JAXBException {
        FamilyDao familyDaoDao;

        File[] archivos = new File[directorio.listFiles().length];

        familyDaoDao = FamilyDao.getInstance();

        archivos = listarFicherosPorCarpeta(directorio);

        for (File f : archivos) {

            JAXBContext jaxbContext = JAXBContext.newInstance(Family.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Family fe = (Family) jaxbUnmarshaller.unmarshal(f);
            familyDaoDao.crear(fe);
        }
    }

    /**
     * Metodo que lee archivos xml de la carpeta comentarios y crea los objetos atraves del dao que llama a morphia
     * @throws UnknownHostException Se lanza para indicar que la dirección IP de un host no se pudo determinar.
     * @throws JAXBException para controlar expeciones con archivos
     */
    private void importComments() throws UnknownHostException, JAXBException {
        CommentsDao commentsDao;

        File[] archivos = new File[directorio.listFiles().length];

        commentsDao = CommentsDao.getInstance();

        archivos = listarFicherosPorCarpeta(directorio);

        for (File f : archivos) {

            JAXBContext jaxbContext = JAXBContext.newInstance(Comment.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            Comment ce = (Comment) jaxbUnmarshaller.unmarshal(f);
            commentsDao.crear(ce);
        }
    }

    /**
     * Extraccoin de la coleccion personas a archivos xml
     *
     * @throws IOException*Señala que se ha producido una excepción de E / S de algún tipo. Esta clase es la clase general de excepciones
     *producidas por operaciones de E / S fallidas o interrumpidas.
     */
    private void extractPersons() throws IOException {
        personDao = null;

        personDao = PersonDao.getInstance();

        ArrayList<Person> personas = (ArrayList<Person>) personDao.listar();

        for (Person p : personas) {
            // 1. Write Object to XML String
            write2XMLString(p);
            String pathFilePerson = directorio + dinamicSlash + p.getNombre() + ".xml";

            write2XMLFile(p, pathFilePerson);
        }
    }

    /**
     * Extraccion de la coleccion familias a archivos xml
     *
     * @throws IOException*Señala que se ha producido una excepción de E / S de algún tipo. Esta clase es la clase general de excepciones
     * producidas por operaciones de E / S fallidas o interrumpidas.
     */

    private void extractFamilys() throws IOException {
        FamilyDao familyDaoDao;

        familyDaoDao = FamilyDao.getInstance();

        ArrayList<Family> familias = (ArrayList<Family>) familyDaoDao.listarTodos();

        for (Family f : familias) {

            write2XMLString(f);
            String pathFilePerson = directorio + dinamicSlash + f.getNombre() + ".xml";

            write2XMLFile(f, pathFilePerson);
        }
    }

    /**
     * Extraccion de la coleccion comentarios a archivos xml
     *
     * @throws IOException*Señala que se ha producido una excepción de E / S de algún tipo. Esta clase es la clase general de excepciones
     * producidas por operaciones de E / S fallidas o interrumpidas.
     */

    private void extractComments() throws IOException {
        CommentsDao commentDao;

        commentDao = CommentsDao.getInstance();

        ArrayList<Comment> familias = (ArrayList<Comment>) commentDao.listarTodos();

        for (Comment c : familias) {
            // 1. Write Object to XML String
            write2XMLString(c);
            String pathFilePerson = directorio + dinamicSlash + "comentario_" + c.getSelfId() + ".xml";

            write2XMLFile(c, pathFilePerson);
        }
    }

    /**
     * @param carpeta archivo raiz en realidad representa una carpeta
     * @return File[]
     */
    private static File[] listarFicherosPorCarpeta(File carpeta) {

        File[] archivos = new File[carpeta.listFiles().length];
        try {

            int contador = 0;

            for (final File ficheroEntrada : carpeta.listFiles()) {

                if (ficheroEntrada.isDirectory()) {
                    listarFicherosPorCarpeta(ficheroEntrada);

                } else {

                    if (contador < carpeta.listFiles().length) {
                        archivos[contador] = ficheroEntrada;
                        contador++;
                    }

                    System.out.println(ficheroEntrada.getName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return archivos;
    }

    /*
     * Convert Object to XML String
     */

    /**
     *
     * @param object en la practica entra un objeto tipo File
     * @return devolvemos el resultado de la conversion del archivo en String formateado (INDENT_OUTPUT)
     */
    private static String write2XMLString(Object object)
            throws JsonProcessingException {

        XmlMapper xmlMapper = new XmlMapper();
        // use the line of code for pretty-print XML on console. We should remove it in production.
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        return xmlMapper.writeValueAsString(object);
    }

    /*
     * Write Object to XML file
     */

    /**
     *
     * @param object El objeto que representa a un objeto person, family o comment
     * @param pathFile tipo String que representa la ruta donde iran los nuevos archivos que van a generarse
     */
    private static void write2XMLFile(Object object, String pathFile)
            throws  IOException {

        XmlMapper xmlMapper = new XmlMapper();

        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);

        xmlMapper.writeValue(new File(pathFile), object);
    }


    /**
     * Usado para apoyarme en la practica de spring batch
     * El CustomItemReader sacara los datos de esta funcion
     * @return Array de archivos
     */
    public File[] readPathFiles(){

        ArrayList<File> directorios = new ArrayList<>();


        directorios.add(new File(System.getProperty("user.dir") + dinamicSlash + "personas" + dinamicSlash));
        directorios.add(new File(System.getProperty("user.dir") + dinamicSlash + "familias" + dinamicSlash));
        directorios.add(new File(System.getProperty("user.dir") + dinamicSlash + "comentarios" + dinamicSlash));

        int cantidadFicheros = directorios.stream().mapToInt(d -> d.listFiles().length).sum();

        File[] dataToImport = new File[cantidadFicheros];

        //dataToImport = listarFicherosPorCarpeta(dataToImport);
        int contador = 0;
        for(File d : directorios){

            File [] filesPath = listarFicherosPorCarpeta(d);
            for(File fu: filesPath){

                dataToImport[contador++] = fu;
            }
        }



        return  dataToImport;

    }

}
