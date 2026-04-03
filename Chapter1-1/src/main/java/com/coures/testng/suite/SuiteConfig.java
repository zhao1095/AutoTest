package com.coures.testng.suite;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public class SuiteConfig {

    @BeforeSuite
    public  void  BeforeSuite(){
        System.out.print("BeforeSuite 运行.....");
    }
    @AfterSuite
    public  void  AfterSuite(){
        System.out.print("AfterSuite 运行结束。。。。");
    }
    @BeforeTest
    public void BeforeTest(){
        System.out.print("这是BeforeTest");
    }
    @AfterTest
    public  void AfterTest(){
        System.out.print("这是AfterTest");
    }


}
