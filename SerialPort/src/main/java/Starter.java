import controller.DataController;
import entity.SensorData;
import window.Win;

public class Starter {

    public static void main(String[] args) {

        Win win = new Win();
        DataController dataController = new DataController();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    dataController.updateData(win);
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

    }

}
