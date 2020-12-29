package com.example;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class GetTest {

    public static String access_token;
    /**
     * 获取token
     */
    @AfterAll
    public static void getMethod(){
        access_token=given()
                .params("corpid","ww1aa64e79931be685","corpsecret","RGhEy5wN2GZFzVAz5zPU6xX6YjBio3r8Lm4Nz8ruchg")
                //.get("https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid=ww1aa64e79931be685&corpsecret=RGhEy5wN2GZFzVAz5zPU6xX6YjBio3r8Lm4Nz8ruchg")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then()
                .log().all().extract().response().path("access_token");
        //System.out.println(access_token);
    }

    /**
     * 企业微信内部服务api，发送应用消息
     */
    @Test
    void postMethod(){
        given()
                .contentType("application/json;charset=utf-8")
                .body("{\n" +
                        "   \"touser\" : \"111\",\n" +
                        "   \"msgtype\" : \"text\",\n" +
                        "   \"agentid\" : 1000003,\n" +
                        "   \"text\" : {\n" +
                        "       \"content\" : \"测试post接口\\n学习链接<a href=\\\"https://mp.csdn.net/\\\">欢迎</a>，学习开始。\"\n" +
                        "   },\n" +
                        "}")
                .post("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token=" + access_token)
                .then()
                .log().all();
    }
}
