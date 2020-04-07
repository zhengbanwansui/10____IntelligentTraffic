package serial;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

public class ComListener implements SerialPortEventListener {
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        System.out.println("data in");
    }
}
