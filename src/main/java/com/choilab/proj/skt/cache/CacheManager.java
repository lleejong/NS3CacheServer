package com.choilab.proj.skt.cache;

import com.choilab.proj.skt.database.NS3Data;

public interface CacheManager {
	
	public NS3Data isHit(NS3Data obj);
	public void update(NS3Data obj);
	
}


