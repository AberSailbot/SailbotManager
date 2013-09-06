package gui;

import data.DataSet;

public class RunSailbotManager {

	public static void main(String args[]){
		
		DataSet set = DataSet.getInstance();
		
		set.addDataCell("lat", "Lattitude")
		.addDataCell("lon", "Longitiude")
		.addDataCell("speed", "Speed")
		.addDataCell("bhead", "Boat heading")
		.addDataCell("targetheading", "Target heading")
		.addDataCell("targetdistance", "Target distance")
		.addDataCell("whead", "Waypoint heading")
		.addDataCell("distance", "Waypoint distance")
		.addDataCell("wind", "Wind direction")
		.addDataCell("spos", "Sail position")
		.addDataCell("rpos", "Rudder position")
		.addDataCell("nwn", "Next waypoint number")
		.addDataCell("nwlat", "Next WP lattitude")
		.addDataCell("nwlon", "Next WP longitiude")
		.addDataCell("temp", "Temperature")
		.addDataCell("time", "Timestamp")
		.addDataCell("status", "Status");
		
		
		RobotManagerFrame frame = RobotManagerFrame.getInstance();
		frame.run();
	}
	
}
