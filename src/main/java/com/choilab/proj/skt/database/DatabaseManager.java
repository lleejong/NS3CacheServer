package com.choilab.proj.skt.database;

import java.util.List;



public interface DatabaseManager {
	public void init();
	public List<NS3InputData> fetchData();
	public void updateData(NS3InputData dataObj);
}

