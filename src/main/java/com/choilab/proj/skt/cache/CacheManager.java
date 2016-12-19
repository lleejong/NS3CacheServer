package com.choilab.proj.skt.cache;

import java.sql.SQLException;

import com.choilab.proj.skt.database.NS3Data;

public interface CacheManager {
	
	public NS3Data isHit(NS3Data obj) throws SQLException;
	public void update(NS3Data obj);
	
}


