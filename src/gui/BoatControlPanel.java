/**
 * 
 */
package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import datareceiver.AbstractDataConnector;
import datareceiver.SerialDataConnector;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (24 Aug 2013)
 */
public class BoatControlPanel extends JPanel implements ActionListener{
	
	private String[] OPERATION_MODES = {"race", "stationkeeping"};
	
	private JButton sendWaypointsButton;
	private JComboBox modeSelector;
	private JSpinner wpSelector;
	private JButton changeWpButton;
	
	
	public BoatControlPanel(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		modeSelector = new JComboBox<>(OPERATION_MODES);
		
		sendWaypointsButton = new JButton("SEND WPS TO BOAT");
		sendWaypointsButton.addActionListener(this);
		
		wpSelector = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
		
		changeWpButton = new JButton("GO TO WP");
		changeWpButton.addActionListener(this);
		
		this.add(modeSelector);
		this.add(sendWaypointsButton);
		
		this.add(new JSeparator());
		
		this.add(wpSelector);
		this.add(changeWpButton);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0){
		RobotManagerFrame frame = RobotManagerFrame.getInstance();
		if(arg0.getSource()==this.sendWaypointsButton){
			StringBuilder message = new StringBuilder("set waypoints");
			//message.append(" ").append(modeSelector.getSelectedItem().toString());
			for(Coordinate point : frame.getWaypoints().getPoints()){
				message.append(" ").append(point.getLat())
				   	   .append(";").append(point.getLon());
			}
			AbstractDataConnector sender = frame.getActiveDataConnector();
			if(sender != null)
				sender.sendMessage(message.toString());
		}else if(arg0.getSource() ==this.changeWpButton){
			int wpNum = (int) this.wpSelector.getValue();
			if(wpNum >= 0 && wpNum < frame.getWaypoints().getPoints().size()){
				AbstractDataConnector sender = frame.getActiveDataConnector();
				if(sender != null)
					sender.sendMessage("set waypoint " + wpNum);
			}else{
				System.out.println("Incorrect waypoint number selected! Cannot send to boat.");
			}
		}
		
	}
}
