/**
 * 
 */
package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToggleButton;

import data.Settings;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (5 May 2013)
 */
public class MenuBar extends JMenuBar implements ActionListener{
	
	private JCheckBoxMenuItem followBoat;

	public MenuBar(){
		super();
		JMenu dataSourceMenu = new JMenu("Data source");
		
		ButtonGroup radio = new ButtonGroup();
		JRadioButtonMenuItem none = new JRadioButtonMenuItem("None");
		none.setActionCommand("none");
		JRadioButtonMenuItem mock = new JRadioButtonMenuItem("Mock");
		mock.setActionCommand("mock");
		JRadioButtonMenuItem serial = new JRadioButtonMenuItem("Serial");
		serial.setActionCommand("serial");
		JRadioButtonMenuItem log = new JRadioButtonMenuItem("Log file");
		log.setActionCommand("logfile");
		radio.add(none);
		radio.add(mock);
		radio.add(serial);
		radio.add(log);
		dataSourceMenu.add(none);
		dataSourceMenu.add(mock);
		dataSourceMenu.add(serial);
		dataSourceMenu.add(log);
		
		none.setSelected(true);
		
		JMenu jumpMenu = new JMenu("Jump on map");
		JMenuItem aber = new JMenuItem("Aberystwyth");
		JMenuItem lake = new JMenuItem("Llyn-yr-oerfa");
		JMenuItem gloucesterHarbour = new JMenuItem("Gloucester Harbor, MA");
		
		aber.addActionListener(this);
		lake.addActionListener(this);
		gloucesterHarbour.addActionListener(this);
		
		jumpMenu.add(aber);
		jumpMenu.add(lake);
		jumpMenu.add(gloucesterHarbour);
		
		mock.addActionListener(this);
		serial.addActionListener(this);
		log.addActionListener(this);
		
		followBoat = new JCheckBoxMenuItem("Follow boat");
		followBoat.setSelected(Settings.getBoolean(Settings.FOLLOW_ROBOT));
		followBoat.addActionListener(this);
		
		this.add(dataSourceMenu);
		this.add(followBoat);
		this.add(jumpMenu);
		this.setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0){
		String message = arg0.getActionCommand();
		switch(message){
		case "none":
		case "mock":
		case "serial":
		case "logfile":
			RobotManagerFrame.getInstance().setDataSource(message);
			break;
		case "Follow boat":
			RobotManagerFrame.getInstance().setFollowBoat(followBoat.isSelected());
			Settings.set(Settings.FOLLOW_ROBOT, followBoat.isSelected());
			break;
		case "Aberystwyth":
			RobotManagerFrame.getInstance().getMap().setDisplayPositionByLatLon(52.41156, -4.08975, 15);
			break;
		case "Llyn-yr-oerfa":
			RobotManagerFrame.getInstance().getMap().setDisplayPositionByLatLon(52.4008, -3.8713, 15);
			break;
		case "Gloucester Harbor, MA":
			RobotManagerFrame.getInstance().getMap().setDisplayPositionByLatLon(42.5976, -70.6675, 14);
			break;
		}
		
	}
}
