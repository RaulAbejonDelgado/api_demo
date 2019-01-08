package com.example.demo.batch.step;

import com.example.demo.dao.DataFlowDao;
import com.example.demo.dao.PersonDao;
import com.example.demo.pojo.Person;
import com.example.demo.service.PersonService;
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

import java.util.ArrayList;
import java.util.HashMap;

public class MongoReader extends MongoItemReader<ArrayList<Person>> {

	MongoTemplate mongoTemplate;
	MongoItemReader<Person> reader = new MongoItemReader<Person>();
	ArrayList<MongoItemReader<Person>> test = null;
	ArrayList<Person> personasTest = new ArrayList<>();
	PersonDao personDao = null;
	DataFlowDao dataFlowDao = null;
	boolean flag = false;

	public MongoReader()  {
		super();
		test = new ArrayList<>();

		try {
			String query = "find({})";

			mongoTemplate = mongoTemplate();
			reader.setTemplate(mongoTemplate);
			reader.setQuery("{ familyId: { $gt: 0 } }" );
			reader.setTargetType(Person.class);
			reader.setCollection("persons");
			reader.setPageSize(100);
			reader.setSort(new HashMap<String, Sort.Direction>() {
				{
					put("selfId", Sort.Direction.DESC);
				}
			});

			dataFlowDao = DataFlowDao.getInstance();
			personDao = PersonDao.getInstance();


		}catch (Exception e){

			e.printStackTrace();
		}

	}

	private int count = 0;

	@Override
	public ArrayList<Person> read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {

		System.out.println("Pasamos por mongo reader");
		personasTest = (ArrayList<Person>) personDao.listar();
		if(reader.read() != null){
			System.out.println(reader.read());
			personasTest.add(reader.read());
			return personasTest;
		}
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