/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListDataListener;
import javax.swing.text.html.StyleSheet.ListPainter;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.MapWaypointMarker;

import data.DataSet;
import data.Waypoints;

/**
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 * @version 1.0 (6 May 2013)
 */
public class WaypointsPanel extends JPanel implements MouseListener, ActionListener{
	
	private class WaypointListModel implements ListModel{

		
		public WaypointListModel(){
		}
		
		@Override
		public void addListDataListener(ListDataListener arg0){
			// TODO Auto-generated method stub
			
		}

		@Override
		public Object getElementAt(int arg0){
			return waypoints.getStringRepresentation(arg0);
		}

		@Override
		public int getSize(){
			return waypoints.getPoints().size();
		}

		@Override
		public void removeListDataListener(ListDataListener arg0){
			// TODO Auto-generated method stub
			
		}
		
	}
	
	JPanel listPanel;
	JList wpList = new JList();
	JButton load, save, clear;
	JPanel buttonsPanel, bottomPanel;
	JPanel latLonPanel;
	
	JPanel wpEditPanel;
	JButton up, down, remove;
	JButton addFromPosition;
	
	JTextField lat, lon;
	JButton addWaypoint;
	Waypoints waypoints;
	boolean editable = false;

	/**
	 * 
	 */
	public WaypointsPanel(Waypoints wps){
		super(new BorderLayout());
		this.waypoints = wps;
		
		wpList = new JList(new WaypointListModel());
		listPanel = new JPanel(new BorderLayout());
		listPanel.add(wpList, BorderLayout.CENTER);
		this.add(listPanel, BorderLayout.CENTER);
		
		wpEditPanel = new JPanel(new FlowLayout());
		up = new JButton("UP");
		down = new JButton("DOWN");
		remove = new JButton("REMOVE");
		
		up.addActionListener(this);
		down.addActionListener(this);
		remove.addActionListener(this);
		
		wpEditPanel.add(up);
		wpEditPanel.add(down);
		wpEditPanel.add(remove);
		
		listPanel.add(wpEditPanel, BorderLayout.SOUTH);
		
		save = new JButton(new ImageIcon("png/Save.png"));
		load = new JButton(new ImageIcon("png/Open.png"));
		clear = new JButton(new ImageIcon("png/Clear.png"));
		
		save.setToolTipText("Save waypoints to file");
		load.setToolTipText("Load waypoints from file");
		clear.setToolTipText("Remove all waypoints");
		
		save.addActionListener(this);
		load.addActionListener(this);
		clear.addActionListener(this);
		
		buttonsPanel = new JPanel(new FlowLayout());
		buttonsPanel.add(load);
		buttonsPanel.add(save);
		buttonsPanel.add(clear);
		
		this.add(buttonsPanel, BorderLayout.NORTH);
		
		bottomPanel = new JPanel(new BorderLayout());
		addFromPosition = new JButton("Add current position");
		addFromPosition.addActionListener(this);
		bottomPanel.add(addFromPosition, BorderLayout.NORTH);
		lat = new JTextField();
		lon = new JTextField();
		addWaypoint = new JButton("ADD");
		addWaypoint.addActionListener(this);
		latLonPanel = new JPanel(new GridLayout(2, 2));
		latLonPanel.add(new JLabel("Lat:"));
		latLonPanel.add(lat);
		latLonPanel.add(new JLabel("Lon:"));
		latLonPanel.add(lon);
		bottomPanel.add(latLonPanel, BorderLayout.CENTER);
		bottomPanel.add(addWaypoint, BorderLayout.SOUTH);
		bottomPanel.setBorder(new TitledBorder("Add manually"));
		
		this.add(bottomPanel, BorderLayout.SOUTH);
		
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager()
		  .addKeyEventDispatcher(new KeyEventDispatcher() {
		      @Override
		      public boolean dispatchKeyEvent(KeyEvent e) {
		    	  if(e.getID()==KeyEvent.KEY_PRESSED){
		    		  if(e.getKeyCode()==KeyEvent.VK_CONTROL){
		    			  editable = true;
		    		  }
		    	  }else if(e.getID()==KeyEvent.KEY_RELEASED){
		    		  if(e.getKeyCode()==KeyEvent.VK_CONTROL){
		    			  editable = false;
		    		  }
		    	  }
		        return false;
		      }
		});
		
	}
	
