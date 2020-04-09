import entity.User;
import http.HttpRequest;
import serial.RXTXtest;

import java.io.IOException;

public class Starter {

    public static void main(String[] args) throws InterruptedException, IOException {

        RXTXtest rxtx = new RXTXtest();
        rxtx.process();
        
    }

}
