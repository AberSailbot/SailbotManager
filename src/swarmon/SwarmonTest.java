package swarmon;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

public class SwarmonTest {

	public static void main(String args[]){
		SwarmonConnector swarmon = new SwarmonConnector("Aber1", "ak98aber1");
		try {
			swarmon.getRealTimeData();
			swarmon.registerRobot("AberSailbot", "testAberRobot");
			
			JSONArray obj = swarmon.getRobots();
			for(int i = 0; i < obj.length(); i++){
				System.out.println(obj.get(i));
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
