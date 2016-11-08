package com.choilab.proj.skt.config;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Config {
	
	private Config() {

	}

	private static Config instance = new Config();

	public static void init() {
		instance.parseXmlFile();
	}
	
	
	
	private static String ipaddr = "localhost";
	private static int port = 3306;
	private static String dbName = "simpletest";  
	private static String username = "root";
	private static String password = "jjong"; 
	
	

	public static String getIpaddr() {
		return ipaddr;
	}

	public static int getPort() {
		return port;
	}

	public static String getDbName() {
		return dbName;
	}

	public static String getUsername() {
		return username;
	}

	public static String getPassword() {
		return password;
	}


	private void parseXmlFile() {

		try {
			File xmlFile = new File("resources/config.xml");

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = dbf.newDocumentBuilder();
			Document xmlDoc = docBuilder.parse(xmlFile);
			
			
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
