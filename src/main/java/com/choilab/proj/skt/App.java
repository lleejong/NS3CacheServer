package com.choilab.proj.skt;

import java.util.List;

import com.choilab.proj.skt.config.Config;
import com.choilab.proj.skt.database.DatabaseManager;
import com.choilab.proj.skt.database.MysqlDatabaseManager;
import com.choilab.proj.skt.database.NS3InputData;

public class App {
	public static void main(String[] args) {
		Config.init();
		DatabaseManager dbManager = MysqlDatabaseManager.getInstance();
		dbManager.init();
		
		List<NS3InputData> list = dbManager.fetchData();
		for(NS3InputData data : list){
			System.out.println(data.toString());
		}
		
	}
}
