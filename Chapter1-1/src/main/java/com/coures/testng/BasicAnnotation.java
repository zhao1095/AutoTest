package com.coures.testng;

import org.testng.annotations.*;

public class BasicAnnotation {
    //最基本的注解，用来把方法标记为测试的一部分
    @Test
    public void  testCase1(){
        System.out.print("这是测试用例1");
    }

    @Test
    public  void  testCase2(){
        System.out.print("这是测试用例2");
    }

    //这是在执行方法之前运行的；类下的每个方法都会执行一次
    @BeforeMethod
    public void  beforeMethod(){
        System.out.print("BeforeMethod这是测试方法之前运行");
    }

    @AfterMethod
    public  void  AfterMethod(){
        System.out.print("AfterMethod这是测试方法之后运行");
    }
    //这是在执行类之前运行的，
    @BeforeClass
    public  void BeforeClass(){
        System.out.print("这是在类运行前运行的--beforeClass");
    }

    @AfterClass
    public  void AfterClass(){
        System.out.print("这是在类运行之后运行的----AfterClass");
    }

    //这是在类运行之前执行，=且在BeforeClass之前执行
    @BeforeSuite
    public  void BeforeSuite (){
        System.out.print("这是测试套件---BeforeSuite");
    }

    @AfterSuite
    public  void  AfterSuite(){
        System.out.print("这是测试套件----AfterSuite");
    }
}
