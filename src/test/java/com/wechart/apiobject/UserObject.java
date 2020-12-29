package com.wechart.apiobject;

import com.wechart.utils.FakerUtils;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UserObject {
    /**
     * 创建用户
     * @param userId	用户id
     * @param accessToken	accessToken
     * @return	Response
     */
    public static Response create(String userId, String accessToken){
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("userid", userId);
        bodyMap.put("name", FakerUtils.getRandomStr(12));
        bodyMap.put("mobile", FakerUtils.getTel());
        bodyMap.put("department", "1");
        //JSONObject jsonObj = new JSONObject(bodyMap);
        //System.out.println(jsonObj);

        return customPost("https://qyapi.weixin.qq.com/cgi-bin/user/create", bodyMap, accessToken);
    }

    /**
     * 修改用户
     */
    public static Response update(String userId, String accessToken){
        Map<String, String> bodyMap = new HashMap<>();
        bodyMap.put("userid", userId);
        bodyMap.put("mobile", FakerUtils.getTel());
        //JSONObject jsonObj = new JSONObject(bodyMap);
        return customPost("https://qyapi.weixin.qq.com/cgi-bin/user/update", bodyMap, accessToken);
    }

    /**
     * 删除用户
     * @param userId
     * @param accessToken
     * @return
     */
    public static Response delete(String userId, String accessToken){
        Map<String, String> queryParam = new HashMap<>();
        queryParam.put("userid", userId);
        queryParam.put("access_token", accessToken);

        return customGet("https://qyapi.weixin.qq.com/cgi-bin/user/delete", queryParam);
    }

    private static Response customGet(String url, Map<String, String> queryParams){
        return given()
                .queryParams(queryParams)
                .when()
                .get(url)
                .then()
                .log().all()
                .extract().response();
    }

    private static Response customPost(String url, Map<String, String> body, String accessToken){
        return given()
                .contentType("application/json")
                .queryParam("access_token", accessToken)
                .body(body)
                .when()
                .post(url)
                .then()
                .log().all()
                .extract().response();
    }

}
