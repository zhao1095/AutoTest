package com.coures.testng.groups;

import org.testng.annotations.AfterGroups;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.Test;

public class GroupsOnMethod {
    @Test(groups = "service")
    public  void group1(){
        System.out.print("这是组测试在方法上的第一个测试111111");
    }
    @Test(groups = "service")
    public  void  group2(){
        System.out.print("这是组测试在方法上的第二个测试2222222");
    }
    @Test(groups = "client")
    public  void group3(){
        System.out.print("这是组测试在方法上的第三个测试333333333");
    }
    @Test(groups = "client")
    public  void group4(){
        System.out.print("这是组测试在方法上的第三个测试44444");
    }
    @BeforeGroups("service")
    public  void  BeforeGroupsOnService(){
        System.out.print("这是组测试1运行之前运行的方法");
    }
    @ AfterGroups("service")
    public  void  AfterGroupsOnService(){
        System.out.print("这是组测试2运行之后运行的方法");
    }
}
