# auto_apiTest
example下主要是mockdemo示例

wechart下为企业微信创建部门和成员示例
--apiobject主要存放封装的某个模块请求，在deparment和user测试下进行test调用

--utils里面主要放工具类

--task下放公共使用task

# 接口测试框架

主要是framwork、test的package下编写

一、定义ApiActionModel，用来用来存储接口对象yaml反序列化出来的action单元
1、确定请求方法和URL
2、请求参数、URL中全局变量替换 PS:这里需要编写占位符工具类PlaceholderUtils 和全局变量类
3、根据形参和实参构建内部变量Map
4、请求参数、URL中内部变量替换
5、拿到上面替换完成的接口请求数据，进行请求并返回结果

二、定义ApiObjectModel类，用来用来存储apiObject yaml反序列化出来的ApiObject实体对象
1、添加load方法，反序列化一个yaml文件为ApiObjectModel对象

三、定义ApiLoder类，用来加载api对象和获取某个action
1、实现load方法，批量反序列化一个路径下所有的yaml文件。
2、实现run方法，根据api名称和action名称从反序列化的对象列表中获取某个action。

四、定义StepModel类，用来用来存储testcase yaml反序列化出来的step单元
1、需要定义AssertModel类，用来存储反序列化出来的断言对象
2、需要定义StepResult类，用来存储请求的相应信息和断言结果
3、替换实参中的变量
4、根据case中的配置执行相应的action，当然要传入替换过变量的实参
5、根据case中的配置截取响应中的字段，并存入step变量Map中
6、根据case中的配置截取响应中的字段，并存入Global变量Map中
7、根据case中的配置对返回结果进行软断言，但不会终结测试将断言结果存入断言结果列表中，在用例最后进行统一结果输出
8、将response和断言结果存储到stepResult对象中并返回

五、定义ApiTestCaseModel类，用来用来存储testcase yaml反序列化出来的TestCase实体对象
1、加载用例层关键字变量
2、遍历执行所有step
3、处理step返回的save变量
4、处理assertList，到最后进行断言。
5、进行assertAll断言

六、定义apiTest测试方法和同名的参数方法，用来读取目录下所有的testcase文件并执行
1、遍历目录下所有的用例文件，并组装成参数列表
