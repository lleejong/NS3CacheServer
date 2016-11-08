package com.choilab.proj.skt;

import com.choilab.proj.skt.config.Config;
import com.choilab.proj.skt.database.DatabaseManager;
import com.choilab.proj.skt.database.MysqlDatabaseManager;

public class App {
	public static void main(String[] args) {
		Config.init();
		DatabaseManager dbManager = MysqlDatabaseManager.getInstance();
		dbManager.init();
	}
}
