package serial;

import entity.SensorData;
import entity.User;
import http.HttpRequest;

import java.io.IOException;

/**
 * 字符串格式: 温度#湿度#气压#光照#距离% 80#26#1013#340#44
 * 如果没有%则不取字符串
 * 如果有%取到%为止并上传
 */
public class UploadData {

    int temperature;
    int humidness;
    int pressure;
    int light;
    int distance;

    String checkStringAndUpload(String str) throws IOException {
        if (str.contains("%")) {
            StringBuilder info = new StringBuilder("");
            StringBuilder temp = new StringBuilder("");
            boolean foundKey = false;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '%') {
                    foundKey = true;
                } else if (!foundKey) {
                    info.append(str.charAt(i));
                } else {
                    temp.append(str.charAt(i));
                }
            }
            str = temp.toString();
            System.out.println("获取传感器: " + info);
            String[] strs = info.toString().split("#");
            temperature = Integer.parseInt(strs[0]);
            humidness = Integer.parseInt(strs[1]);
            pressure = Integer.parseInt(strs[2]);
            light = Integer.parseInt(strs[3]);
            distance = Integer.parseInt(strs[4]);
            // 需要修改的http请求位置
            SensorData data = new SensorData(1,temperature,humidness,pressure,light,distance);
            HttpRequest.postRequest("http://localhost:8080/data", data);
        }
        return str;
    }

    @Override
    public String toString() {
        return "UploadData{" +
                "temperature=" + temperature +
                ", humidness=" + humidness +
                ", pressure=" + pressure +
                ", light=" + light +
                ", distance=" + distance +
                '}';
    }
}
