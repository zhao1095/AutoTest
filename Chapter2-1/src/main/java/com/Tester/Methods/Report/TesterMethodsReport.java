package com.Tester.Methods.Report;


import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

public class TesterMethodsReport {

    @Test
    public void test1(){
        Assert.assertEquals(1,1);
    }

    @Test
    public void test2(){
        Assert.assertEquals(1,3);
    }

    @Test
    public  void test3(){
        Assert.assertEquals("mmm","mmm");
    }

    @Test
    public  void logs(){
        Reporter.log("这是自己写的日志");
        throw  new RuntimeException("这是日志运行的异常");
    }
}
