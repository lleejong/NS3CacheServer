package com.choilab.proj.skt.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class TaskScheduler {
	
	private final int PORT = 8888;
	private ServerSocket serverSocket;
	private int taskID = 1;
	
	public TaskScheduler() throws IOException{
		serverSocket = new ServerSocket(PORT);
	}
	
	public void run(){
		while(true){
			try {
				Socket socket = serverSocket.accept();
				new TaskHandler(taskID++, socket).run();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

}
