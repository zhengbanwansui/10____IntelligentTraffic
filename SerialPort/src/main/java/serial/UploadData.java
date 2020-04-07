package serial;

public class UploadData {

    // 字符串格式: 温度#湿度#气压#光照#距离%
    // 如果没有%则不取字符串
    // 如果有%取到%为止并上传
    String checkStringAndUpload(String str) {
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
        }
        return str;
    }

}
