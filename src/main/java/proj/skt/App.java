package proj.skt;

import proj.skt.config.Config;
import proj.skt.core.TaskScheduler;
import proj.skt.database.DatabaseManager;
import proj.skt.database.MysqlDatabaseManager;

public class App {
	public static void main(String[] args) {
		Config.init();
		DatabaseManager dbManager = new MysqlDatabaseManager();
		dbManager.init();
		
		System.out.println("Server Running...");
//		List<NS3Data> list = dbManager.fetchData();
//		for(NS3Data data : list){
//			System.out.println(data.toString());
//		}
		
		
		//args[0] = port
		//args[1] = cache on off --> true or false
		//args[2] = type int 1 2 3
		
		ServerConfigure.isCache = false;
		if(args[1].compareToIgnoreCase("TRUE") == 0){
			ServerConfigure.isCache = true;
		}
		ServerConfigure.port = Integer.parseInt(args[0]);
		
		ServerConfigure.type = Integer.parseInt(args[2]);
		
		TaskScheduler taskScheduler = new TaskScheduler();
		taskScheduler.run();	
	}
}
