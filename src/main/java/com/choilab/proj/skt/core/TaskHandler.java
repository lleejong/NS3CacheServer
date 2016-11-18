package com.choilab.proj.skt.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import com.choilab.proj.skt.cache.CacheManager;
import com.choilab.proj.skt.cache.SimpleCacheManager;
import com.choilab.proj.skt.database.DatabaseManager;
import com.choilab.proj.skt.database.MysqlDatabaseManager;
import com.choilab.proj.skt.database.NS3Data;

public class TaskHandler extends Thread{
	private int taskID;
	private Socket socket;
	private PrintWriter writer;
	private BufferedReader reader;
	private CacheManager cacheManager;
	
	public TaskHandler(int taskID, Socket socket){
		this.taskID = taskID;
		this.socket = socket;
		this.cacheManager = SimpleCacheManager.getInstance();
	}
	
	public void run(){
		try {
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			String fromClient = reader.readLine();
			System.out.println("--Received MSG : " + fromClient);
			String[] temp = fromClient.split("/");
			double txLoss = Double.parseDouble(temp[1]);
			double txDelay = Double.parseDouble(temp[2]);
			double txJitter = Double.parseDouble(temp[3]);
			double rxLoss = Double.parseDouble(temp[4]);
			double rxDelay = Double.parseDouble(temp[5]);
			double rxJitter = Double.parseDouble(temp[6]);
			
			NS3Data request = new NS3Data(txLoss,txDelay,txJitter,rxLoss,rxDelay,rxJitter);
			NS3Data result = cacheManager.isHit(request);
			if(result == null){
				writer.println("[MISS]");
				writer.flush();
				String throughput = reader.readLine();
				request.setThroughput(Double.parseDouble(throughput.split(" ")[0]));
				cacheManager.update(request);
				//writer.println("[ACK]");
				//writer.flush();
			}
			else{
				writer.println("[HIT]/" + result.getThroughput());
				writer.flush();
			}
			
			reader.close();
			writer.close();
			socket.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
