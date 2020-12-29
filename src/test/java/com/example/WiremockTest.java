package com.example;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;

public class WiremockTest {
    static WireMockServer wireMockServer;
    @BeforeAll
    public static void initData(){
        wireMockServer = new WireMockServer(wireMockConfig().port(8089)); //No-args constructor will start on port 8080, no HTTPS
        wireMockServer.start();

        configureFor("localhost", 8089);
        System.out.println("mock server start");

    }

    @Test
    public void stubMock() throws InterruptedException {
        stubFor(get(urlEqualTo("/my/resource"))
                .withHeader("Accept", equalTo("text/xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "text/xml")
                        .withBody("<response>Some content</response>")));

        //添加500s等待，让服务不要立马关闭
        Thread.sleep(500000);

   }

   @Test
    public void easy_mock(){
        try {
            stubFor(get(urlEqualTo("/my/resource"))
                    .withHeader("Accept", equalTo("text/xml"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "text/xml")
                            .withBody("<response>Some content01</response>")));
            Thread.sleep(10000);
            reset();
            stubFor(get(urlEqualTo("/my/resource"))
                    .withHeader("Accept", equalTo("text/xml"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "text/xml")
                            .withBody("<response>中文content01</response>")));
            Thread.sleep(500000);

        }catch (InterruptedException e){
            e.printStackTrace();
        }
   }

    @Test
    public void proxyMockTest() throws Exception{

        stubFor(get(urlMatching(".*")).atPriority(10)
                .willReturn(aResponse().proxiedFrom("https://ceshiren.com")));
            //类型于Charles里的remote local
        stubFor(get(urlEqualTo("/categories_and_latest")).atPriority(10)
                .willReturn(aResponse().withBody(Files.readAllBytes(Paths.get(com.example.WiremockTest.class.getResource("/categoryList.json").getPath().substring(1))))));
        /*
        stubFor(get(urlEqualTo("/categories_and_latest")).atPriority(10)
                .willReturn(aResponse().withBody(Files.readAllBytes(Paths.get("target/classes绝对路径/categoryList.json")))));
        */
        Thread.sleep(500000);
    }


}
