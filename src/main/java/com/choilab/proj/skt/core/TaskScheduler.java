package com.choilab.proj.skt.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TaskScheduler {

	private final int PORT = 6789;
	private ServerSocket serverSocket;
	private int taskID = 1;

	public TaskScheduler() {
		try {
			serverSocket = new ServerSocket(PORT);
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
