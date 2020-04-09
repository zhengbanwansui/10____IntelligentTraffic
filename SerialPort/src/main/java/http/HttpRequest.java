package http;

import com.alibaba.fastjson.JSON;
import entity.User;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


public class HttpRequest {

    public static void postRequest(String url, User user) throws IOException {
        System.out.println("请求URL: " + url + " object转JSON字符串: " + JSON.toJSONString(user));

        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(url);
        String jsonStr = JSON.toJSONString(user);
        StringEntity entity = new StringEntity(jsonStr, "UTF-8");
        httpPost.setEntity(entity);
        httpPost.setHeader("Content-Type", "application/json;charset=utf8");
        // 响应模型
        CloseableHttpResponse response = null;
        // 由客户端执行(发送)Post请求
        response = httpClient.execute(httpPost);
        // 从响应模型中获取响应实体
        HttpEntity responseEntity = response.getEntity();

        System.out.println("响应状态为:" + response.getStatusLine());
        if (responseEntity != null) {
            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
            System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
        }
    }

}
