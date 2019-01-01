package com.example.demo.dao;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

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

    public void objectExport(String collection) throws Exception {

        //seteamos la ruta donde se guardan las colecciones exportadas
        directorio = new File(System.getProperty("user.dir") + dinamicSlash + collection + dinamicSlash);

        if (!System.getProperty("os.name").equals("Linux")) {

            dinamicSlash = "\\";

        }

        directorio.mkdir();
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

    public void objectImport(String collection) throws Exception, IOException {

        //seteamos la ruta donde se guardan las colecciones exportadas
        directorio = new File(System.getProperty("user.dir") + dinamicSlash + collection + dinamicSlash);

        if (!System.getProperty("os.name").equals("Linux")) {

            dinamicSlash = "\\";

        }

        directorio.mkdir();

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

    private void importFamilys() throws UnknownHostException, JAXBException {
        FamilyDao familyDaoDao = null;

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

    private void importComments() throws UnknownHostException, JAXBException {
        CommentsDao commentsDao = null;

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
     * @throws IOException
     */
    public void extractPersons() throws IOException {
        personDao = null;

        personDao = PersonDao.getInstance();

        ArrayList<Person> personas = (ArrayList<Person>) personDao.listar();

        for (Person p : personas) {
            // 1. Write Object to XML String
            String xmlPerson = write2XMLString(p);
            //String pathFilePerson = System.getProperty("user.dir") +"\\personas\\"+ p.getNombre()+".xml";
            String pathFilePerson = directorio + dinamicSlash + p.getNombre() + ".xml";

            write2XMLFile(p, pathFilePerson);
        }
    }

    /**
     * Extraccion de la coleccion familias a archivos xml
     *
     * @throws IOException
     */

    public void extractFamilys() throws IOException {
        FamilyDao familyDaoDao = null;

        familyDaoDao = FamilyDao.getInstance();

        ArrayList<Family> familias = (ArrayList<Family>) familyDaoDao.listarTodos();

        for (Family f : familias) {
            // 1. Write Object to XML String
            String xmlPerson = write2XMLString(f);
            //String pathFilePerson = System.getProperty("user.dir") +"\\personas\\"+ p.getNombre()+".xml";
            String pathFilePerson = directorio + dinamicSlash + f.getNombre() + ".xml";

            write2XMLFile(f, pathFilePerson);
        }
    }

    /**
     * Extraccion de la coleccion comentarios a archivos xml
     *
     * @throws IOException
     */

    public void extractComments() throws IOException {
        CommentsDao commentDao = null;

        commentDao = CommentsDao.getInstance();

        ArrayList<Comment> familias = (ArrayList<Comment>) commentDao.listarTodos();

        for (Comment c : familias) {
            // 1. Write Object to XML String
            String xmlPerson = write2XMLString(c);
            //String pathFilePerson = System.getProperty("user.dir") +"\\personas\\"+ p.getNombre()+".xml";
            String pathFilePerson = directorio + dinamicSlash + "comentario_" + c.getSelfId() + ".xml";

            write2XMLFile(c, pathFilePerson);
        }
    }

    /**
     * @param carpeta los archivos de la carpeta
     * @return Array de ficheros
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

}
