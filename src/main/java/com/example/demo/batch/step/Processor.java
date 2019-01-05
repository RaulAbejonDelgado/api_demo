package com.example.demo.batch.step;

import com.example.demo.pojo.Comment;
import org.springframework.batch.item.ItemProcessor;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;

public class Processor implements ItemProcessor<String, String> {

	@Override
	public String process(String data) throws Exception {

		return data.toUpperCase();

	}



}
