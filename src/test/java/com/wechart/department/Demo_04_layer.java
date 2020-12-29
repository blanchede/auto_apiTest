package com.wechart.department;

import com.wechart.apiobject.DepartMentObject;
import com.wechart.apiobject.TokenHelper;
import com.wechart.task.EvnHelperTask;
import com.wechart.utils.FakerUtils;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
/**
 * 1.基础脚本，分别执行了创建、修改、查询、删除
 * 2.进行了优化，编辑部门方法进行了解耦，可以独立运行
 * 3.进行了优化，使用时间戳命名法避免入参重复造成的报错（修改部门测试用例）--数据有可追溯性
 * 4.进行了优化，每次方法执行前后都对历史数据进行清理，确保每次执行脚本数据环境一致（修改部门测试用例）
 * 5.进行了优化，对脚本进行了分层，减少了重复代码，提高了代码复用率，并减少了维护成本。
 * */

/**
 * department show
 */

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class Demo_04_layer {
    private static final Logger logger = LoggerFactory.getLogger(Demo_01_base.class);
    static String accessToken;

    @BeforeAll
    public static void getAccessToken() throws Exception {
        accessToken= TokenHelper.getAccessToken();
        logger.info(accessToken);
    }
    @AfterEach
    @BeforeEach
    void clearDepartment(){
        EvnHelperTask.clearDpartMentTask(accessToken);
//        Response listResponse =DepartMentObject.listDepartMent("1",accessToken);
//        ArrayList<Integer> departmentIdList = listResponse.path("department.id");
//        for(int departmentId : departmentIdList){
//            if(1==departmentId){
//                continue;
//            }
//            Response DelResponse = DepartMentObject.deletDepartMent(departmentId+"",accessToken);
//        }
    }
    @DisplayName("创建部门")
    @Test
    @Order(1)
    void createDepartment() {
        String name = "name"+ FakerUtils.getTimeStamp();
        String enName = "en_name"+FakerUtils.getTimeStamp();

        Response response= DepartMentObject.creatDepartMent(name,enName,accessToken);
        assertEquals("0",response.path("errcode").toString());

    }
    @DisplayName("修改部门")
    @Test
    @Order(2)
    void updateDepartment() {
        String updateName = "name"+ FakerUtils.getTimeStamp();
        String updateEnName = "en_name"+FakerUtils.getTimeStamp();

        String departmentId = DepartMentObject.creatDepartMent(accessToken);

        Response updateResponse = DepartMentObject.updateDepartMent(updateName,updateEnName,departmentId,accessToken);
        assertEquals("0",updateResponse.path("errcode").toString());

    }

    @DisplayName("查询部门")
    @Test
    @Order(3)
    void listDepartment() {
        String departmentId = DepartMentObject.creatDepartMent(accessToken);

        Response response =DepartMentObject.listDepartMent(departmentId,accessToken);
        assertEquals("0",response.path("errcode").toString());
        assertEquals(departmentId,response.path("department.id[0]").toString());

    }
    @DisplayName("删除部门")
    @Test
    @Order(4)
    void deleteDepartment() {
        String departmentId = DepartMentObject.creatDepartMent(accessToken);

        Response response = DepartMentObject.deletDepartMent(departmentId,accessToken);
        assertEquals("0",response.path("errcode").toString());

    }
}
