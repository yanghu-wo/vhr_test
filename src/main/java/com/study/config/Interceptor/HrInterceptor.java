package com.study.config.Interceptor;

import com.study.config.accnotation.UnInterception;
import com.study.vhr.entity.Hr;
import com.study.vhr.service.IHrService;
import com.study.vhr.service.impl.HrServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: HrIntercepter
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 22:20 2022/9/1
 */
public class HrInterceptor implements HandlerInterceptor {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private IHrService hrService;


    private static final Logger logger = LoggerFactory.getLogger(HrInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1、首先判断redis是否有信息，若有，直接放过，若无，进入下一步
        // 1.1 用户信息 由 sessionId 和 用户 name 组合
        /*String sessionID = request.getSession().getId();
        String name = stringRedisTemplate.opsForValue().get(sessionID);
        if(name != null){
            // 1.2 如果redis中有该用户信息，跳过拦截，直接进入
            return true;
        }*/

        // 1、判断方法是否为 添加了可忽略的 注解 方法
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String methodName = method.getName();

        // 获取请求url
        String url = request.getRequestURL().toString();
        // 获取请求ip
        String ip = request.getRemoteAddr();
        logger.info("用户请求的url为：{}，ip地址为：{}", url, ip);
        logger.info("====拦截到了方法：{}，在该方法执行之前执行====", methodName);

        // @UnInterception 是我们自定义的注解
        UnInterception unInterception = method.getAnnotation(UnInterception.class);
        if(null != unInterception) {
            return true;
        }

        // 2、判断浏览器 会话中是否有用户的关联信息
        String userSession = (String)request.getSession().getAttribute("user_session");

        if (userSession != null) {
            HashMap<String,String> onlineUsers = HrServiceImpl.getOnlineUsers();
            String curSid = request.getSession().getId();
            String userInfo = userSession.split("_")[1]+"_"+userSession.split("_")[2];

            String onlineSid = onlineUsers.get(userInfo);

//            System.out.println(onlineSid);
//            System.out.println("登录会话id : "+userSession);

            if (onlineSid != null && !curSid.equals(onlineSid)) {
                request.setAttribute("failMsg","您的账号已在别处登录，请重新登陆或修改密码！");
                request.getRequestDispatcher("login").forward(request,response);
                return false;
            }else {
                System.out.println("测试一下！！");
                //这种情况session还没过期，账号又还是活跃状态，此时应该更新本账号的登录时间
                long curTime = new Date().getTime();
                onlineUsers.put(userInfo + "_loginTime","" + curTime/1000);
                return true;
            }
        }
        request.getRequestDispatcher("login").forward(request,response);
        return false;

        // 3、判断用户当前权限，是否能够进行当前操作
       /* String password = request.getParameter("password");
        String userName = request.getParameter("username");
        String redisID = request.getSession().getId() +"_"+userName;
        // 3.1 判断是否有用户信息，若有，进入
        if(password != null&& userName != null){
            // 3.1.1 根据用户名获取数据库中 hr 信息，并与用户密码比对
            Hr hr = hrService.findOneHrByUserName(request.getParameter("username"));
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 获取加密文本
            Boolean isMatchPassword = passwordEncoder.matches(password,hr.getPassword()); // 匹配密码
            if(isMatchPassword){
                stringRedisTemplate.opsForValue().set(redisID,password,2000,TimeUnit.SECONDS);
                return true;
            }
            // 3.1.2 如果密码不匹配
            request.getSession().setAttribute("error","用户名或密码错误！！！");
            response.sendRedirect("/login");
            return false;
        }*/
        // request.getRequestedSessionId() 与 request.getSession().getId()有区别
        // 前者获取每次请求sessionId 会变，后者为浏览器sessionId不变

        // 返回true才会继续执行，返回false则取消当前请求

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("执行完方法之后进执行(Controller方法调用之后)，但是此时还没进行视图渲染");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("整个请求都处理完咯，DispatcherServlet也渲染了对应的视图咯，此时我可以做一些清理的工作了");
    }
}
