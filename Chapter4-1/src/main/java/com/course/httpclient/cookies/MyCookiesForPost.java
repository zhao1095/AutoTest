package com.course.httpclient.cookies;

import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class MyCookiesForPost {
    private String url;
    private ResourceBundle bundle;

    @BeforeTest
    public void beforeTest() {
        bundle = ResourceBundle.getBundle("application", Locale.CHINA);
        url = bundle.getString("test.url");
    }

    @Test
    public void testGetCookies() throws IOException {
        String result;
        System.out.println("开始测试");
        //从配置文加中，拼接测试的URL
        String uri = bundle.getString("getCookies.uri");
        String testUrl = this.url + uri;

        //测试逻辑代码书写
        HttpGet get = new HttpGet(testUrl);
        //创建空的cookiesStore去获取cookie中的key value值
        CookieStore cookieStore = new BasicCookieStore();
        CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = client.execute(get);
        result = EntityUtils.toString(response.getEntity(), "utf-8");
        System.out.println(result);
        System.out.println("测试结束");

        //获取Cookies信息
        List<Cookie> CookieList = cookieStore.getCookies();

        for (Cookie cookie : CookieList) {
            String name = cookie.getName();
            String value = cookie.getValue();
            System.out.println("cookie name" + name + ";  cookie value " + value);
        }


    }

    @Test(dependsOnMethods = {"testGetCookies"})
    public  void  testPostWithCookies() throws IOException {
        String uri = bundle.getString("test.post.with.cookies");
        String testUrl = this.url+uri;
//        System.out.println(testUrl); //测试代码运行时,查找问题运行
        //声明一个client对象,用于进行方 法的执行
        CookieStore store = new BasicCookieStore();
        CloseableHttpClient client= HttpClientBuilder.create().setDefaultCookieStore(store).build();


        BasicClientCookie cookie = new BasicClientCookie("login","true");
        cookie.setDomain("localhost"); // 设置域名
        //cookie.setPath("/"); // 设置路径
        // 2. 把 Cookie 添加到 Store 中
        store.addCookie(cookie);


//        CloseableHttpClient client = HttpClients.createDefault();
        //声明一个方法,这个方法是Post方法
        HttpPost post = new HttpPost(testUrl);
        //设置请求头信息,设置Header信息
        post.setHeader("Content-type","application/json");
        //添加参数
        JSONObject param = new JSONObject();
        param.put("name","guang");
        param.put("age","20");
        //将参数信息添加到方法中
        StringEntity entity = new StringEntity(param.toString(),"utf-8");
        //参数和对象绑定
        post.setEntity(entity);
//        System.out.println("发送的请求数据" + post.toString());//测试代码运行时,查找问题运行
        //声明一个对象来进行对响应结果的存储
        String result;
//        //设置cookies信息
//        client.setCookieStore(store);
        //执行post方法
        HttpResponse response = client.execute(post);
        //获取响应结果
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        System.out.println(result);
//        System.out.println("服务器返回内容"+ result);//测试代码运行时,查找问题运行
        //处理结果,判断结果是否符合预期
        //1.将返回的响应结果字符串转换为JSon对象
        JSONObject resultjson = new JSONObject(result);

        //获取结果值
        String success = (String) resultjson.get("guang");
        String status = (String) resultjson.get("status");
        //2.具体判断返回结果的值
        Assert.assertEquals("NO",success);
        Assert.assertEquals("bye",status);

        }

}
