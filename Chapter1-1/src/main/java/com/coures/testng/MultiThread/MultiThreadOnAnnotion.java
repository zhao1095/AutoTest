package com.coures.testng.MultiThread;

import org.testng.annotations.Test;

public class MultiThreadOnAnnotion {
    @Test(invocationCount = 10,threadPoolSize = 3)
    public  void test(){
        System.out.println(1);
        System.out.printf("Thread Id : %S%n", Thread.currentThread().getId());

    }
}
