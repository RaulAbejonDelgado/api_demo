package com.example.demo.batch.config;

import com.example.demo.batch.listener.JobCompletionListener;
import com.example.demo.batch.step.*;
import com.example.demo.pojo.Person;
import com.mongodb.DBObject;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.File;
import java.net.UnknownHostException;
import java.util.ArrayList;

@Configuration
public class ExportMongoBatchConfig {

	@Autowired
	public JobBuilderFactory exportCustomjobBuilderFactory;

	@Autowired
	public StepBuilderFactory exportCustomstepBuilderFactory;

	@Autowired
	private MongoConfig mongoConfig;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Bean
	public Job customProcessJob1() throws UnknownHostException {
		return exportCustomjobBuilderFactory.get("exportCustomProcessJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(exportCustomOrderStep1())
				.end()
				.build();
	}

	@Bean
	public Step exportCustomOrderStep1()  {
		return exportCustomstepBuilderFactory.get("exportCustomOrderStep1").
				<MongoItemReader< Person >, File> chunk(1)
				.reader(new MongoReader())
				//.processor(new MongoProcessor())
				.writer(new CustomWriter())
				.build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}

}
