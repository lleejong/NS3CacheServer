package com.choilab.proj.skt.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {
	public synchronized static void newLine(String line){
		try {
			FileWriter fw = new FileWriter(new File("/log.txt"), true);
			fw.write(line);
			
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
