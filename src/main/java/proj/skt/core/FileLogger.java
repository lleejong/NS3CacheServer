package proj.skt.core;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileLogger {
	public static void newLine(String line){
		try {
			FileWriter fw = new FileWriter(new File("/hitLog.txt"), true);
			fw.write(line + "\n");
			
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void newLine2(int totalCnt, int hitCnt1, int hitCnt2){
		try {
			FileWriter fw = new FileWriter(new File("/hitRatio.txt"));
			fw.write("Total :" + totalCnt + "\n");
			fw.write("hit1 :" + hitCnt1 + "\n");
			fw.write("hit2 :" + hitCnt2 + "\n");
			fw.write("======================================\n");
			fw.write("Total Hit Ratio :" + (double)(hitCnt1 + hitCnt2)/totalCnt + "\n");
			fw.write("Hit 1 Ratio :" + (double) hitCnt1/totalCnt + "\n");
			fw.write("Hit 2 Ratio :" + (double) hitCnt2/totalCnt + "\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
