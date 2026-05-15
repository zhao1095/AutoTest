package com.courese.httpclient.demo;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.Test;

import java.io.IOException;

public class MyHttpClient {
    @Test
    public  void test1() throws IOException {// 定义测试方法，声明抛出IO异常以处理网络错误

        //用来存放结果
        String result;
        HttpGet get= new HttpGet("http://www.baidu.com");
        //用来执行get方法的
        // 3. 创建客户端：使用工厂方法创建默认的CloseableHttpClient实例
        CloseableHttpClient client = HttpClients.createDefault();
        // 4. 执行请求：客户端执行GET请求，并返回服务器响应对象
        CloseableHttpResponse response=client.execute(get);
        // 5. 处理响应：使用EntityUtils将响应实体转换为字符串，并指定编码格式为UTF-8
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
    }
}
