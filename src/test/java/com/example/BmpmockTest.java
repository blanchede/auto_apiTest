package com.example;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import static spark.Spark.*;

public class BmpmockTest {

    @Test
    public void  bmp() throws IOException {
        BrowserMobProxy proxy = new BrowserMobProxyServer();
        proxy.start(8080);
        int port = proxy.getPort(); // get the JVM-assigned port
        // Selenium or HTTP client configuration goes here
        proxy.addResponseFilter((response, contents, messageInfo) -> {
            if (messageInfo.getOriginalUrl().contains(".json")) {
                /**
                 * todo: json -> hashmap -> rescue -> hashmap -> json
                 * mock 所有字符串改成null
                 * curl -k https://ceshiren.com/categories.json -x http://127.0.0.1:8080 满足正则的变成了null
                 */

                String contentNew = contents.getTextContents().replaceAll(":\"[^\"]*\"", "null");
                contents.setTextContents(contentNew);
                //contents.setTextContents("This message body will appear in all responses!");
            }
        });

        /**
         * 把请求变成另外的url，如下是到首页
         */
        proxy.addRequestFilter(((request, contents, messageInfo) -> {
            request.setUri("/");
            return null;
        }));

        System.in.read();

    }

    /**
     * sparkjava
     * @param args
     */
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World");
        get("/proxy", (req, res) -> {
            BrowserMobProxy proxy = new BrowserMobProxyServer();
            proxy.start(Integer.valueOf(req.queryMap("port").toString()));
            //proxy.start(req.port());
            return null;
        });
    }
}
