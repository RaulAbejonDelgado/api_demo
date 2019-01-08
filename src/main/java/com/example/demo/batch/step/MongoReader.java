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
			reader.setSort(new HashMap<String, Sort.Direction>() {
				{
					put("selfId", Sort.Direction.DESC);
				}
			});

			Person p = reader.read();


		}catch (Exception e){

			e.printStackTrace();
		}

	}

	private int count = 0;

	@Override
	public MongoItemReader<Person> read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
		count++;
		System.out.println("Pasamos por mongo reader");
		if(reader.read() != null){
			return reader;
		}
		System.out.println("La pasada nº"+ count +" ha sido nula");
		return null;

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