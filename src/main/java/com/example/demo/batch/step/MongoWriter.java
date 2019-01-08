package com.example.demo.batch.step;

import com.example.demo.dao.CommentsDao;
import com.example.demo.dao.FamilyDao;
import com.example.demo.dao.PersonDao;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import org.springframework.batch.item.ItemWriter;

import java.util.ArrayList;
import java.util.List;

public class MongoWriter implements ItemWriter<Object> {
	/**
	 * @param list Lista de objetos de colecciones distintas
	 *             Por lo que aun hay que hacer un minimo proceso
	 *             para discriminar por el tipo de clase
	 * @throws Exception
	 */
	@Override
	public void write(List<?> list) throws Exception {

		System.out.println("Mongo writer");
	}
}