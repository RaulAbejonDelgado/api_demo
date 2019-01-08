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

@RestController
@RequestMapping("/API/MongobatchRead")
public class MongoBatch1Controller {

    private static Batch1Service batch1Service = null;

    @Autowired
    JobLauncher mongoProcessJob;

    @Autowired
    Job customProcessJobMongo;

    public MongoBatch1Controller() {
        try {
            batch1Service = Batch1Service.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(method = RequestMethod.GET)
    public String mongoBatch() throws Exception {

        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        mongoProcessJob.run(customProcessJobMongo, jobParameters);

        return "Mongo Batch job executed !!";

    }

}