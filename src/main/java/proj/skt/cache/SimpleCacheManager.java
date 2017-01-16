package proj.skt.cache;

import java.sql.SQLException;

import proj.skt.ServerConfigure;
import proj.skt.core.FileLogger;
import proj.skt.database.DatabaseManager;
import proj.skt.database.MysqlDatabaseManager;
import proj.skt.database.NS3Data;

public class SimpleCacheManager implements CacheManager {
	public static int totalCnt = 0;
	public static int hitCnt1 = 0;
	public static int hitCnt2 = 0;
	
	private static final double th = 2.0;
	private static final double sp = 0.0;
	private DatabaseManager dbManager;

	public SimpleCacheManager() {
		dbManager = new MysqlDatabaseManager();
	}

	public NS3Data isHit(NS3Data obj) throws SQLException {
		totalCnt++;
		if (!ServerConfigure.isCache)
			return null;

		int step = 1;

		//step1;
		NS3Data isHitData = dbManager.cacheQuery(step++, obj);
		if (isHitData != null) {

			double distance = Math.abs((isHitData.getTxDelay() + isHitData.getRxDelay()) - (obj.getTxDelay() + obj.getRxDelay()))
					+ Math.abs((isHitData.getTxJitter() + isHitData.getRxJitter()) - (obj.getTxJitter() + obj.getRxJitter())) + Math.abs((isHitData.getTxLoss() - obj.getTxLoss()))
					+ Math.abs((isHitData.getRxLoss() - obj.getRxLoss()));

			if (distance < th){
				hitCnt1++;
				FileLogger.newLine(obj.toStringWithTagName() + "/HIT1/"+distance);
				FileLogger.newLine2(totalCnt,hitCnt1,hitCnt2);
				return isHitData;
			}
		}

		//step2;
		isHitData = dbManager.cacheQuery(step++, obj);
		if (isHitData != null) {
			hitCnt2++;
			FileLogger.newLine(obj.toStringWithTagName() + "/HIT2");
			FileLogger.newLine2(totalCnt,hitCnt1,hitCnt2);
			return isHitData;
		}

		//step3 : 아직 구현 X
		isHitData = dbManager.cacheQuery(step++, obj);
		FileLogger.newLine(obj.toStringWithTagName() + "/MISS");
		FileLogger.newLine2(totalCnt,hitCnt1,hitCnt2);
		return isHitData;

	}

	public void update(NS3Data obj) {
		dbManager.updateData(obj);
	}

}
