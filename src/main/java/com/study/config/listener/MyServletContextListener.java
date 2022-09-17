package com.study.config.listener;

import com.study.vhr.entity.Hr;
import com.study.vhr.service.IHrService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * @ClassName: MyServletContextListener
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 22:57 2022/9/1
 */
public class MyServletContextListener implements ApplicationListener<ContextRefreshedEvent> {
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //1、获取上下文
        ApplicationContext applicationContext = event.getApplicationContext();
        //2、获取对应的service业务
        IHrService hrService = applicationContext.getBean(IHrService.class);
        List<Hr> hrList = hrService.findAllHr();
        //3、获取application域对象，将查到的信息放到Application域中
        ServletContext application = applicationContext.getBean(ServletContext.class);
        application.setAttribute("hrList", hrList);
    }
}
