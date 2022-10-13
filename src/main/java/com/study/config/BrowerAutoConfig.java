package com.study.config;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

import java.io.IOException;

/**
 * @ClassName: BrowerAutoConfig
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 16:12 2022/10/11
 */
@Configuration
public class BrowerAutoConfig {
    @EventListener({ApplicationReadyEvent.class})
    public void applicationReadyEvent(){
        // 这里需要注url:端口号后不加任何地址,跳转项目首页,比如index.html,否则404
        String url = "http://localhost:8991/login";
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
