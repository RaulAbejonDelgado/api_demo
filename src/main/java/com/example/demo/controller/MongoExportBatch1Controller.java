package com.example.demo.controller;
 
import com.example.demo.service.Batch1Service;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/API/MongobatchExport")
public class MongoExportBatch1Controller {

    private static Batch1Service batch1Service = null;

    @Autowired
    JobLauncher jobLauncher1;

    @Autowired
    Job customProcessJob1;

    public MongoExportBatch1Controller(){
        try {
            batch1Service = Batch1Service.getInstance();
        } catch (Exception e){
            e.printStackTrace();
        }

    }
 
    @RequestMapping( method = RequestMethod.GET)
    public String mongoBatch() throws Exception {

            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis() )
                    .toJobParameters();
        jobLauncher1.run(customProcessJob1, jobParameters);

        return "Mongo Batch job executed !!";

    }

}