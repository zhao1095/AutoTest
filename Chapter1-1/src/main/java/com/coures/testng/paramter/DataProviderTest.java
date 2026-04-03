package com.coures.testng.paramter;

import org.testng.IMethodInstance;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Method;

public class DataProviderTest {

    /*
     DataProvider 进行传参
     */
    @Test(dataProvider = "data")
    public  void testDataProvider(String name,int age){
        System.out.print("name="+ name +": age="+ age);
    }
    @DataProvider(name = "data")
    public Object [] [] providerData(){
        Object[][] object=new Object[][]{
                {"张三",10},
                {"lisi",15},
                {"王五",13},
        };
        return object;
    }

    /*
    通过方法名传递参数,使用Dataprovider对象进行参数传递
     */
    @Test(dataProvider = "methodData")
    public  void  Test1(String name , int age){
        System.out.print("这是参数传参方法1 name="+name +":age = "+age);
    }
    @Test(dataProvider = "methodData")
    public  void  Test2(String name , int age){
        System.out.print("这是参数传参方法2222 name="+name +":age = "+age);
    }
    @DataProvider(name="methodData")
    public Object[][] methodDataTest (Method method){
        Object[] [] result =null;
        if(method.getName().equals("Test1")){
            result =new Object[][]{
                    {"zhangsan",20},
                    {"李四",21}
            };
        }else if(method.getName().equals("Test2")){
            result =new Object[][]{
                    {"李凯",22},
                    {"Ma6",23}
            };
        }
        return result;
    }



}
