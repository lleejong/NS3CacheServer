package com.choilab.proj.skt.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.choilab.proj.skt.ServerConfigure;

public class TaskScheduler {

	
	private ServerSocket serverSocket;
	private int taskID = 1;

	public TaskScheduler() {
		
		try {
			serverSocket = new ServerSocket(ServerConfigure.port);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				//System.out.println("---New Request accepted");
				new TaskHandler(taskID++, socket).run();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
