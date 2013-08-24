/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.MapBoatIndicator;
import org.openstreetmap.gui.jmapviewer.tilesources.OfflineOsmTileSource;

import data.DataSet;
import data.Settings;
import data.Waypoints;
import datareceiver.AbstractDataConnector;
import datareceiver.LogFileReader;
import datareceiver.MockDataReceiver;
import datareceiver.SerialDataConnector;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class RobotManagerFrame extends JFrame implements WindowListener {

	private static RobotManagerFrame instance;
	
	JMapViewer map;
	TelemetryDataPanel tp;
	JTabbedPane tabbedPanel;
	JPanel sidePanel;
	LogReaderPanel controller;
	DataSet dataSet;
	
	MapBoatIndicator boatMarker;
	Waypoints waypoints = new Waypoints();
	WaypointsPanel wpPanel = new WaypointsPanel(waypoints);
	
	boolean followBoat;
	
	AbstractDataConnector activeDataConnector;
	LogFileReader logFileReader;
	

	public static RobotManagerFrame getInstance(){
		if(instance==null) instance = new RobotManagerFrame();
		return instance;
	}
	
	private RobotManagerFrame(){
		
		Settings.loadFromFile();
		
		this.setSize(700, 500);
		this.setTitle("SailbotManager");
		MenuBar mb = new MenuBar();
		this.setJMenuBar(mb);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		this.dataSet = DataSet.getInstance();
		logFileReader = new LogFileReader(dataSet);
		
		map = new JMapViewer();
		String mapFolderName = Settings.getString(Settings.MAP_FOLDER);
		int minZoom = Settings.getInteger(Settings.MAP_MIN_ZOOM);
		int maxZoom = Settings.getInteger(Settings.MAP_MAX_ZOOM);
		File mapFolder = new File(mapFolderName);
		map.setTileSource(new OfflineOsmTileSource("file://"
				+ mapFolder.getAbsolutePath(), minZoom, maxZoom));
		
		//map.setDisplayPositionByLatLon(52.41156, -4.08975, 15); // Aber
		//map.setDisplayPositionByLatLon(52.4008, -3.8713, 15); //llyn-yr-oerfa
		double lat = Settings.getDouble(Settings.MAP_LAT);
		double lon = Settings.getDouble(Settings.MAP_LON);
		map.setDisplayPositionByLatLon(lat, lon, minZoom);
		
		boatMarker = new MapBoatIndicator(lat, lon);
		map.addMapMarker(boatMarker);
		followBoat = Settings.getBoolean(Settings.FOLLOW_ROBOT);
		
		
		String[] ignoredData = {"time"};
		tp = new TelemetryDataPanel(6,2, Arrays.asList(ignoredData));
		
		tabbedPanel = new JTabbedPane();
		tabbedPanel.addTab("Telemetry", tp);
		tabbedPanel.addTab("Waypoints", wpPanel);
		
		sidePanel = new JPanel(new BorderLayout());
		sidePanel.add(tabbedPanel, BorderLayout.CENTER);
		
		controller = new LogReaderPanel(logFileReader);
		sidePanel.add(controller, BorderLayout.SOUTH);
		controller.setVisible(false);
		logFileReader.setController(controller);
		
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(sidePanel, BorderLayout.WEST);
		
		JPanel centerPanel = new JPanel(new BorderLayout());
		centerPanel.add(new BoatControlPanel(), BorderLayout.SOUTH);
		centerPanel.add(map, BorderLayout.CENTER);
		
		this.getContentPane().add(centerPanel, BorderLayout.CENTER);
		
		map.addMouseListener(this.wpPanel);
		this.setVisible(true);
		this.addWindowListener(this);
		
		try{
			BoatIndicators.loadImages();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		}
	
	public void run(){
		try{
		
			
		while(true){
			if(this.activeDataConnector!=null){
				activeDataConnector.updateDataSet();
				tp.updatePanel();
				this.updateBoatPosition();
				this.invalidate();
			}
			Thread.sleep(1000);			
		}
		
		
		}catch(InterruptedException ex){
			ex.printStackTrace();
		}
	}
	
	public void setDataSource(String dataSource){
		switch(dataSource){
		case "none":
			this.activeDataConnector = null;
			this.controller.setVisible(false);
			break;
		case "mock":
			this.activeDataConnector = new MockDataReceiver(dataSet);
			this.controller.setVisible(false);
			break;
		case "serial":
			String portName = Settings.getString(Settings.SERIAL_PORT);
			this.activeDataConnector = new SerialDataConnector(dataSet, portName);
			this.controller.setVisible(false);
			break;
		case "logfile":
			this.activeDataConnector = this.logFileReader;
			this.controller.refresh();
			this.controller.setVisible(true);
		}
	}
	
	private void updateBoatPosition(){
		try{
			BoatIndicators.update( dataSet.getValueByKey("wind"), 
					dataSet.getValueByKey("bhead"), dataSet.getValueByKey("whead"), 120);
			boatMarker.update();
			if(followBoat) map.setDisplayPositionByLatLon(
					boatMarker.getLat(), boatMarker.getLon(), map.getZoom());
			map.repaint();
		}catch(Exception ex){
			//ex.printStackTrace();
		}
	}

	public boolean isFollowBoat(){
		return followBoat;
	}

	public void setFollowBoat(boolean followBoat){
		this.followBoat = followBoat;
	}

	public JMapViewer getMap(){
		return map;
	}

	@Override
	public void windowActivated(WindowEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0){
		Settings.saveToFile();
	}

	@Override
	public void windowDeactivated(WindowEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0){
		// TODO Auto-generated method stub
		
	}

	public Waypoints getWaypoints(){
		return waypoints;
	}

	public void setWaypoints(Waypoints waypoints){
		this.waypoints = waypoints;
	}

	public AbstractDataConnector getActiveDataConnector(){
		return activeDataConnector;
	}

	public void setActiveDataConnector(AbstractDataConnector activeDataConnector){
		this.activeDataConnector = activeDataConnector;
	}

}
