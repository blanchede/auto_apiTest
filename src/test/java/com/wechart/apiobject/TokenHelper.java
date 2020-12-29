package com.wechart.apiobject;

import static io.restassured.RestAssured.given;

public class TokenHelper {
    public static String getAccessToken(){
        String accessToken = given().log().all()
                .when()
                .param("corpid", "ww4b19fef46274632e")
                .param("corpsecret","FLAFtZnVVHHq0ygrU7viANrMhdigmnG19M2qDnFT7xY")
                .get("https://qyapi.weixin.qq.com/cgi-bin/gettoken")
                .then().log().all()
                .extract().response().path("access_token");
        return accessToken;

    }

}
