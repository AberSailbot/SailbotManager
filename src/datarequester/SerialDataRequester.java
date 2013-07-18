import jssc.SerialPort;

public class SerialDataRequester{

    char currentCommandChar = 'l';
    string? currentData = "";
    SerialPort serialPort;

    public SerialDataRequester(SerialPort serialPort){
        this.serialPort = serialPort;
    }

    public void run(){
        while(true){
            if(currentCommandCharacter == 'l'){
                serialPort.writeByte((byte)currentCommandChar);
            }
            else{
                serialPort.writeString(currentCommandChar+currentData);
                currentCommandCharacter = 'l';
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
