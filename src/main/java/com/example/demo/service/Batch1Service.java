package com.example.demo.service;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.UnknownHostException;

public class Batch1Service {



    private static Batch1Service INSTANCE = null;

    public static synchronized Batch1Service getInstance() throws UnknownHostException {

        if (INSTANCE == null) {
            INSTANCE = new Batch1Service();
        }

        return INSTANCE;
    }

    public Batch1Service(){
        super();



    }

    public boolean launchBatch1(JobLauncher jobLauncher,Job processJob) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        boolean resul = false;

        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(processJob, jobParameters);
        if(jobLauncher.run(processJob, jobParameters).isRunning()){
            resul = true;
        }

        return resul;
    }
}
