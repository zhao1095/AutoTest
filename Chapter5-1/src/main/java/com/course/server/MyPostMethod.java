package com.course.server;

import com.course.bean.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "/")
@RequestMapping("/v1")
public class MyPostMethod {
    //这个变量是用来装cookies信息
    private  static Cookie cookie;
    //模拟用户登录成功获取cookies,然后在访问其他接口获取列表
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(value = "登录接口成功后,获取cookies信息",httpMethod = "POST")
    public  String login (HttpServletResponse response,
                          @RequestParam (value = "userName",required = true) String userName,
                          @RequestParam (value = "password",required = true) String password){
        if (userName.equals("zhangsan") && password.equals("12345")){
            Cookie cookie = new Cookie("login","true");
            response.addCookie(cookie);
            return "恭喜你登录成功";
        }

        return "用户名或者密码错误";
    }


    @RequestMapping(value = "/getUserList",method = RequestMethod.POST)
    @ApiOperation(value = "获取用户列表信息",httpMethod = "POST")
    public String getUserLiset(HttpServletRequest request,
                               @RequestBody User u) {
         User user= new User();;
        //获取cookies
        Cookie[] cookies = request.getCookies();
        //验证cookies是否合法
        if (cookies != null && u != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("login")
                        && cookie.getValue().equals("true")
                        && "zhangsan".equals(u.getName())
                        && u.getPassword().equals("12345")) {
                    user.setName("lisi");
                    user.setAge("20");
                    user.setSex("man");
                    return user.toString();
                }
                return "参数信息错误";
            }
        }
        return "参数不为空且参数信息错误";
    }
}
