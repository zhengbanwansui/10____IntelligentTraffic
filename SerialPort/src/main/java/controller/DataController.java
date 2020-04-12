package controller;

import entity.SensorData;
import window.Win;

public class DataController {

    public void updateData(Win win) {

        win.setJTextFields(win.getRxtx().getUploadData().getData());
        win.setJTextArea(win.getRxtx().getDataBufferLog());

    }

}
