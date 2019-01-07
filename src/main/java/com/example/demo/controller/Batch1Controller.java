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
@RequestMapping("/API/batch")
public class Batch1Controller {

    private static Batch1Service batch1Service = null;
 
    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    Job processJob;

    public Batch1Controller(){
        try {
            batch1Service = Batch1Service.getInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
 
    @RequestMapping( method = RequestMethod.GET)
    public String batch1() throws Exception {
//        boolean resul = false;
//        String msg = "Fail";
//            if(batch1Service.launchBatch1(jobLauncher,processJob)){
//                msg= "Batch job executed";
//            };
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                    .toJobParameters();
            jobLauncher.run(processJob, jobParameters);
        return "Example Batch job executed !!";
        //return msg;
    }

}