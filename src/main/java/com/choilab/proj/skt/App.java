package com.choilab.proj.skt;

import java.util.List;

import com.choilab.proj.skt.config.Config;
import com.choilab.proj.skt.database.DatabaseManager;
import com.choilab.proj.skt.database.MysqlDatabaseManager;
import com.choilab.proj.skt.database.NS3Data;

public class App {
	public static void main(String[] args) {
		Config.init();
		DatabaseManager dbManager = MysqlDatabaseManager.getInstance();
		dbManager.init();
		
		System.out.println("AAAA");
		List<NS3Data> list = dbManager.fetchData();
		for(NS3Data data : list){
			System.out.println(data.toString());
		}
		
	}
}
