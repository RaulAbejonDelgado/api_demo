package com.example.demo.batch.step;

import com.example.demo.batch.config.MongoConfig;
import com.example.demo.dao.DataFlowDao;
import com.example.demo.pojo.Person;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.data.domain.Sort.Direction;

import java.io.File;
import java.util.HashMap;

public class MongoReader extends MongoItemReader<MongoItemReader<Person>> {

	DataFlowDao dataFlowDao;
	File[] dataToImport = null;
	private String collection = "person";

	private int count = 0;

	@Override
	public MongoItemReader<Person> read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {
//		MongoConfig mongoConfig = new MongoConfig();
//		//boolean e = mongoConfig.mongoTemplate().collectionExists("persons");
//		MongoItemReader<Person> reader = new MongoItemReader<Person>();
//		reader.setTemplate(mongoConfig.mongoOperations());
//		reader.setTargetType((Class<? extends Person>) Person.class);
//		reader.setCollection("person");

		MongoConfig mongoConfig = new MongoConfig();
		MongoItemReader<Person> reader = new MongoItemReader<Person>();
		reader.setTemplate(mongoConfig.mongoTemplate());
		reader.setQuery("{}");
		reader.setTargetType(Person.class);
		reader.setSort(new HashMap<String, Direction>() {
			{
				put("selfId", Direction.DESC);
			}
		});
		return reader;


	}

}