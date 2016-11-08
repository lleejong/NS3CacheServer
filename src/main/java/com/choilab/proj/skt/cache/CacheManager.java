package com.choilab.proj.skt.cache;

import com.choilab.proj.skt.database.NS3InputData;
import com.choilab.proj.skt.database.NS3OutputData;

public interface CacheManager {
	
	public NS3OutputData isHit(NS3InputData obj);
	public void update(NS3InputData obj);
	
}


