package com.example.demo.batch.step;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import org.springframework.batch.item.ItemProcessor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class CustomProcessor implements ItemProcessor<File, ArrayList<Object>> {
	int contador = 0 ;


	@Override
	public ArrayList<Object> process(File f) throws Exception {
        ArrayList<Object> objetos = new ArrayList<>();
		JAXBContext jaxbContext = JAXBContext.newInstance(Comment.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

		contador ++;
		System.out.println("Procesando Fichero"+ f.getName()+" nº de archivo "+ contador);


		if(f.getPath().contains("personas")){

			objetos.add((Person) jaxbUnmarshaller.unmarshal(f));

		}

		if(f.getPath().contains("familias")){

            objetos.add((Family) jaxbUnmarshaller.unmarshal(f));

		}

		if(f.getPath().contains("comentarios")){
			Comment comment = (Comment) jaxbUnmarshaller.unmarshal(f);
			//Sete la fecha ya que si no me deja 1970-x-x y da problemas la fecha en la carga inicial
			comment.setFecha(new Date());
            objetos.add(comment);

		}

		return objetos;
	}

}
