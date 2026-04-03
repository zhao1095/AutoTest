package com.coures.testng.MultiThread;

import org.testng.annotations.Test;

/**
 * 多线程测试XML文件,这一部分还需要多理解,目前理解不太多,之后需要再多看学习理解并运用
 */


public class MultiThreadOnXml {
    @Test
    public  void  test1(){
        System.out.printf("Thread Id : %S%n", Thread.currentThread().getId());
    }
    @Test
    public  void  test2(){
        System.out.printf("Thread Id : %S%n", Thread.currentThread().getId());
    }
    @Test
    public  void  test3(){
        System.out.printf("Thread Id : %S%n", Thread.currentThread().getId());
    }
}
