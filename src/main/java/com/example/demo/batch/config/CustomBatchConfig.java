package com.example.demo.batch.config;

import com.example.demo.batch.listener.JobCompletionListener;
import com.example.demo.batch.step.*;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.UnknownHostException;

@Configuration
public class CustomBatchConfig {

	@Autowired
	public JobBuilderFactory customjobBuilderFactory;

	@Autowired
	public StepBuilderFactory customstepBuilderFactory;

	@Bean
	public Job customProcessJob() throws UnknownHostException {
		return customjobBuilderFactory.get("customProcessJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(customOrderStep1())
				.end()
				.build();
	}

	@Bean
	public Step customOrderStep1()  {
		return customstepBuilderFactory.get("customOrderStep1").
				<File, File> chunk(10)
				.reader(new CustomReader())
				.processor(new CustomProcessor())
				.writer(new CustomWriter())
				.build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}

}
