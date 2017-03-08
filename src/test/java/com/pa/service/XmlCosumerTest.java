package com.pa.service;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Test;

public class XmlCosumerTest {
	
	@Test
	public void consumirXml(){
		try {
			XmlService xmlConsumer = new XmlService(new File("C:\\Users\\Alex\\workspace\\cv_paulo_borba.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
