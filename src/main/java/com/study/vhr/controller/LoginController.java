package com.study.vhr.controller;

import com.study.config.Interceptor.HrInterceptor;
import com.study.config.accnotation.UnInterception;
import com.study.vhr.entity.Hr;
import com.study.vhr.service.IHrService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: TestController
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 11:22 2022/9/3
 */

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(HrInterceptor.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private IHrService hrService;

    @UnInterception
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * 用户登录接口
     * @param hr 用户信息
     * @param code 验证码
     * @param session 会话信息
     * @param map 保存用户登录状态
     * @return
     */
    @RequestMapping("/toLogin")
    public String tologin(Hr hr, String code, HttpSession session, Map<String, Object> map){
        // 1.1、将用户验证码转化成小写
        String verifyCode = code.toLowerCase();
        // 1.2、比较前端验证码与 redis 缓存验证码是否一致，若不一致，再次跳回登录界面
        if(!verifyCode.equals(stringRedisTemplate.opsForValue().get("code"))){
            // 1.2.1 将错误信息返回给前端
            map.put("failMsg","验证码错误！！");
            return "login";
        }
        // 2.1 若验证码一致，再比对用户信息
        // 用户信息与浏览器id结合
        String userSession = session.getId()+"_"+hr.getUsername()+"_"+hr.getPassword();

        // 2.2 判断是否有用户信息，若有，进一步比较
        int flagCase = hrService.validateLogin(hr,session);
        if(flagCase == 1){
            // 把当前用户信息放到session中
            System.out.println("登陆阶段会话id : "+userSession);
            return "redirect:/index";
        }else if (flagCase == 2){
            map.put("failMsg", "该账号已登录，请不要重复登录");
            return "login";
        }else {
            map.put("failMsg", "用户名或密码错误");
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        SecurityUtils.getSubject().logout();
        System.out.println("退出系统！！");
        session.removeAttribute("user_session");
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
