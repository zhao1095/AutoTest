package com.coures.testng;

import org.testng.annotations.Test;

public class DependTest {

    @Test
    public  void test1(){
        System.out.print("这是测试1，，Running");
    }
    @Test(dependsOnMethods = {"test1"})
    public  void test2(){
        System.out.print("这是测试2，，Running");
    }

    /**
     * 如果被依赖的方法异常，依赖的方法会被忽略
     * 例如：以下代码执行
     */

    @Test
    public  void test3(){
        System.out.print("这是测试3，，Running");
        throw new RuntimeException();
    }
    @Test(dependsOnMethods = {"test3"})
    public  void test4(){
        System.out.print("这是测试4，，Running");
    }
}
