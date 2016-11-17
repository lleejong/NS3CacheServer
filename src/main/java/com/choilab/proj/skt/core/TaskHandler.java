package com.choilab.proj.skt.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.choilab.proj.skt.database.DatabaseManager;
import com.choilab.proj.skt.database.MysqlDatabaseManager;

public class TaskHandler extends Thread{
	private int taskID;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private DatabaseManager dbManager;
	
	public TaskHandler(int taskID, Socket socket){
		this.taskID = taskID;
		this.socket = socket;
		this.dbManager = MysqlDatabaseManager.getInstance();
	}
	
	public void run(){
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String fromClient = reader.readLine();
			String[] temp = fromClient.split("//");
			double txLoss = Double.parseDouble(temp[1]);
			double txDelay = Double.parseDouble(temp[2]);
			double txJitter = Double.parseDouble(temp[3]);
			double rxLoss = Double.parseDouble(temp[4]);
			double rxDelay = Double.parseDouble(temp[5]);
			double rxJitter = Double.parseDouble(temp[6]);
			
			
			

			
			//System.out.println("---Task " + taskID + " : received msg -> " + fromClient);
			//messenger.sendMsg("[CHECK]/" + txLoss + "/" + txDelay+"/" + txJitter + "/" + rxLoss + "," + rxDelay + "/" + rxJitter);
			
			
			//writer.println("[ACK]");
			//writer.flush();
			
			reader.close();
			writer.close();
			socket.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
