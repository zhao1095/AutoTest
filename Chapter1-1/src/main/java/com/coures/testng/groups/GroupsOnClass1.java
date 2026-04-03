package com.coures.testng.groups;

import org.testng.annotations.Test;

@Test(groups = "Student")
public class GroupsOnClass1 {
    public void Student1(){
        System.out.print("这是GroupsOnClass1中的Student1运行结果,111111");
    }
    public void Student2(){
        System.out.print("这是GroupsOnClass1中的Student2运行结果,222222");
    }
}
