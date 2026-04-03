package com.coures.testng;

import org.testng.annotations.Test;

public class IgnoreTest {
    @Test
    public  void  Ignore1(){
        System.out.print("这是忽略测试---显示会执行");
    }
    @Test(enabled = false)
    public  void  Tgnore2(){
        System.out.print("这是忽略测试2----忽略---不执行");
    }
    @Test(enabled = true)
    public  void  Ignore3(){
        System.out.print( "这是忽略测试3---忽略----执行");
    }
}