	private void remove(){
		int n = wpList.getSelectedIndex();
		if(n==-1) return;
		this.waypoints.remove(n);
		this.refresh();
	}
	
	private void moveUp(){
		int n = wpList.getSelectedIndex();
		if(n==-1) return;
		if(this.waypoints.moveUp(n)){
			this.refresh();
			this.wpList.setSelectedIndex(n-1);
		}
	}
	
	private void moveDown(){
		int n = wpList.getSelectedIndex();
		if(n==-1) return;
		if(this.waypoints.moveDown(n)){
			this.refresh();
			this.wpList.setSelectedIndex(n+1);
		}
	}
	
	public void refresh(){
		listPanel.remove(wpList);
		wpList = new JList(new WaypointListModel()); //FIXME ugly, ugly way :(
		listPanel.add(wpList, BorderLayout.CENTER);
		refreshMarkers();
		this.validate();
		this.repaint();
	}
	
	public void refreshMarkers(){
		RobotManagerFrame frame = RobotManagerFrame.getInstance();
		frame.getMap().removeAllWaypointMarkers();
		for(int i = 0; i < this.waypoints.getPoints().size(); i++){
			Coordinate c = this.waypoints.getPoints().get(i);
			frame.getMap().addMapMarker(new MapWaypointMarker(c, i));
		}
		frame.getMap().validate();
	}

	@Override
	public void mouseClicked(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0){
	}

	@Override
	public void mouseReleased(MouseEvent arg0){
		if(arg0.getButton()==MouseEvent.BUTTON1 && this.editable){
			//System.out.println(arg0.getPoint());
			RobotManagerFrame frame = RobotManagerFrame.getInstance();
			Coordinate pos = frame.getMap().getPosition(arg0.getPoint());
			//System.out.println(pos);
			this.waypoints.add(pos);
			this.refresh();
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0){
		if(arg0.getSource()==this.save){
			JFileChooser fc = new JFileChooser(".");
			fc.setApproveButtonText("Save");
			fc.setDialogTitle("Save waypoints");
			fc.showOpenDialog(this);
			File f = fc.getSelectedFile();
			if(f==null) return;
			this.waypoints.saveToFile(f);
		}else if(arg0.getSource()==this.load){
			JFileChooser fc = new JFileChooser(".");
			fc.setDialogTitle("Open waypoints file");
			fc.showOpenDialog(this);
			File f = fc.getSelectedFile();
			if(f==null) return;
			this.waypoints.readFromFile(f);
			this.refresh();
		}else if(arg0.getSource()==this.clear){
			this.waypoints.clearList();
			this.refresh();
		}else if(arg0.getSource()==this.addWaypoint){
			double lat, lon;
			try{
				lat = Double.parseDouble(this.lat.getText());
			}catch(NumberFormatException ex){
				this.lat.setBackground(Color.RED);
				return;
			}
			try{
				lon = Double.parseDouble(this.lon.getText());
			}catch(NumberFormatException ex){
				this.lon.setBackground(Color.RED);
				return;
			}
			this.lat.setBackground(Color.WHITE);
			this.lon.setBackground(Color.WHITE);
			this.waypoints.add(new Coordinate(lat, lon));
			this.refresh();
		}else if(arg0.getSource()==this.remove){
			this.remove();
		}else if(arg0.getSource()==this.up){
			this.moveUp();
		}else if(arg0.getSource()==this.down){
			this.moveDown();
		}else if(arg0.getSource()==this.addFromPosition){
			Number lat = DataSet.getInstance().getValueByKey("lat");
			Number lon = DataSet.getInstance().getValueByKey("lon");
			waypoints.add(new Coordinate(Double.parseDouble(lat.toString()), Double.parseDouble(lon.toString())));
			this.refresh();
		}
		
	}
	

}
