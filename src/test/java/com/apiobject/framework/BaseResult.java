
package com.apiobject.framework;

import io.restassured.response.Response;

/**
 * @className: BaseResult
 * @description: 用于保存结果的对象基类，StepResult来继承
 **/
public class BaseResult {
    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    private Response response;
}