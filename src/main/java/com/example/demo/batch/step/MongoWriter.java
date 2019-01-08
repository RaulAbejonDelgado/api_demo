package com.example.demo.batch.step;


import com.example.demo.pojo.Person;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.MongoItemWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MongoWriter implements ItemWriter<ArrayList<Person>> {

    private String dinamicSlash = "//";
    private File directorio = null;
    private String collection = "personas_batch";
    int count = 0;

    /**
     * @param list Lista de objetos de colecciones distintas
     *             Por lo que aun hay que hacer un minimo proceso
     *             para discriminar por el tipo de clase
     * @throws Exception
     */
    @Override
    public void write(List<? extends ArrayList<Person>> list) throws Exception {
        //seteamos la ruta donde se guardan las colecciones exportadas
        directorio = new File(System.getProperty("user.dir") + dinamicSlash + collection + dinamicSlash);

        if (!System.getProperty("os.name").equals("Linux")) {

            dinamicSlash = "\\";

        }
        System.out.println("Mongo writer");
        list.forEach(l -> {
            if (l != null) {
                l.forEach(p -> {
                    if (p != null) {
                        System.out.println("*************************WRITER**************************" + p.getNombre());
                        try {
                            String xmlPerson = write2XMLString(p);
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        String pathFilePerson = directorio + dinamicSlash + p.getNombre() + "test.xml";

                        try {
                            write2XMLFile(p, pathFilePerson);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                });
            }

        });
        count++;
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