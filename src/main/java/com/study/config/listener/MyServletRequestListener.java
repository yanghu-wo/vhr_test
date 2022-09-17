package com.study.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: MyServletRequestListener
 * @Author: LiuYun
 * @Description: 监听请求
 * @Data: Create in 22:56 2022/9/1
 */
//@Component
public class MyServletRequestListener implements ServletRequestListener {

    private static final Logger logger = LoggerFactory.getLogger(MyServletRequestListener.class);
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        logger.info("监听器销毁........");
        HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
        logger.info("session id为：{}", request.getRequestedSessionId());
        logger.info("request url为：{}", request.getRequestURL());

    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        logger.info("监听器初始化........");
    }
}
