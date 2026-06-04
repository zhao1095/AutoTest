package com.course.config;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.*;

public class TestConfig {
    public static String loginUrl;
    public static String updateUserInfoUrl;
    public static String getUserListUrl;
    public static String getUserInfoUrl;
    public static String addUserUrl;

    public static  CookieStore  cookiestore = new BasicCookieStore();
    public static CloseableHttpClient closeableHttpClient;
    static{
        closeableHttpClient = HttpClientBuilder.create().setDefaultCookieStore(cookiestore).build();
    }
    public static CookieStore store;
}
