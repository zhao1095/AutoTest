package com.coures.testng.groups;

import org.testng.annotations.Test;

@Test(groups = "teacher")
public class GroupsOnClass3 {
    public void Teacher1(){
        System.out.print("这是GroupsOnClass3中的Teacher1运行结果");
    }
    public void Teacher2(){
        System.out.print("这是GroupsOnClass3中的Teacher2运行结果");
    }
}
