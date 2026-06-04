package com.course.server;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@Api(value = "/")
public class MyGetMethod {
    @RequestMapping(value = "/getcookies",method = RequestMethod.GET)
    @ApiOperation(value = "通过这个方法可以获取到cookies信息",httpMethod = "GET")
    public String getCookies(HttpServletResponse response){
        //HttpServerletRequest   装请求信息类
        //HttpServerletResponse  装响应信息类
        Cookie cookie = new Cookie("login","true");
        response.addCookie(cookie);
        return "获取到cookies信息";
    }

    /*
    * 要求客户端携带cookies访问
    * 这还是一个需要携带cookies信息才能访问的请求
    * */
    @RequestMapping(value = "/get/with/cookies",method = RequestMethod.GET)
    @ApiOperation(value = "要求客户端携带cookies访问",httpMethod = "GET")
    public  String getWithCookies(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if (Objects.isNull(cookies))
        return "携带cookies信息不能为空";
        for (Cookie cookie :cookies){
            if (cookie.getName().equals("login") && cookie.getValue().equals("true")){
                return "您已完成客户端携带cookies访问";
            }
        }
        return "必须携带正确的cookies信息来";
    }


    /**
     *开发一个需要携带参数才能访问的get请求
     * 第一种实现方式 url key=value 等于key= value
     * 模拟获取商品列表
     */
    @RequestMapping(value =  "/get/with/param" , method = RequestMethod.GET)
    @ApiOperation(value = "第一种携带参数访问的get请求", httpMethod = "GET")
    public Map<String , Integer> getList(@RequestParam Integer start , @RequestParam Integer end){
        Map<String ,Integer> myList = new HashMap<>();
        //实际是从数据库中取出,未连接数据库,自己写数据
        myList.put("饼干",10);
        myList.put("水" , 50);
        myList.put("水果", 100);
        return  myList;
    }

    /**
     * 第二种需要携带参数访问的get请求
     *url=ip:port/get/with/param/1/10
     */
    @RequestMapping( value = "/get/with/param/{start}/{end}")
    @ApiOperation(value = "第er种携带参数访问的get请求", httpMethod = "GET")
    public  Map myGetList(@RequestParam (defaultValue = "1") Integer start , @RequestParam (defaultValue = "10") Integer end){

        Map<String ,Integer> myList = new HashMap<>();
        //实际是从数据库中取出,未连接数据库,自己写数据
        myList.put("鸡肉",10);
        myList.put("鱼" , 50);
        myList.put("猪肉", 100);
        return  myList;
    }

}
