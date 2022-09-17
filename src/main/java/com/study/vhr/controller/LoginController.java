package com.study.vhr.controller;

import com.study.config.accnotation.UnInterception;
import com.study.vhr.entity.Hr;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName: TestController
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 11:22 2022/9/3
 */

@Controller
@RequestMapping("/user")
public class LoginController {

    /**
     * 用户登录接口
     * @param hr
     * @param request
     * @return
     */
    @RequestMapping("/login")
    public String login(Hr hr, HttpServletRequest request){
//        UsernamePasswordToken token = new UsernamePasswordToken(hr.getUsername(),hr.getPassword());
//        System.out.println(hr);

        try {
//            Subject subject = SecurityUtils.getSubject();
//            subject.login(token);
//
            request.getSession().setAttribute("user",hr);
            return "success";
        }catch(Exception e){
            e.printStackTrace();
            request.getSession().setAttribute("user", hr);
            request.setAttribute("error", "用户名或密码错误！");
            return "login";
        }
    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
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
