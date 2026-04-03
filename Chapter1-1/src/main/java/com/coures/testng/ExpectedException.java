package com.coures.testng;

import org.testng.annotations.Test;

import java.util.concurrent.ExecutionException;

public class ExpectedException {
    /**
     * 什什时候会用到异常测试
     * 在我们期望结果为某一个异常时
     * 比如:我们传入不合法的参数，程序输出异常
     * 输出结果就是异常
     */
    //这是一个测试结果失败的异常测试
    @Test(expectedExceptions=RuntimeException.class)
    public  void  runTimeExceptionFailed(){
        System.out.print("这是一个异常测试输出。异常异常异常");
    }
    //这是一个成功的异常测试
    @Test(expectedExceptions = RuntimeException.class)
    public void runTimeExceptionSuccess(){
        System.out.print("这是一个成功的异常测试");
        throw new RuntimeException();
    }
}
