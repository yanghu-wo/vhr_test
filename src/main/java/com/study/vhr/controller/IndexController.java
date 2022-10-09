package com.study.vhr.controller;

import com.study.config.Interceptor.HrInterceptor;
import com.study.config.accnotation.UnInterception;
import com.study.vhr.entity.Hr;
import com.study.vhr.service.IHrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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

    @UnInterception
    @GetMapping("/index")
    public String index(HttpServletRequest request){
        // 进入主页，判断用户是否登陆过
        // 1、首先判断redis是否有信息，返回查询结果
        // 1.1 若有缓存记录，
       /* String username = stringRedisTemplate.opsForValue().get(request.getSession().getId());
        if(username != null){
            // 1.2 查询数据库用户信息，并将信息封装到session中
            Hr hr = hrService.findOneHrByUserName(username);
            request.getSession().setAttribute("user",hr);
            // 1.3 跳转主界面
            return "success";
        }*/

        // 2、用户在redis无缓存记录，跳转到登录界面
        return "login";
    }

    @GetMapping("/unauthorized")
    public String unauthorized() {
        return "unauthorized";
    }
}
