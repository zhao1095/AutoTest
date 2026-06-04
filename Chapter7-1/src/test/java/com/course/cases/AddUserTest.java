package com.course.cases;

import com.course.config.TestConfig;
import com.course.model.AddUserCase;
import com.course.model.User;
import com.course.utils.DatabaseUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddUserTest {
    @Test(dependsOnGroups = "loginTrue",description = "添加用户接口测试信息")
    public void addUser() throws IOException, InterruptedException {
        SqlSession session = DatabaseUtil.getSqlSession();
        AddUserCase addUserCase = session.selectOne("addUserCase", 1);
        if (addUserCase == null){
            System.out.println("添加用户****失败");
            return;
        }
        System.out.println(addUserCase.toString());
        System.out.println(TestConfig.addUserUrl);

        //发请求,获取结果
        String result = getResult(addUserCase);
        //验证返回结果
        //休眠 3000ms 避开事务隔离问题，接口返回 true、数据库已存数据，休眠后查询就能查到
       Thread.sleep(3000);
        //新增完后，新开一个全新 SqlSession 查询
        //旧 SqlSession 创建于新增数据之前，快照定格在过去，永远查新数据；必须重新创建 SqlSession 才能读取数据库最新真实数据。
       SqlSession sqlSession = DatabaseUtil.getSqlSession();
        User user = sqlSession.selectOne("addUserInfo",addUserCase);
        if (user == null ){
            System.out.println("数据库额未查询到新增用户:" + user);
            Assert.fail("新增入库失败");
            return;
        }
        System.out.println(user.toString());

        Assert.assertEquals(addUserCase.getExpected(),result);
        session.close();
        sqlSession.close();
    }

    private String getResult(AddUserCase addUserCase) throws IOException {
        HttpPost post = new HttpPost(TestConfig.addUserUrl);
        JSONObject param = new JSONObject();
        param.put("userName",addUserCase.getUserName());
        param.put("password",addUserCase.getPassword());
        param.put("sex",addUserCase.getSex());
        param.put("age",addUserCase.getAge());
        param.put("permission",addUserCase.getPermission());
        param.put("isDelete",addUserCase.getIsDelete());

        //设置头信息
        post.setHeader("content-type","application/json");
        StringEntity entity =new StringEntity(param.toString(),"utf-8");
        post.setEntity(entity);

        //设置cookies
        TestConfig.store = TestConfig.cookiestore;
        String result;
        HttpResponse response= TestConfig.closeableHttpClient.execute(post);
        result = EntityUtils.toString(response.getEntity());
//        System.out.println(result);
        return result;

    }

}
