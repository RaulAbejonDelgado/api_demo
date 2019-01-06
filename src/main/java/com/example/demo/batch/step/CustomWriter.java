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

public class CustomWriter implements ItemWriter<Object> {
	/**
	 *
	 * @param list Lista de objetos de colecciones distintas
	 *             Por lo que aun hay que hacer un minimo proceso
	 *             para discriminar por el tipo de clase
	 *
	 * @throws Exception
	 */
	@Override
	public void write(List<?> list) throws Exception {

		ArrayList<Person> personas = new ArrayList<>();
		ArrayList<Family> familias = new ArrayList<>();
		ArrayList<Comment> comentarios = new ArrayList<>();
		PersonDao personDao = PersonDao.getInstance();
		FamilyDao familyDao = FamilyDao.getInstance();
		CommentsDao commentsDao = CommentsDao.getInstance();
 		System.out.println("En este ciclo vamos a escribir "+ list.size()+ " elementos");

		for(Object o : list){

			if(((ArrayList) o).get(0).getClass().getName().contains("Person")){

				Person p =  (Person) ((ArrayList) o).get(0) ;
				personas.add(p);
			}
			if(((ArrayList) o).get(0).getClass().getName().contains("Family")){

				Family f = (Family) ((ArrayList) o).get(0);
				familias.add(f);
			}
			if(((ArrayList) o).get(0).getClass().getName().contains("Comment")){

				Comment c = (Comment) ((ArrayList) o).get(0);
				comentarios.add(c);
			}

		}
		if(personas.size() > 0){

			System.out.println("Se cargan "+ personas.size()+" Personas");
			personDao.crearPorLote(personas);

		}

		if(familias.size() > 0 ){

			System.out.println("Se cargan "+ familias.size()+" Familias");
			familyDao.crearPorLote(familias);

		}
		if(comentarios.size() > 0){

			System.out.println("Se cargan "+ comentarios.size()+" Comentarios");
			commentsDao.crearPorLote(comentarios);

		}

	}
}