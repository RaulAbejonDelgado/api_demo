package com.example.demo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration
public class DataSourceConfiguration {

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
        final Datastore datastore = morphia.createDatastore(new MongoClient(new MongoClientURI("mongodb://localhost:27017")), "publicaciones");
        datastore.ensureIndexes();
        return datastore;
    }


}
