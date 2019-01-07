package com.example.demo.batch.step;

import com.example.demo.dao.DataFlowDao;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.io.File;
import java.net.UnknownHostException;

public class CustomReader implements ItemReader<File> {

	DataFlowDao dataFlowDao;
	File[] dataToImport = null;

	/**
	 * Inicializo el dataToImport en el constructor para cargar los archivos
	 */
	public CustomReader()  {
		super();
		try {
			DataFlowDao dataFlowDao = DataFlowDao.getInstance();
			dataToImport = dataFlowDao.readPathFiles();
		}catch (Exception e){
			e.printStackTrace();
		}

	}

	private int count = 0;

	@Override
	public File read() throws Exception, UnexpectedInputException,
			ParseException, NonTransientResourceException {

		if(count < dataToImport.length){

			return dataToImport[count++];

		}else{

			count = 0;

		}

		return null;

	}

}