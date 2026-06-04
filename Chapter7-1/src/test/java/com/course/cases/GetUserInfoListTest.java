package com.course.cases;

import com.alibaba.fastjson.JSON;
import com.course.config.TestConfig;
import com.course.model.GetUserListCase;
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
import java.util.List;
import java.util.Map;

public class GetUserInfoListTest {
    @Test(groups = "loginTrue",description = "获取性别为男的用户")
    public void getUserListInfo() throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        GetUserListCase getUserListCase = sqlSession.selectOne("getUserListCase", 1);
        System.out.println(getUserListCase.toString());
        System.out.println(TestConfig.getUserListUrl);

        //发送请求,获取结果
        JSONArray resultJson = getJsonResult(getUserListCase);
        System.out.println("接口返回的数据:" + resultJson);
        //验证
        List<User> userList = null;
        if ("true".equals(getUserListCase.getExpected())) {
            userList = sqlSession.selectList("queryUserBySex", getUserListCase);
            for (User u : userList) {
                System.out.println("获取的User" + u.toString());
            }
        }
        JSONArray userListJson = new JSONArray(userList);
        System.out.println(userListJson);
        Assert.assertEquals(userListJson.length(), resultJson.length());
        for (int i = 0; i < resultJson.length(); i++) {
            JSONObject except = (JSONObject) resultJson.get(i);
            JSONObject actual = (JSONObject) userListJson.get(i);
            Map<String,Object> exceptMap = JSON.parseObject(except.toString(),Map.class);
            Map<String,Object> actualMap = JSON.parseObject(actual.toString(),Map.class);
            Assert.assertEquals(exceptMap,actualMap);

            sqlSession.close();
        }
    }

    private JSONArray getJsonResult(GetUserListCase getUserListCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.getUserListUrl);
        JSONObject param = new JSONObject();
//        param.put("userName",getUserListCase.getUserName());
        param.put("sex",getUserListCase.getSex());
//        param.put("age",getUserListCase.getAge());

        post.setHeader("Content-type","application/json");
        StringEntity entity =new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        TestConfig.store = TestConfig.cookiestore;
        String result;
        HttpResponse response = TestConfig.closeableHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity(),"utf-8");
//        System.out.println("完整结果: "+ result);
        JSONArray jsonArray = new JSONArray(result);
        return  jsonArray;
    }
}
