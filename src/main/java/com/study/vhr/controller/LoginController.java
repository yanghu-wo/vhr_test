package com.study.vhr.controller;

import com.study.config.Interceptor.HrInterceptor;
import com.study.config.accnotation.UnInterception;
import com.study.vhr.entity.Hr;
import com.study.vhr.service.IHrService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: TestController
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 11:22 2022/9/3
 */

@Controller
@RequestMapping("/user")
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(HrInterceptor.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IHrService hrService;

    /**
     * 用户登录接口
     * @param hr
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String login(Hr hr, HttpServletRequest request){
        // 1.1、将用户验证码转化成小写
        String verifyCode = request.getParameter("code").toLowerCase();

        // 1.2、比较前端验证码与 redis 缓存验证码是否一致，若不一致，再次跳回登录界面
        if(!verifyCode.equals(stringRedisTemplate.opsForValue().get("code"))){
            logger.info("验证码错误！！");
            // 1.2.1 将错误信息返回给前端
            request.getSession().setAttribute("error", "验证码错误！！");
            return "login";
        }

        // 2.1 若验证码一致，再比对用户信息
        String sessionID = request.getSession().getId();
        String redisID = sessionID+"_"+hr.getUsername();

        // 2.2 判断是否有用户信息，若有，进一步比较
        Hr hrInfo = hrService.findOneHrByUserName(hr.getUsername());
        if(hrInfo!=null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 获取加密文本
            Boolean isMatchPassword = passwordEncoder.matches(hr.getPassword(),hrInfo.getPassword()); // 匹配密码

            if(isMatchPassword){
                request.getSession().setAttribute("user",hrInfo);
                // 2.3 查询 redis 有无用户信息
                String password = stringRedisTemplate.opsForValue().get(redisID);
                if(password == null){
                    stringRedisTemplate.opsForValue().set(redisID,hr.getPassword(),2000, TimeUnit.SECONDS);
                }
                System.out.println("存入redis信息！！！");
                return "success";
            }
        }

        // 3.1 如果redis中信息与该用户信息不匹配，返回用户信息错误消息
        request.getSession().setAttribute("error","用户名或密码错误！！！");
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        System.out.println("退出系统！！");
        return "login";
    }

    /**
     * 身份认证测试接口
     * @param request
     * @return
     */
    @RequestMapping("/admin")
    public String admin(HttpServletRequest request) {
        Object user = request.getSession().getAttribute("user");
        return "success";
    }

    /**
     * 角色认证测试接口
     * @param request
     * @return
     */
    @RequestMapping("/student")
    public String student(HttpServletRequest request) {
        return "success";
    }

    /**
     * 权限认证测试接口
     * @param request
     * @return
     */
    @RequestMapping("/teacher")
    public String teacher(HttpServletRequest request) {
        return "success";
    }
}
