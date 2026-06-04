package com.course.cases;

import com.alibaba.fastjson.JSON;
import com.course.config.TestConfig;
import com.course.model.GetUserInfoCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.*;

public class GetUserInfoTest {
    @Test(groups = "loginTrue",description = "获取userID为1的用户")
    public void getUserInfo() throws IOException {
        // 获取Mybatis会话，从getuserinfocase用例表查询本条测试用例数据（userID、expected）
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        GetUserInfoCase getUserInfoCase = sqlSession.selectOne("getUserInfoCase", 1);
        if (getUserInfoCase == null) {
            System.out.println("数据库中UserID为1的用户数为:");
            return;
        } else {
            System.out.println(getUserInfoCase.toString());
            System.out.println(TestConfig.getUserInfoUrl);
            // 发送HTTP接口请求，拿到接口返回的JSON数组数据
            JSONArray resultJson = getJsonResult(getUserInfoCase);
            System.out.println(resultJson);
            //验证
/*            User user = sqlSession.selectOne(getUserInfoCase.getExpected(),getUserInfoCase);
            List userlist = new ArrayList();
            userlist.add(user);
            JSONArray jsonArray = new JSONArray(userlist);
            System.out.println(jsonArray);
            Assert.assertEquals(jsonArray,resultJson);
        }
    }*/

            // 调试代码
            Object obj = sqlSession.selectOne("getUserInfoCase", getUserInfoCase);
            System.out.println("查出来的对象类型是：" + obj.getClass().getName());
            //根据用例里的userID，去user业务表查询真实用户数据（核心：数据库基准数据）

            User user = sqlSession.selectOne("getUserById", getUserInfoCase.getUserID());

            //把查询出的用户对象装入List，用于FastJSON转为JSONArray

            List<User> userList = new ArrayList();
            userList.add(user);
            //数据库User实体 → JSON数组（格式：[{"id":1,"userName":"zhangsan"...}]）
            JSONArray jsonArray = new JSONArray(userList);
//            Assert.assertEquals(jsonArray.toString(), resultJson.toString());
            // 处理接口嵌套字符串数组问题,且除理空指针异常
            String apiJsonStr = null;
            if (resultJson !=null && resultJson.length() > 0 ) {

                 apiJsonStr = resultJson.getString(0);
            }
              com.alibaba.fastjson.JSONObject apiObj = JSON.parseArray(apiJsonStr).getJSONObject(0); ;
            if (apiObj == null){
                System.out.println("数据库不存在用户UserID为:"+ getUserInfoCase.getUserID());
                return;
            }

            JSONObject dbObj = jsonArray.getJSONObject(0);
            // 修正：不用构造器，用parse转Map
            //①JSON键值顺序不同导致字符串比对失败；②数据库JSON带[]、接口不带[]的格式差异
            Map<String,Object> dbMap = JSON.parseObject(dbObj.toString(), Map.class);
            System.out.println("数据库查出的字符串是: " + dbMap);
            Map<String,Object> apiMap = JSON.parseObject(apiObj.toString(), Map.class);
            System.out.println("接口返回的字符串是: " + apiMap);

            Assert.assertEquals(dbMap, apiMap);
            sqlSession.close();
        }
    }
    private JSONArray getJsonResult(GetUserInfoCase getUserInfoCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserInfoUrl);
        JSONObject param = new JSONObject();
        param.put("id",getUserInfoCase.getUserID());

        post.setHeader("Content-type","application/json");
        StringEntity entity =new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);
        TestConfig.store = TestConfig.cookiestore;

        HttpResponse response = TestConfig.closeableHttpClient.execute(post);
        String result;
        result = EntityUtils.toString(response.getEntity(),"utf-8");
        List resultList = Arrays.asList(result);
//        System.out.println(result);
        JSONArray array = new JSONArray(resultList);
         return  array;

    }
}
