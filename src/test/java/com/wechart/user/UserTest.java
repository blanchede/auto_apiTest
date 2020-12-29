package com.wechart.user;

import com.wechart.apiobject.TokenHelper;
import com.wechart.apiobject.UserObject;
import com.wechart.utils.FakerUtils;
import io.qameta.allure.Epic;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 用户测试
 */
@Epic("企业微信用户测试")
public class UserTest {

    private static String accessToken;

    @BeforeAll
    static void getAccessToken() {
        accessToken = TokenHelper.getAccessToken();
    }

    @DisplayName("创建用户")
    @Test
    void createUserTest() {
        Response response = UserObject.create(FakerUtils.getRandomStr(10), accessToken);
        assertEquals("0", response.path("errcode").toString());
    }

    @DisplayName("编辑用户")
    @Test
    void updateUserTest(){
        String userId = FakerUtils.getRandomStr(10);
        Response response = UserObject.create(userId, accessToken);
        assertEquals("0", response.path("errcode").toString());

        Response response1 = UserObject.update(userId, accessToken);
        assertEquals("0", response1.path("errcode").toString());
    }

    @DisplayName("删除用户")
    @Test
    void deleteUserTest(){
        String userId = FakerUtils.getRandomStr(10);
        Response response = UserObject.create(userId, accessToken);
        assertEquals("0", response.path("errcode").toString());

        Response response1 = UserObject.delete(userId, accessToken);
        assertEquals("0", response1.path("errcode").toString());
    }
}
