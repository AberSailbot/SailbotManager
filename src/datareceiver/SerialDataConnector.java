package datareceiver;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import data.DataSet;

/**
 * Receives data string through serial port,
 * using java-simple-serial-connector (https://code.google.com/p/java-simple-serial-connector/).
 * 
 * Every string sent by remote device needs to be ended with a '$' sign
 * (therefore '$' must not be contained inside the string).
 * 
 * @author Kamil Mrowiec <kam20@aber.ac.uk>
 *
 */
public class SerialDataConnector extends AbstractDataConnector implements SerialPortEventListener{
	
	/**
	 * End character, appended to each message, used as separator/delimiter.
	 */
	public static char END_CHAR = '$';
	
	SerialPort serialPort;
	String latestData, buffer;
	
	public SerialDataConnector(DataSet dataSet, String port){
		super(dataSet);
		
		serialPort = new SerialPort(port);
		
        try {
            serialPort.openPort();
            serialPort.setParams(115200, 8, 1, 0);
            serialPort.addEventListener(this);
        }
        catch (SerialPortException ex){
            System.out.println(ex);
        }
	}

	@Override
	public String readDataString() {
		return latestData;
	}

	@Override
	public void serialEvent(SerialPortEvent event) {
		
		if(event.isRXCHAR()){
			try {
				
				buffer += serialPort.readString();
				buffer = buffer.replace("\n", "");
				buffer = buffer.replace("\r", "");
				
				int n = buffer.indexOf(END_CHAR);
				if(n != -1){
					latestData = buffer.substring(0, n);
					buffer = buffer.substring(n+1, buffer.length()-1);
				}
			} catch (SerialPortException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	@Override
	public void sendMessage(String message){
		try{
			serialPort.writeString(message + END_CHAR);
		}catch(SerialPortException ex){
			ex.printStackTrace();
		}
	}

}
