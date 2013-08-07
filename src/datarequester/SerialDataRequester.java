package datarequester;
import jssc.SerialPort;

public class SerialDataRequester extends Thread{

    String currentCommand = "get_log;
    String currentData = "";
    SerialPort serialPort;

    public SerialDataRequester(SerialPort serialPort){
        this.serialPort = serialPort;
    }

    public void run(){
        while(true){
            if(currentCommand.equals("get_log") || currentCommand.equals("clear_waypoints"){
                serialPort.writeString(currentCommand);
            }
            else{
                serialPort.writeString(currentCommand+" "+currentData);
                currentCommandChar = "get_log";
            }
        }
    }

    public char getCurrentCommandChar(){
        return currentCommandChar;
    }

    public void setCurrentCommandChar(char newCommandChar){
        currentCommandChar = newCommandChar;
    }
}
