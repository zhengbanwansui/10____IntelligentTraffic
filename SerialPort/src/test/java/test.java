import entity.SensorData;
import http.HttpRequest;
import utils.FilePath;

import java.io.IOException;

public class test {
    public static void main(String[] args) throws IOException {
        HttpRequest.postRequest("http://localhost:8080/data"
                , new SensorData(1,1,1,1,1,1));
    }
}
