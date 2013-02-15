package datareceiver;
import java.text.NumberFormat;
import java.text.ParseException;

import data.DataSet;

/**
 * Receives a data string from abstract source,
 * formatted as key-value pairs, each separated by space.
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public abstract class AbstractDataReceiver {

	/**
	 * DataSet that will be changed with every 
	 * call to updateDataSet()
	 */
	protected DataSet dataSet;
	
	/**
	 * Contains received data as key-value pairs, 
	 * separated with space.
	 */
	protected String dataString;
	
	/**
	 * Receives data string. Way of achieving that will
	 * depend of implementation, generally either trough serial port or UDP package
	 * @return
	 */
	protected abstract String readDataString();
	
	protected AbstractDataReceiver(DataSet dataSet){
		this.dataSet = dataSet;
		this.dataString = "";
	}
	
	/**
	 * Parses the data string and updates the data set with parsed values.
	 */
	public void updateDataSet(){
		
		this.dataString = this.readDataString();
		if(this.dataString==null){
			System.out.println("Data string is null; cannot update data set");
			return;
		}
		
		String kvps[] = this.dataString.split(" ");
		String buffer[];
		Number value = 0;
		
		for(String s : kvps){
			buffer=s.split("=");
			if(buffer==null || buffer.length!=2) continue; //Data read incorrectly.
			
			try{
				value = NumberFormat.getInstance().parse(buffer[1]);
			}catch(ParseException ex){
				System.out.print("Could not parse "+buffer[1]);
				ex.printStackTrace();
				continue;
			}
			
			this.dataSet.updateDataCell(buffer[0], value);
		}
		
	}
	
	
}
