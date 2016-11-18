package com.choilab.proj.skt;

import com.choilab.proj.skt.config.Config;
import com.choilab.proj.skt.core.TaskScheduler;
import com.choilab.proj.skt.database.DatabaseManager;
import com.choilab.proj.skt.database.MysqlDatabaseManager;

public class App {
	public static void main(String[] args) {
		Config.init();
		DatabaseManager dbManager = MysqlDatabaseManager.getInstance();
		dbManager.init();
		
		System.out.println("Server Running...");
//		List<NS3Data> list = dbManager.fetchData();
//		for(NS3Data data : list){
//			System.out.println(data.toString());
//		}
		TaskScheduler taskScheduler = new TaskScheduler();
		taskScheduler.run();	
	}
}
