package com.coures.testng;

import org.testng.annotations.Test;

public class TimeOutTest {
    @Test(timeOut = 3000) //单位为毫秒
    public  void testSuccess() throws InterruptedException {
        Thread.sleep(2000);
        System.out.print("未超时,继续执行.....");
    }
    @Test(timeOut = 3000)
    public  void  testFailed() throws InterruptedException {
        Thread.sleep(5000);
        System.out.print("超时,跳过执行下一条.....");
    }
}
