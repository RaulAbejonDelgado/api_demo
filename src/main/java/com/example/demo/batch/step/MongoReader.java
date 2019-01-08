package com.example.demo.batch.step;

import com.example.demo.pojo.Person;
import com.mongodb.MongoClient;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;


import java.util.HashMap;

public class MongoReader extends MongoItemReader<MongoItemReader<Person>> {

	MongoTemplate mongoTemplate;
	MongoItemReader<Person> reader = new MongoItemReader<Person>();

	public MongoReader()  {
		super();
		try {

			mongoTemplate = mongoTemplate();

			reader.setTemplate(mongoTemplate);
			reader.setQuery("{}");
			reader.setTargetType(Person.class);
			reader.setCollection("persons");
			reader.setSort(new HashMap<String, Sort.Direction>() );

		}catch (Exception e){

			e.printStackTrace();
		}

	}

	private int count = 0;

	@Override
	public MongoItemReader<Person> read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {

		System.out.println("Pasamos por mongo reader");

		count++;
		return reader;

	}

	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		return new SimpleMongoDbFactory(new MongoClient(), "publicaciones");
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		MongoTemplate mongoTemplate = new MongoTemplate(mongoDbFactory());
		return mongoTemplate;
	}

}