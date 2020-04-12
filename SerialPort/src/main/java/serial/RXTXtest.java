package serial;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.TooManyListenersException;

import entity.SensorData;
import entity.User;
import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import http.HttpRequest;
import window.Win;

public final class RXTXtest {

    private SerialPort serialPort;
    private String dataBuffer = "";
    private String dataBufferLog = "";
    private UploadData uploadData = new UploadData();

    public void open(Win win, String serialPortName, int baudRate, int DATABITS, int PARITY, int STOPBITS) {
        // 开启串口
        serialPort = openSerialPort(serialPortName, baudRate, DATABITS,PARITY, STOPBITS);
    }

    public void listen() {

        new Thread(() -> {
            // 监听串口
            RXTXtest.setListenerToSerialPort(serialPort, serialPortEvent -> {
                if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                    byte[] bytes = RXTXtest.readData(serialPort);
                    dataBuffer += new String(bytes);
                    dataBufferLog += new String(bytes);
                    try {
                        dataBuffer = uploadData.checkStringAndUpload(dataBuffer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }).start();
    }


    // 开线程轮询
    public void loopQuery() {

        new Thread(() -> {
            String str = "a";
            byte[] bytes = str.getBytes();
            while (true) {
                RXTXtest.sendData(serialPort, bytes);//发送数据
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**获得系统可用的端口名称列表*/
    public static List<String> getSystemPort(){
        List<String> systemPorts = new ArrayList<>();
        //获得系统可用的端口
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();
        while(portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();//获得端口的名字
            systemPorts.add(portName);
        }
        return systemPorts;
    }

    /**开启串口*/
    static SerialPort openSerialPort(String serialPortName, int baudRate, int DATABITS, int PARITY, int STOPBITS) {
        try {
            //通过端口名称得到端口
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(serialPortName);
            //打开端口，（自定义名字，打开超时时间）
            CommPort commPort = portIdentifier.open(serialPortName, 2222);
            //判断是不是串口
            if (commPort instanceof SerialPort) {
                SerialPort serialPort = (SerialPort) commPort;
                //设置串口参数（波特率，数据位8，停止位1，校验位无）
                serialPort.setSerialPortParams(baudRate, DATABITS, STOPBITS, PARITY);
                //serialPort.setSerialPortParams(baudRate, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
                System.out.println("开启串口成功，串口名称："+serialPortName);
                return serialPort;
            }
            else {
                //是其他类型的端口
                throw new NoSuchPortException();
            }
        } catch (NoSuchPortException | UnsupportedCommOperationException | PortInUseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**关闭串口*/
    public static void closeSerialPort(SerialPort serialPort) {
        if(serialPort != null) {
            serialPort.close();
            System.out.println("关闭了串口："+serialPort.getName());
            serialPort = null;
        }
    }

    /**向串口发送数据*/
    private static void sendData(SerialPort serialPort, byte[] data) {
        OutputStream os = null;
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        try {
            os = serialPort.getOutputStream();//获得串口的输出流
            os.write(data);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                    os = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**从串口读取数据*/
    private static byte[] readData(SerialPort serialPort) {
        InputStream in = null;
        byte[] bytes = {};
        try {
            Thread.sleep(50);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        try {
            in = serialPort.getInputStream();
            int bufflenth = in.available(); // 获取buffer里的数据长度
            while (bufflenth != 0) {
                bytes = new byte[bufflenth]; // 初始化byte数组为buffer中数据的长度
                in.read(bytes);
                bufflenth = in.available();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    /**给串口设置监听*/
    private static void setListenerToSerialPort(SerialPort serialPort, SerialPortEventListener listener) {
        try {
            //给串口添加事件监听
            serialPort.addEventListener(listener);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
        serialPort.notifyOnDataAvailable(true);//串口有数据监听
        serialPort.notifyOnBreakInterrupt(true);//中断事件监听
    }

    public String getDataBufferLog() {
        return dataBufferLog;
    }

    public UploadData getUploadData() {
        return uploadData;
    }
}