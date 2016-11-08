package com.choilab.proj.skt.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import com.choilab.proj.skt.config.Config;

public class MysqlDatabaseManager implements DatabaseManager{
	
	private final String ip;
	private final int port;
	private final String dbName;
	private final String username;
	private final String password;
	private final String connectionUrl;
	
	
	
	private final static MysqlDatabaseManager instance = new MysqlDatabaseManager();
	public static MysqlDatabaseManager getInstance(){
		return MysqlDatabaseManager.instance;
	}
	
	private MysqlDatabaseManager(){
		this.ip = Config.getIpaddr();
		this.port = Config.getPort();
		this.username = Config.getUsername();
		this.password = Config.getPassword();
		this.dbName = Config.getDbName();
		
		this.connectionUrl = "jdbc:mysql://"+ ip + ":" + port + "/" + dbName;
	}

	public void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = getConnection();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private Connection getConnection(){
		Connection conn = null;

		try {
			conn = DriverManager.getConnection(connectionUrl, username, password);
		} catch(SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}
	

	public List<NS3InputData> fetchData() {
		return null;
	}

	public void updateData(NS3InputData dataObj) {
	}

}
