
package com.apiobject.framework.actions;


import com.apiobject.framework.global.GlobalVariables;
import com.util.PlaceholderUtils;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.ArrayList;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

/**
 * @version: V1.0
 * @className: ApiActionModel
 * @packageName: com.apiobject.framwork.actions
 * @description: 接口动作对象
 * 开发日志01：定义ApiActionModel，用来用来存储接口对象yaml反序列化出来的action单元
 * 1、确定请求方法和URL
 * 2、请求参数、URL中全局变量替换 PS:这里需要编写占位符工具类PlaceholderUtils 和全局变量类
 * 3、根据形参和实参构建内部变量Map
 * 4、请求参数、URL中内部变量替换
 * 5、拿到上面替换完成的接口请求数据，进行请求并返回结果
 **/
public class ApiActionModel {
    private String method = "get";
    private String url;
    private String body;
    private String contentType;
    private HashMap<String, String> query;
    private HashMap<String, String> headers;
    private String post;
    private String get;
    private Response response;
    private ArrayList<String> formalParam;
    private HashMap<String, String> actionVariables = new HashMap<>();

    /**
     * 选择上面的变量，点击右键Generate->Getter and Setter，选择所有的点击ok；
     * 这样其他外部可以调用；
     */

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public HashMap<String, String> getQuery() {
        return query;
    }

    public void setQuery(HashMap<String, String> query) {
        this.query = query;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getGet() {
        return get;
    }

    public void setGet(String get) {
        this.get = get;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public ArrayList<String> getFormalParam() {
        return formalParam;
    }

    public void setFormalParam(ArrayList<String> formalParam) {
        this.formalParam = formalParam;
    }

    public Response run(ArrayList<String> actualParameter) {
        HashMap<String, String> finalQuery = new HashMap<>();
        String runBody = this.body;
        String runUrl = this.url;
        /**
         * 1.确定请求方法和URL
         */
        if (post != null) {
            runUrl = post;
            method = "post";
        } else if (get != null) {
            runUrl = get;
            method = "get";
        }
        /**
         * 2、请求参数、URL中全局变量替换
         * PS:这里需要编写占位符工具类PlaceholderUtils
         */
        if (query != null) {
            finalQuery.putAll(PlaceholderUtils.resolveMap(query, GlobalVariables.getGlobalVariables()));
        }
        //body全局变量替换
        runBody = PlaceholderUtils.resolveString(runBody, GlobalVariables.getGlobalVariables());
        //url全局变量替换
        runUrl = PlaceholderUtils.resolveString(runUrl, GlobalVariables.getGlobalVariables());
        if (formalParam != null && actualParameter != null && formalParam.size() > 0 && actualParameter.size() > 0) {

            /**
             * 3、根据形参和实参构建内部变量MAP
             */
            for (int index = 0; index < formalParam.size(); index++) {
                actionVariables.put(formalParam.get(index), actualParameter.get(index));
            }
            /**
             * 4、请求、URL中的内部变量进行一个替换
             */
            if(query !=null){
                finalQuery.putAll(PlaceholderUtils.resolveMap(query,actionVariables));
            }
            runBody =  PlaceholderUtils.resolveString(body,actionVariables);
            runUrl = PlaceholderUtils.resolveString(runUrl,actionVariables);
        }
        /**
         * 5、拿到了上面完成了变量替换的请求数据，我们接下来要进行请求并返回结果
         */
        RequestSpecification requestSpecification = given().log().all();
        if(contentType !=null){
            requestSpecification.contentType(contentType);
        }
        if(headers !=null){
            requestSpecification.headers(headers);
        }
        if(finalQuery != null && finalQuery.size()>0){
            requestSpecification.formParams(finalQuery);
        }else if (runBody !=null){
            requestSpecification.body(runBody);
        }

        Response response = requestSpecification.request(method,runUrl).then().log().all().extract().response();

        this.response = response;
        return response;
    }

}