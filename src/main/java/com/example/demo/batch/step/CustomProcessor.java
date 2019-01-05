package com.example.demo.batch.step;

import com.example.demo.pojo.Comment;
import com.example.demo.pojo.Family;
import com.example.demo.pojo.Person;
import org.springframework.batch.item.ItemProcessor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;

public class CustomProcessor implements ItemProcessor<File, File> {
	int contador = 0 ;

	@Override
	public File process(File f) throws Exception {

		contador ++;
		System.out.println("Procesando Fichero"+ f.getName()+" nº de archivo "+ contador);

		return f;
	}

}
