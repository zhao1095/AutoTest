package com.course.controller;

import com.course.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Api(value = "v1")
@RequestMapping(value = "v1")
public class Demo {

      private  static  final  String namespace="com.course";
    //获取一个执行sql语句的对象
    @Autowired
    private SqlSessionTemplate template;

    @RequestMapping(value = "/getUserCount",method = RequestMethod.GET)
//    @ApiOperation(value = "可以获取到用户数",httpMethod = "GET")
    public int getUserCount(){
       return template.selectOne(namespace +".getUserCount");
    }

    @RequestMapping(value = "/addUser",method = RequestMethod.POST)
//    @ApiOperation(value = "添加`用户",httpMethod = "POST")
    public  int addUser(@RequestBody User user){
        int result =template.insert( namespace +".addUser",user);
        return  result;
    }

    @RequestMapping(value = "/updateUser",method = RequestMethod.POST)
//    @ApiOperation(value = "更新用户信息",httpMethod = "POST")
    public int updateUser(@RequestBody User user){
        return template.update(namespace +".updateUser",user);

    }

    @RequestMapping(value = "/delUser",method = RequestMethod.POST)
//    @ApiOperation(value = "删除用户",httpMethod = "POST")
    public  int  delUser(@RequestParam int id){
        return  template.delete(namespace +".delUser",id);
    }
}
