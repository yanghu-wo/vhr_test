package com.study.config;

import com.study.config.realm.UserRealm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName: ShiroConfig
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 9:40 2022/9/3
 */
//@Configuration
public class ShiroConfig {
    private static final Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    @Bean
    public UserRealm getUserRealm(){
        UserRealm userRealm = new UserRealm();
        logger.info("====myRealm注册完成=====");
        return userRealm;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(){
        org.apache.shiro.web.mgt.DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(getUserRealm());
        logger.info("====securityManager注册完成====");
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        // 定义shiroFactoryBean
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 设置自定义的securityManager
        factoryBean.setSecurityManager(securityManager);
        // 设置默认登录的url，身份认证失败会访问该url
        factoryBean.setLoginUrl("/index");
        // 设置成功之后要跳转的链接
        factoryBean.setSuccessUrl("/success");
        // 设置未授权界面，权限认证失败会访问该url
        factoryBean.setUnauthorizedUrl("/unauthorized");

        Map<String, String> filterChainMap = new LinkedHashMap<>();
        // 配置可以匿名访问的地址，可以根据实际情况自己添加，放行一些静态资源等
        filterChainMap.put("/css/**", "anon");
        filterChainMap.put("/imgs/**", "anon");
        filterChainMap.put("/js/**", "anon");
        filterChainMap.put("/swagger-*/**", "anon");
        filterChainMap.put("/swagger-ui.html/**", "anon");
        // 登录url 放行
        filterChainMap.put("/index", "anon");

//        // “/user/admin” 开头的需要身份认证
//        filterChainMap.put("/admin*", "authc");
//        // “/user/student” 开头的需要角色认证，是“admin”才允许
//        filterChainMap.put("/student*/**", "roles[admin]");
//        // “/user/teacher” 开头的需要权限认证，是“user:create”才允许
//        filterChainMap.put("/teacher*/**", "perms[\"user:create\"]");

        // 配置logout过滤器
        filterChainMap.put("/logout", "logout");
        // 设置shiroFilterFactoryBean的FilterChainDefinitionMap
        factoryBean.setFilterChainDefinitionMap(filterChainMap);
        logger.info("====shiroFilterFactoryBean注册完成====");
        return factoryBean;
    }


}