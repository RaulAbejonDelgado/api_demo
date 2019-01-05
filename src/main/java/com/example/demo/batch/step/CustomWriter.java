package com.example.demo.batch.step;

import com.example.demo.dao.CommentsDao;
import com.example.demo.dao.FamilyDao;
import com.example.demo.dao.PersonDao;
import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import org.springframework.batch.item.ItemWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CustomWriter implements ItemWriter<File> {

	@Override
	public void write(List<? extends File> list) throws Exception {

		JAXBContext jaxbContext = JAXBContext.newInstance(Comment.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		ArrayList<Person> personas = new ArrayList<>();
		ArrayList<Family> familias = new ArrayList<>();
		ArrayList<Comment> comentarios = new ArrayList<>();
		PersonDao personDao = PersonDao.getInstance();
		FamilyDao familyDao = FamilyDao.getInstance();
		CommentsDao commentsDao = CommentsDao.getInstance();

		System.out.println("En este ciclo vamos a escribir "+ list.size()+ " elementos");

		for(File f : list){

			if(f.getPath().contains("personas")){

				personas.add((Person) jaxbUnmarshaller.unmarshal(f));

			}

			if(f.getPath().contains("familias")){

				familias.add((Family) jaxbUnmarshaller.unmarshal(f));

			}

			if(f.getPath().contains("comentarios")){

				comentarios.add((Comment) jaxbUnmarshaller.unmarshal(f));

			}

		}
		if(personas.size() > 0){

			personDao.crearPorLote(personas);

		}

		if(familias.size() > 0 ){

			familyDao.crearPorLote(familias);

		}
		if(comentarios.size() > 0){

			commentsDao.crearPorLote(comentarios);

		}

	}
}