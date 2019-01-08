package com.example.demo.batch.config;

import com.example.demo.batch.listener.JobCompletionListener;
import com.example.demo.batch.step.MongoProcessor;
import com.example.demo.batch.step.MongoReader;
import com.example.demo.batch.step.MongoWriter;
import com.example.demo.pojo.Person;
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

import java.net.UnknownHostException;
import java.util.ArrayList;

@Configuration
public class MongoBatchConfig {

	@Autowired
	public JobBuilderFactory mongojobBuilderFactory;

	@Autowired
	public StepBuilderFactory mongoBuilderFactory;;

	@Bean
	public Job customProcessJobMongo() throws UnknownHostException {
		return mongojobBuilderFactory.get("customProcessJobMongo")
				.incrementer(new RunIdIncrementer())
				.listener(listener())
				.flow(mongoOrderStep())
				.end()
				.build();
	}


	@Bean
	public Step mongoOrderStep()  {
		return mongoBuilderFactory.get("mongoOrderStep").
				<ArrayList<Person>,ArrayList<Person>> chunk(1)
				.reader(new MongoReader())
				.processor(new MongoProcessor())
				.writer(new MongoWriter())
				.build();
	}

	@Bean
	public JobExecutionListener listener() {
		return new JobCompletionListener();
	}




}
