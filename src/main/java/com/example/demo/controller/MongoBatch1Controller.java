package com.example.demo.controller;

import com.example.demo.service.Batch1Service;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Para la practica de lectura  con spring batch y mongoDB
 * el problema que me he encontrado con esta practica, es que en el flujo entre el reader,process y writer
 * pierdo parte de los datos sacando solo la mitad de datos
 */
@RestController
@RequestMapping("/API/MongobatchRead")
public class MongoBatch1Controller {

    @Autowired
    JobLauncher mongoProcessJob;

    @Autowired
    Job customProcessJobMongo;

    public MongoBatch1Controller() {
        super();

    }

    @RequestMapping(method = RequestMethod.GET)
    public String mongoBatch() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        mongoProcessJob.run(customProcessJobMongo, jobParameters);

        return "Mongo Batch job executed !!";

    }


}