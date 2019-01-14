package com.example.demo.controller;
 
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador encargado de cargar los datos demo atraves de una peticcion http en el endpoint localhost:8080//API/batch<br>
 * El proceso mira en la carpeta raiz del proyyecto en las siquientes carpetaas, comentarios, familias, personas
 * Este controlador esta pensado para la practica de spring batch
 */
@RestController
@RequestMapping("/API/batch")
public class CustomBatch1Controller {


    @Autowired
    private
    JobLauncher jobLauncher;

    @Autowired
    private
    Job customProcessJob;

 
    @RequestMapping( method = RequestMethod.GET)
    public String mongoBatch() throws Exception {

            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis() )
                    .toJobParameters();
            jobLauncher.run(customProcessJob, jobParameters);

        return "<a href='http://localhost:8080'>http://localhost:8080</a>";


    }

}