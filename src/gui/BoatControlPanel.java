/**
 * 
 */
package gui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import datareceiver.AbstractDataConnector;
import datareceiver.SerialDataConnector;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (24 Aug 2013)
 */
public class BoatControlPanel extends JPanel implements ActionListener{
	private JButton sendWaypointsButton;
	
	public BoatControlPanel(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		sendWaypointsButton = new JButton("SEND WPS TO BOAT");
		sendWaypointsButton.addActionListener(this);
		this.add(sendWaypointsButton);
	}

	@Override
	public void actionPerformed(ActionEvent arg0){
		if(arg0.getSource()==this.sendWaypointsButton){
			RobotManagerFrame frame = RobotManagerFrame.getInstance();
			StringBuilder message = new StringBuilder("set");
			for(Coordinate point : frame.getWaypoints().getPoints()){
				message.append(" ").append(point.getLat())
				   	   .append(";").append(point.getLon());
			}
			AbstractDataConnector sender = frame.getActiveDataConnector();
			if(sender != null)
				sender.sendMessage(message.append(SerialDataConnector.END_CHAR).toString());
		}
		
	}
}
