/**
 * projectName: apiobject-framwork
 * fileName: ApiObjectModel.java
 * packageName: com.apiobject.framework.api
 * date: 2020-12-26 下午2:37
 */
package com.apiobject.framework.api;

import com.apiobject.framework.actions.ApiActionModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * @className: ApiObjectModel
 * @packageName: com.apiobject.framework.api
 * @description: 接口对象类(加载yaml文件，反序列化)
 * 开发日志02：定义ApiObjectModel类，用来用来存储apiObject yaml反序列化出来的ApiObject实体对象
 * 1、添加load方法，反序列化一个yaml文件为ApiObjectModel对象
 **/
public class ApiObjectModel {
    private String name;
    private HashMap<String , ApiActionModel> actions;
    private HashMap<String ,String> obVariables = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, ApiActionModel> getActions() {
        return actions;
    }

    public void setActions(HashMap<String, ApiActionModel> actions) {
        this.actions = actions;
    }

    public HashMap<String, String> getObVariables() {
        return obVariables;
    }

    public void setObVariables(HashMap<String, String> obVariables) {
        this.obVariables = obVariables;
    }

    public static ApiObjectModel load(String path) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        return objectMapper.readValue(new File(path),ApiObjectModel.class);
    }

}