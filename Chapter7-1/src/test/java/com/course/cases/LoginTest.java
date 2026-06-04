package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.InterfaceName;
import com.course.model.LoginCase;
import com.course.utils.ConfigFile;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTest {
    @BeforeTest(groups = "loginTrue",description = "测试准备工作,获取Httpclient对象")
    public  void beforeTest(){
        TestConfig.getUserInfoUrl = ConfigFile.getUrl(InterfaceName.GETUSERINFO);
        TestConfig.getUserListUrl = ConfigFile.getUrl(InterfaceName.GetUSERlist);
        TestConfig.addUserUrl = ConfigFile.getUrl(InterfaceName.ADDUSERINFO);
        TestConfig.loginUrl = ConfigFile.getUrl(InterfaceName.LOGIN);
        TestConfig.updateUserInfoUrl = ConfigFile.getUrl(InterfaceName.UPDATEUSERINFO);

        TestConfig.closeableHttpClient = (CloseableHttpClient) HttpClients.createDefault();
    }

    @Test(groups = "loginTrue",description = "用户登录成功接口测试")
    public void loginTrue() throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        LoginCase queryparam = new LoginCase();
        queryparam.setUserName("zhangsan");
        LoginCase loginCase = sqlSession.selectOne("com.course.model.loginCase",queryparam);
        if (loginCase == null) {
            System.out.println("数据库中不存在userName为:" + queryparam.getUserName());
        }else {
            System.out.println(loginCase.toString());

            System.out.println(TestConfig.loginUrl);
            //发送请求
            String result = getresult(loginCase);
            //验证结果
            Assert.assertEquals(loginCase.getExpected(), result);
        }

    }

    @Test(groups = "loginFalse",description = "用户登录失败接口测试")
    public void loginFalse() throws IOException {
        SqlSession sqlSession = DatabaseUtil.getSqlSession();
        LoginCase queryparam = new LoginCase();
        queryparam.setUserName("lisi");
        LoginCase loginCase = sqlSession.selectOne("com.course.model.loginCase",queryparam);
        if(loginCase == null){
            System.out.println("数据库不存在username为: " +queryparam.getUserName());
        }else {
            System.out.println(loginCase.toString());
            System.out.println(TestConfig.loginUrl);
            String result = getresult(loginCase);
            Assert.assertEquals(loginCase.getExpected(), result);
        }
    }

    private String getresult(LoginCase loginCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.loginUrl);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userName",loginCase.getUserName());
        jsonObject.put("password", loginCase.getPassword());
        post.setHeader("content-type","application/json");
        StringEntity entity = new StringEntity(jsonObject.toString(),"utf-8");
        post.setEntity(entity);
        String result;
        HttpResponse response= TestConfig.closeableHttpClient.execute(post);
        result= EntityUtils.toString(response.getEntity());
        TestConfig.store =TestConfig.cookiestore;
        return  result;
    }
}
