package com.choilab.proj.skt.cache;

import java.util.HashMap;
import java.util.List;

import com.choilab.proj.skt.database.DatabaseManager;
import com.choilab.proj.skt.database.MysqlDatabaseManager;
import com.choilab.proj.skt.database.NS3Data;

public class SimpleCacheManager implements CacheManager{

	private static final SimpleCacheManager instance = new SimpleCacheManager();
	
	private HashMap<String,NS3Data> cache = new HashMap<String,NS3Data>();
	
	public static SimpleCacheManager getInstance() {
		return SimpleCacheManager.instance;
	}
	
	private DatabaseManager dbManager;
	
	private SimpleCacheManager(){
		dbManager = MysqlDatabaseManager.getInstance();
		init();
	}
	
	private void init(){
		List<NS3Data> list = dbManager.fetchData();
		
		for(NS3Data obj : list){
			cache.put(obj.toString(), obj);
		}
	}
	public NS3Data isHit(NS3Data obj) {
		return null;
	}

	public void update(NS3Data obj) {
		
	}
	
}
