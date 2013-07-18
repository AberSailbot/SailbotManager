package datarequester;
import jssc.SerialPort;

public class SerialDataRequester extends Thread{

    char currentCommandChar = 'l';
    String currentData = ""; //Should this be a string?
    SerialPort serialPort;

    public SerialDataRequester(SerialPort serialPort){
        this.serialPort = serialPort;
    }

    public void run(){
        while(true){
            if(currentCommandChar == 'l'){
                serialPort.writeByte((byte)currentCommandChar);
            }
            else{
                serialPort.writeString(currentCommandChar+currentData);
                currentCommandChar = 'l';
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
