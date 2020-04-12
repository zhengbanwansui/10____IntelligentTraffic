package window;

import entity.SensorData;
import gnu.io.SerialPort;
import serial.RXTXtest;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import static serial.RXTXtest.getSystemPort;

public class Win extends JFrame implements ActionListener {

    private RXTXtest rxtx;

    public JComboBox<String> jComboBox1 = new JComboBox<>();
    public JComboBox<String> jComboBox2 = new JComboBox<>();
    public JComboBox<String> jComboBox3 = new JComboBox<>();
    public JComboBox<String> jComboBox4 = new JComboBox<>();
    public JComboBox<String> jComboBox5 = new JComboBox<>();
    private JButton openSerial = new JButton("打开串口");
    private JButton startCollect = new JButton("开始采集");
    private JTextField jTextField1 = new JTextField();
    private JTextField jTextField2 = new JTextField();
    private JTextField jTextField3 = new JTextField();
    private JTextField jTextField4 = new JTextField();
    private JTextField jTextField5 = new JTextField();
    private JTextArea jTextArea = new JTextArea();
    private JTextField clock = new JTextField();

    public Win() {
        rxtx = new RXTXtest();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(200,200,900,500);
        BGJPanel rootPanel = new BGJPanel("back.png");
        this.add(rootPanel);
        rootPanel.setLayout(null);
        System.out.println("可用端口: " + getSystemPort());
        for (String comString : getSystemPort()) {
            jComboBox1.addItem(comString);
        }
        jComboBox2.addItem("9600");
        jComboBox2.addItem("19200");
        jComboBox2.addItem("38400");
        jComboBox3.addItem("8");
        jComboBox3.addItem("7");
        jComboBox3.addItem("6");
        jComboBox3.addItem("5");



        jComboBox4.addItem("None");
        jComboBox4.addItem("Odd");
        jComboBox4.addItem("Even");
        jComboBox5.addItem("1");
        jComboBox5.addItem("2");
        jComboBox5.addItem("1.5");
        jComboBox1.setBounds(50,50,100,40);
        jComboBox2.setBounds(50,100,100,40);
        jComboBox3.setBounds(50,150,100,40);
        jComboBox4.setBounds(50,200,100,40);
        jComboBox5.setBounds(50,250,100,40);
        rootPanel.add(jComboBox1);
        rootPanel.add(jComboBox2);
        rootPanel.add(jComboBox3);
        rootPanel.add(jComboBox4);
        rootPanel.add(jComboBox5);
        openSerial.setBounds(50,300,100,40);
        openSerial.addActionListener(this);
        rootPanel.add(openSerial);
        startCollect.setBounds(50,350,100,40);
        startCollect.setEnabled(false);
        startCollect.addActionListener(this);
        rootPanel.add(startCollect);
        jTextField1.setBounds(200,50,100,30);
        jTextField2.setBounds(200,100,100,30);
        jTextField3.setBounds(200,150,100,30);
        jTextField4.setBounds(200,200,100,30);
        jTextField5.setBounds(200,250,100,30);
        rootPanel.add(jTextField1);
        rootPanel.add(jTextField2);
        rootPanel.add(jTextField3);
        rootPanel.add(jTextField4);
        rootPanel.add(jTextField5);
        jTextArea.setBounds(350, 50, 400, 230);
        jTextArea.setLineWrap(true);
        jTextArea.setWrapStyleWord(true);
        rootPanel.add(jTextArea);
        clock.setBounds(350, 10, 200, 30);
        rootPanel.add(clock);
        setVisible(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Date date = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(date);
                    clock.setText(dateString);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        openSerial.setEnabled(false);
        startCollect.setEnabled(true);
        if (e.getSource() == openSerial) {
            int DATABITS = SerialPort.DATABITS_8;
            int PARITY = SerialPort.PARITY_NONE;
            int STOPBITS = SerialPort.STOPBITS_1;
            switch (jComboBox3.getSelectedItem().toString()) {
                case "5":
                    DATABITS = SerialPort.DATABITS_5;
                    break;
                case "6":
                    DATABITS = SerialPort.DATABITS_6;
                    break;
                case "7":
                    DATABITS = SerialPort.DATABITS_7;
                    break;
                case "8":
                    DATABITS = SerialPort.DATABITS_8;
                    break;
                default:
            }
            switch (jComboBox4.getSelectedItem().toString()) {
                case "None":
                    PARITY = SerialPort.PARITY_NONE;
                    break;
                case "Odd":
                    PARITY = SerialPort.PARITY_ODD;
                    break;
                case "Even":
                    PARITY = SerialPort.PARITY_EVEN;
                    break;
                default:
            }
            switch (jComboBox5.getSelectedItem().toString()) {
                case "1":
                    STOPBITS = SerialPort.STOPBITS_1;
                    break;
                case "2":
                    STOPBITS = SerialPort.STOPBITS_2;
                    break;
                case "1.5":
                    STOPBITS = SerialPort.STOPBITS_1_5;
                    break;
                default:
            }
            rxtx.open(this, jComboBox1.getSelectedItem().toString()
                    , Integer.parseInt(jComboBox2.getSelectedItem().toString())
                    , DATABITS, PARITY, STOPBITS);
            rxtx.listen();
        }
        if (e.getSource() == startCollect) {
            startCollect.setEnabled(false);
            rxtx.loopQuery();
        }
    }

    public void setJTextFields(int a, int b, int c, int d, int e) {
        jTextField1.setText(Integer.toString(a));
        jTextField2.setText(Integer.toString(b));
        jTextField3.setText(Integer.toString(c));
        jTextField4.setText(Integer.toString(d));
        jTextField5.setText(Integer.toString(e));
    }

    public void setJTextFields(SensorData sensorData) {
        jTextField1.setText(Integer.toString(sensorData.getTemperature()));
        jTextField2.setText(Integer.toString(sensorData.getHumidness()));
        jTextField3.setText(Integer.toString(sensorData.getPressure()));
        jTextField4.setText(Integer.toString(sensorData.getLight()));
        jTextField5.setText(Integer.toString(sensorData.getDistance()));
    }

    public void appendJTextArea(String str) {
        jTextArea.append(str);
    }

    public void setJTextArea(String str) {
        jTextArea.setText(str);
    }

    public RXTXtest getRxtx() {
        return rxtx;
    }
}
