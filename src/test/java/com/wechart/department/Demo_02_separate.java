package com.wechart.department;

import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * 1.基础脚本，分别执行了创建、修改、查询、删除
 * 2.进行了优化，编辑部门方法进行了解耦，可以独立运行
 */

/**
 * department show
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Demo_02_separate {
    static String accessToken;
    static String departmentId;
    @BeforeAll
    public static void getAccessToken(){
        accessToken = given().log().all()
                .when()
                .param("corpid", "ww4b19fef46274632e")
                .param("corpsecret","FLAFtZnVVHHq0ygrU7viANrMhdigmnG19M2qDnFT7xY")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all()
                .extract().response().path("access_token");
    }

    @DisplayName("创建部门")
    @Order(1)
    @Test
    void createDepartment(){
        String body = "{\n" +
                "   \"name\": \"广州研发中心\",\n" +
                "   \"name_en\": \"RDGZ\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 1,\n" +
                "   \"id\": 3\n" +
                "}";
        Response responce = given().log().all()
                .contentType("application/json")
                .body(body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + accessToken)
                .then().log().all()
                .extract()
                .response();
        departmentId = responce.path("id").toString();

    }

    @DisplayName("修改部门")
    @Order(2)
    @Test
    void updateDepartment(){
        String creat_body = "{\n" +
                "   \"name\": \"广州研发中心\",\n" +
                "   \"name_en\": \"RDGZ\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 1,\n" +
                "   \"id\": 3\n" +
                "}";
        final Response creatResponce = given().log().all()
                .contentType("application/json")
                .body(creat_body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/create?access_token=" + accessToken)
                .then().log().all()
                .extract()
                .response();
        String departmentId = creatResponce.path("id")!=null ? creatResponce.path("id").toString():null;

        String update_body = "{\n" +
                "   \"id\": "+departmentId+",\n" +
                "   \"name\": \"广州研发中心update\",\n" +
                "   \"name_en\": \"RDGZupdate\",\n" +
                "   \"parentid\": 1,\n" +
                "   \"order\": 1\n" +
                "}";
        Response updateResponse=given().log().all()
                .contentType("application/json")
                .body(update_body)
                .post("https://qyapi.weixin.qq.com/cgi-bin/department/update?access_token=" + accessToken)
                .then()
                .log().body()
                .extract().response();
        assertEquals("0",updateResponse.path("errcode").toString());

    }

    @DisplayName("查询部门")
    @Order(3)
    @Test
    void searchDepartment(){
        Response response = given().log().all()
                .when()
                .param("access_token", accessToken)
                .param("id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/list")
                .then().log().body()
                .extract().response();
        assertEquals("0", response.path("errcode").toString());

    }

    @DisplayName("删除部门")
    @Order(4)
    @Test
    void deleteDepartment(){
        Response response = given().log().all()
                .when()
                .param("access_token", accessToken)
                .param("id",departmentId)
                .get("https://qyapi.weixin.qq.com/cgi-bin/department/delete")
                .then().log().body()
                .extract().response();
        assertEquals("0", response.path("errcode").toString());

    }



}
