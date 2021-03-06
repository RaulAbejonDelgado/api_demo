package com.example.demo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

/**
 * Usado para instaciar el ODM Moprhia , donde crea la conexcion En base a los valores<br>
 *     DB_LOCATION<br>
 *     DB_NAME
 */
@Configuration
public class DataSourceConfiguration {

    private static final String DB_LOCATION = "mongodb://localhost:27017";
    private static final String DB_NAME = "publicaciones";
    private Morphia morphia() {
        final Morphia morphia = new Morphia();

        //Le dedimos a Morphia donde estan los pojos.
        //Le podemos decir varias rutas en diferentes paquetes o clases.
        morphia.mapPackage("pojo");

        return morphia;
    }

    @Bean
    public static Datastore getConnection() throws UnknownHostException {
        final Morphia morphia = new Morphia();
        morphia.mapPackage("pojo");
        final Datastore datastore = morphia.createDatastore(new MongoClient(new MongoClientURI(DB_LOCATION)), DB_NAME);
        datastore.ensureIndexes();
        return datastore;
    }


}
