package com.study.config.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @ClassName: MyHttpSessionListener
 * @Author: LiuYun
 * @Description:  监听会话
 * @Data: Create in 22:53 2022/9/1
 */
//@Component
public class MyHttpSessionListener implements HttpSessionListener {

    private static final Logger logger = LoggerFactory.getLogger(MyHttpSessionListener.class);

    // 记录上线人数
    private Integer count  = 0;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        logger.info("新用户上线");
        count++;
        se.getSession().getServletContext().setAttribute("count", count);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        logger.info("用户下线");
        count--;
        se.getSession().getServletContext().setAttribute("count", count);
    }
}
