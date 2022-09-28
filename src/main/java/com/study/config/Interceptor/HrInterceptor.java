package com.study.config.Interceptor;

import com.study.config.accnotation.UnInterception;
import com.study.vhr.entity.Hr;
import com.study.vhr.service.IHrService;
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
import java.lang.reflect.Method;
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
        // 获取请求url
        String url = request.getRequestURL().toString();
        // 获取请求ip
        String ip = request.getRemoteAddr();
        logger.info("用户请求的url为：{}，ip地址为：{}", url, ip);

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        String methodName = method.getName();

        // @UnInterception 是我们自定义的注解
        UnInterception unInterception = method.getAnnotation(UnInterception.class);
        if(null != unInterception) {
            return true;
        }

        logger.info("====拦截到了方法：{}，在该方法执行之前执行====", methodName);

        String redisID = request.getRequestedSessionId()+"_"+request.getParameter("username");
        String password = request.getParameter("password");
        String verifyCode = request.getParameter("code").toLowerCase();

        // 确认验证码
        if(!verifyCode.equals(stringRedisTemplate.opsForValue().get("code"))){
            logger.info("验证码错误！！");
            return false;
        }

        Hr hr = hrService.findOneHrByUserName(request.getParameter("username"));

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Boolean is_true = passwordEncoder.matches(password,hr.getPassword());
        if(is_true){
            stringRedisTemplate.opsForValue().set(redisID,password,2000,TimeUnit.SECONDS);
            return true;
        }
        // 返回true才会继续执行，返回false则取消当前请求
        return false;
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
