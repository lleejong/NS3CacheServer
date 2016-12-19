package com.choilab.proj.skt.cache;

import java.util.HashMap;
import java.util.List;

import com.choilab.proj.skt.ServerConfigure;
import com.choilab.proj.skt.core.FileLogger;
import com.choilab.proj.skt.database.DatabaseManager;
import com.choilab.proj.skt.database.MysqlDatabaseManager;
import com.choilab.proj.skt.database.NS3Data;

public class SimpleCacheManager implements CacheManager {

	private static final SimpleCacheManager instance = new SimpleCacheManager();

	private static final double th = 100.0;

	public static SimpleCacheManager getInstance() {
		return SimpleCacheManager.instance;
	}

	private DatabaseManager dbManager;

	private SimpleCacheManager() {
		dbManager = MysqlDatabaseManager.getInstance();
	}

	public NS3Data isHit(NS3Data obj) {
		if (!ServerConfigure.isCache)
			return null;

		NS3Data isHitData = dbManager.cacheQuery(obj);

		FileLogger.newLine("isHitData != NULL : " + (isHitData != null));
		
		if (isHitData != null) {
			double distance = Math.abs((isHitData.getTxDelay() + isHitData.getRxDelay()) - (obj.getTxDelay() + obj.getRxDelay()))
					+ Math.abs((isHitData.getTxJitter() + isHitData.getRxJitter()) - (obj.getTxJitter() + obj.getRxJitter())) + Math.abs((isHitData.getTxLoss() - obj.getTxLoss()))
					+ Math.abs((isHitData.getRxLoss() - obj.getRxLoss()));

			FileLogger.newLine("isHitData :" + isHitData.toStringWithTagName() +"\n" + "input :" + obj.toStringWithTagName() + "\n" + "distance : " + distance + "\n");
			if (distance < th)
				return isHitData;
			else
				return null;
		} else
			return null;
		// return cache.get(obj.toString());
	}

	public void update(NS3Data obj) {
		dbManager.updateData(obj);
	}

}
