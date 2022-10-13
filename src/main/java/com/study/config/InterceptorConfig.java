package com.study.config;

import com.study.config.Interceptor.HrInterceptor;
import com.study.vhr.entity.Hr;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: InterceptorConfig
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 22:22 2022/9/1
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getHrInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login")     // 登录界面
                .excludePathPatterns("/toLogin")  // 登录接口
                .excludePathPatterns("/logout")    // 退出接口
                .excludePathPatterns("/unauthorized") //失败跳转页面
                .excludePathPatterns("/getCode");  // 获取验证码
        super.addInterceptors(registry);
    }

    /**
     * 用来指定静态资源不被拦截，否则继承WebMvcConfigurationSupport这种方式会导致静态资源无法直接访问
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        super.addResourceHandlers(registry);
    }

    /**
     * 提前实例化
     * @return
     */
    @Bean
    public HrInterceptor getHrInterceptor(){
        return new HrInterceptor();
    }

}
