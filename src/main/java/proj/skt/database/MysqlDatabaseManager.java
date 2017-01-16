package proj.skt.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import proj.skt.config.Config;

public class MysqlDatabaseManager implements DatabaseManager {

	private final String ip;
	private final int port;
	private final String dbName;
	private final String username;
	private final String password;
	private final String connectionUrl;

	public MysqlDatabaseManager() {
		this.ip = Config.getIpaddr();
		this.port = Config.getPort();
		this.username = Config.getUsername();
		this.password = Config.getPassword();
		this.dbName = Config.getDbName();

		// this.connectionUrl = "jdbc:mysql://" + ip + ":" + port + "/" +
		// dbName;
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

	public NS3Data cacheStep1(NS3Data data) throws SQLException {
		String query = "SELECT B.* FROM (SELECT A.*, ABS(? - A.TxDelay) + ABS(? - A.RxDelay) + " + "ABS(? - A.TxJitter) + ABS(? - A.RxJitter) AS EuclideanDistance FROM ns3data A) B "
				+ "ORDER BY B.EuclideanDistance LIMIT 1";
		Connection conn = getConnection();

		PreparedStatement pstmt = conn.prepareStatement(query);

		pstmt.setDouble(1, data.getTxDelay());
		pstmt.setDouble(2, data.getRxDelay());
		pstmt.setDouble(3, data.getTxJitter());
		pstmt.setDouble(4, data.getRxJitter());
		

		ResultSet rs = pstmt.executeQuery();

		if (!rs.next())
			return null;

		double txLoss = rs.getDouble("TxLoss");
		double txDelay = rs.getDouble("TxDelay");
		double txJitter = rs.getDouble("TxJitter");

		double rxLoss = rs.getDouble("RxLoss");
		double rxDelay = rs.getDouble("RxDelay");
		double rxJitter = rs.getDouble("RxJitter");
		double throughput = rs.getDouble("Throughput");

		conn.close();
		
		return new NS3Data(txLoss, txDelay, txJitter, rxLoss, rxDelay, rxJitter, throughput);

	}

	public NS3Data cacheStep2(NS3Data data) throws SQLException {
		String query = "SELECT B.* FROM (SELECT A.*, ABS(? - A.TxDelay) + ABS(? - A.RxDelay) + " + "ABS(? - A.TxJitter) + ABS(? - A.RxJitter) AS EuclideanDistance FROM ns3data A) B "
				+ "WHERE ? > B.TxDelay AND ? > B.RxDelay AND ? > B.TxJitter AND ? > B.RxJitter ORDER BY  B.EuclideanDistance LIMIT 1";
		Connection conn = getConnection();

		PreparedStatement pstmt = conn.prepareStatement(query);

		pstmt.setDouble(1, data.getTxDelay());
		pstmt.setDouble(2, data.getRxDelay());
		pstmt.setDouble(3, data.getTxJitter());
		pstmt.setDouble(4, data.getRxJitter());
		pstmt.setDouble(5, data.getTxDelay());
		pstmt.setDouble(6, data.getRxDelay());
		pstmt.setDouble(7, data.getTxJitter());
		pstmt.setDouble(8, data.getRxJitter());

		ResultSet rs = pstmt.executeQuery();

		if (!rs.next())
			return null;

		double txLoss = rs.getDouble("TxLoss");
		double txDelay = rs.getDouble("TxDelay");
		double txJitter = rs.getDouble("TxJitter");

		double rxLoss = rs.getDouble("RxLoss");
		double rxDelay = rs.getDouble("RxDelay");
		double rxJitter = rs.getDouble("RxJitter");
		double throughput = rs.getDouble("Throughput");

		conn.close();
		return new NS3Data(txLoss, txDelay, txJitter, rxLoss, rxDelay, rxJitter, throughput);
	}

	public NS3Data cacheStep3(NS3Data data) {
		return null;
	}

	public NS3Data cacheQuery(int step, NS3Data data) throws SQLException {

		// SELECT B.* FROM (SELECT A.*, SQRT(POW('1500' - A.TotalDelay, 2) +
		// POW('100' - A.TotalJitter, 2) + POW('0' - A.TxLoss, 2) + POW('0' -
		// A.RxLoss, 2)) AS EuclideanDistance FROM ns3data A) B WHERE '1500' >
		// B.TotalDelay AND '100' > B.TotalJitter ORDER BY B.EuclideanDistance
		// LIMIT 1;
		
		switch (step) {
		case 1:
			return cacheStep1(data);
		case 2:
			return cacheStep2(data);
		case 3:
			return cacheStep3(data);
		default:
			return null;
		}

	}

	public List<NS3Data> fetchData() {
		List<NS3Data> dataList = new ArrayList<NS3Data>();

		try {
			// get connection from the connection pool.
			Connection conn = getConnection();

			// --------------------- 완성된 쿼리 형태 예시 ------------------

			// SELECT B.* FROM (SELECT A.*, SQRT(ABS('1500' - A.TotalDelay, 2) +
			// ABS('100' - A.TotalJitter, 2) + ABS('0' - A.TxLoss, 2) + ABS('0'
			// - A.RxLoss, 2)) AS EuclideanDistance FROM ns3data A) B WHERE
			// '1500' > B.TotalDelay AND '100' > B.TotalJitter ORDER BY
			// B.EuclideanDistance LIMIT 1;

			// String query = "SELECT B.* FROM (SELECT A.*, ABS(? -
			// A.TotalDelay)"
			// + " + ABS(? - A.TotalJitter) + ABS(? - A.TxLoss) + "
			// + "ABS(? - A.RxLoss) AS EuclideanDistance FROM ns3data A) B WHERE
			// ? "
			// + "> B.TotalDelay AND ? > B.TotalJitter AND ? > B.TxLoss AND ? >
			// B.RxLoss ORDER BY B.EuclideanDistance LIMIT 1; ";

			String query = "SELECT * FROM ns3data";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// double txLoss, double txDelay, double txJitter, double rxLoss,
			// double rxDelay, double rxJitter
			// TxDelay, TxLoss, TxJitter, RxDelay, RxLoss, RxJitter, Throughput
			while (rs.next()) {
				double txLoss = rs.getDouble("TxLoss");
				double txDelay = rs.getDouble("TxDelay");
				double txJitter = rs.getDouble("TxJitter");

				double rxLoss = rs.getDouble("RxLoss");
				double rxDelay = rs.getDouble("RxDelay");
				double rxJitter = rs.getDouble("RxJitter");
				double throughput = rs.getDouble("Throughput");

				dataList.add(new NS3Data(txLoss, txDelay, txJitter, rxLoss, rxDelay, rxJitter, throughput));

			}

			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return dataList;
	}

	public void updateData(NS3Data dataObj) {
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
