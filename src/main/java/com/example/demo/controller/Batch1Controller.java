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
 * Primera practica con spring batch
 */
@RestController
@RequestMapping("/API/batchExample")
public class Batch1Controller {

 
    @Autowired
    private
    JobLauncher jobLauncher;

    @Autowired
    private
    Job processJob;

 
    @RequestMapping( method = RequestMethod.GET)
    public String batch1() throws Exception {

            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(processJob, jobParameters);
        return "Example Batch job executed !!";
        //return msg;
    }

}