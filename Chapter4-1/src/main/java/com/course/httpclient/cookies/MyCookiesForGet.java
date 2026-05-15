package com.course.httpclient.cookies;

import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.*;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpCookie;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForGet {
    private String url;
    private ResourceBundle bundle;

    @BeforeTest
    public  void beforeTest(){
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void  testGetCookies() throws IOException {
        String result;
        System.out.println("开始测试");
        //从配置文加中，拼接测试的URL
        String uri = bundle.getString("getCookies.uri");
        String testUrl = this.url+uri;

        //测试逻辑代码书写
        HttpGet get= new HttpGet(testUrl);
        //创建空的cookiesStore去获取cookie中的key value值
        CookieStore cookieStore =new BasicCookieStore();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
        System.out.println("测试结束");

        //获取Cookies信息
        List <Cookie> CookieList =cookieStore.getCookies();

        for (Cookie cookie: CookieList){
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name" + name  +  ";  cookies value " + value);
        }


    }
    @Test (dependsOnMethods = {"testGetCookies"})
    public void  testGetWithCookies() throws IOException {
        String uri = bundle.getString("test.get.with.cookies");
        String  testUrl = this.url+uri;

        HttpGet get = new HttpGet(testUrl);
        //创建一个基本的cookies存储容器
        CookieStore  store = new BasicCookieStore();
        // 创建 HttpClient 实例，并通过 builder 将上面的 store 设置进去
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(store).build();
//        String cookieKey =bundle.getString("name");
//        String cookieValue = bundle.getString("value");
        String cookieKey = "login";
        String cookieValue = "true";
        BasicClientCookie cookie = new BasicClientCookie(cookieKey,cookieValue);
        //在 Apache HttpClient 中，如果你使用 BasicClientCookie 创建 Cookie，必须设置 Domain（域名），否则 HttpClient 可能会认为这个 Cookie 不匹配当前请求的 URL，从而不发送这个 Cookie
        cookie.setDomain("localhost");
        //设置Cookies信息
        // 将 cookie 加入到 store 中，这样 client 在执行请求时就会自动带上这个 cookie
        store.addCookie(cookie);

        CloseableHttpResponse response = client.execute(get);

        //获取响应状态码
        int  statusCode = response.getStatusLine().getStatusCode();
        System.out.println("StatusCode : " + statusCode);

        if(statusCode ==200 ){

            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }


    }

    @Test
    //使用HTTP 协议中设置 Cookie 的 Header方法获取信息,并返回状态码
    public void  TestGetWhitCookies() throws IOException {
        // 1. 从配置文件(application.properties)中读取接口的相对路径
        // 例如读取到的可能是 "/get/with/cookies"
        String uri1 = bundle.getString("test.get.with.cookies");
        // 2. 拼接完整的请求URL (基础URL + 接口路径)
        String  testUrl = this.url+uri1;
        // 3. 创建一个 HttpGet 请求对象，传入目标 URL
        HttpGet get = new HttpGet(testUrl);
        // 4. 从配置文件中读取 Cookie 的键 (例如: "login")
        String cookieName = bundle.getString("name");
        String cookieValue = bundle.getString("value");
        // 6. 【核心步骤】手动设置请求头(Header)来携带 Cookie
        // 注意：HTTP 协议规定 Cookie 的 Header 名通常固定为 "Cookie"
        // 这里将读取到的键值对拼接成 "key=value" 的格式放入 Header 中
            // 注意：HTTP 协议中设置 Cookie 的 Header 名通常是 "Cookie" (大写 C)
        get.setHeader("Cookie", cookieName + "=" + cookieValue);
        // 7. 创建一个默认的 HttpClient 对象，用于发送请求
        CloseableHttpClient client = HttpClients.createDefault();
        // 8. 执行请求，并将服务器返回的响应结果赋值给 response 对象
        CloseableHttpResponse response = client.execute(get);

        //获取响应状态码
        int  statusCode = response.getStatusLine().getStatusCode();
        System.out.println("StatusCode : " + statusCode);

        if(statusCode ==200 ){

            String result = EntityUtils.toString(response.getEntity(),"utf-8");
            System.out.println(result);
        }


    }
}
