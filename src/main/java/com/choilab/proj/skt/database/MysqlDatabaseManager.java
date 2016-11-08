package com.choilab.proj.skt.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.choilab.proj.skt.config.Config;

public class MysqlDatabaseManager implements DatabaseManager {

	private final String ip;
	private final int port;
	private final String dbName;
	private final String username;
	private final String password;
	private final String connectionUrl;

	private final static MysqlDatabaseManager instance = new MysqlDatabaseManager();

	public static MysqlDatabaseManager getInstance() {
		return MysqlDatabaseManager.instance;
	}

	private MysqlDatabaseManager() {
		this.ip = Config.getIpaddr();
		this.port = Config.getPort();
		this.username = Config.getUsername();
		this.password = Config.getPassword();
		this.dbName = Config.getDbName();

		//this.connectionUrl = "jdbc:mysql://" + ip + ":" + port + "/" + dbName;
		this.connectionUrl = "jdbc:mysql://" + ip + "/" + dbName;
	}

	public void init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(connectionUrl, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return conn;
	}

	public List<NS3InputData> fetchData() {
		List<NS3InputData> dataList = new ArrayList<NS3InputData>();

		try {
			// get connection from the connection pool.
			Connection conn = getConnection();

			// --------------------- 완성된 쿼리 형태 예시 ------------------
			// SELECT * FROM ns3data;

			String query = "SELECT * FROM ns3data";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			//double txLoss, double txDelay, double txJitter, double rxLoss, double rxDelay, double rxJitter
			//TxDelay, TxLoss, TxJitter, RxDelay, RxLoss, RxJitter, Thorughput
			while(rs.next()){
				double txLoss = rs.getDouble("TxLoss");
				double txDelay = rs.getDouble("TxDelay");
				double txJitter = rs.getDouble("TxJitter");
				
				double rxLoss = rs.getDouble("RxLoss");
				double rxDelay = rs.getDouble("RxDelay");
				double rxJitter = rs.getDouble("RxJitter");
				double throughput = rs.getDouble("Throughput"); 
				
				dataList.add(new NS3InputData(txLoss,txDelay,txJitter,rxLoss,rxDelay,rxJitter,throughput));
				
			}
			
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dataList;
	}

	public void updateData(NS3InputData dataObj) {
		// insert into ns3data (TxDelay, TxJitter, TxLoss, RxDelay, RxJitter,
		// RxLoss, Throughput) values
		try {
			Connection conn = getConnection();

			String query = "INSERT INTO ns3data " + "(TxDelay, TxJitter, TxLoss, RxDelay, RxJitter, RxLoss, Throughput) " + "VALUES(?, ?, ?, ?, ?, ?, ?)";

			PreparedStatement pstmt = conn.prepareStatement(query);
			pstmt.setDouble(1, dataObj.getTxDelay());
			pstmt.setDouble(2, dataObj.getTxJitter());
			pstmt.setDouble(3, dataObj.getTxLoss());
			pstmt.setDouble(4, dataObj.getRxDelay());
			pstmt.setDouble(5, dataObj.getRxJitter());
			pstmt.setDouble(6, dataObj.getRxLoss());
			pstmt.setDouble(7, dataObj.getThroughput());

			pstmt.executeUpdate();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
