package com.study.vhr.controller;

import com.study.config.Interceptor.HrInterceptor;
import com.study.config.accnotation.UnInterception;
import com.study.vhr.service.IHrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: IndexController
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 11:21 2022/9/3
 */
@Controller
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(HrInterceptor.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IHrService hrService;


    @RequestMapping("/index")
    public String index(){
        return "success";
    }



    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }
}
