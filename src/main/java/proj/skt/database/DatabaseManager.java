package proj.skt.database;

import java.sql.SQLException;
import java.util.List;



public interface DatabaseManager {
	public void init();
	public List<NS3Data> fetchData();
	public void updateData(NS3Data dataObj);
	public NS3Data cacheQuery(int step, NS3Data dataObj) throws SQLException;
}

