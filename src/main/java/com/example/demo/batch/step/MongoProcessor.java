package com.example.demo.batch.step;

import com.example.demo.pojo.Person;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.data.MongoItemReader;

import java.io.File;
import java.util.ArrayList;

public class MongoProcessor implements ItemProcessor<MongoItemReader<Person>,ArrayList<Person>> {
	int contador = 0 ;

	ArrayList<Person> personas = new ArrayList<>();


	@Override
	public ArrayList<Person> process(MongoItemReader<Person> mongoItems) throws Exception {

		System.out.println("Procesndo");
		Person p = mongoItems.read();
		personas.add(p);
		contador ++ ;
		return personas;
	}

}
