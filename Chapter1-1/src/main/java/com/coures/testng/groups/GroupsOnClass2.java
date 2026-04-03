package com.coures.testng.groups;

import org.testng.annotations.Test;

@Test(groups = "Student")
public class GroupsOnClass2 {
    public void Stedent1(){
        System.out.print("这是GroupsOnClass2中的Student1运行结果");
        }
    public void Stedent2(){
        System.out.print("这是GroupsOnClass2中的Student2运行结果");
    }
}
