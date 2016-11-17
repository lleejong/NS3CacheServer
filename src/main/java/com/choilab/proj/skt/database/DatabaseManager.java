package com.choilab.proj.skt.database;

import java.util.List;



public interface DatabaseManager {
	public void init();
	public List<NS3Data> fetchData();
	public void updateData(NS3Data dataObj);
}

