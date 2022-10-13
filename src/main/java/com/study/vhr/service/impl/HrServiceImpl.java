package com.study.vhr.service.impl;

import com.study.vhr.entity.Hr;
import com.study.vhr.mapper.HrMapper;
import com.study.vhr.service.IHrService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.*;

/**
 * @ClassName: HrServiceImpl
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 21:13 2022/9/1
 */
@Service
public class HrServiceImpl implements IHrService {

    @Resource
    private HrMapper hrMapper;

    @Resource
    private RedisTemplate redisTemplate;

    private static HashMap<String,String> onlineUsers = new HashMap<>();

    public static HashMap<String, String> getOnlineUsers(){
        return onlineUsers;
    }

    @Override
    public int validateLogin(Hr hr, HttpSession session) {
        // 把用户相关信息保存在redis中
        // 1、根据输入信息，查询数据库用户
        Hr hrInfo = findOneHrByUserName(hr.getUsername());
        if(hrInfo == null){
            // 找不到用户信息
            return 0;
        }
        // 2.1、校对加密信息
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); // 获取加密文本
        Boolean isMatchPassword = passwordEncoder.matches(hr.getPassword(),hrInfo.getPassword()); // 匹配密码
        // 2.2、校对无误
        if (isMatchPassword) {
            // 获取当前用户登录时间
            Date date = new Date();
            // 获取当前浏览器 会话 id
            String curSessionId = session.getId();
            String userInfo = hr.getUsername()+"_"+hr.getPassword();

            // 从redis中获取 用户上一次登录时间，以及 保存在 redis 中的 sessionId 等信息
            /*Map<String,String> onlineUsers = redisTemplate.opsForHash().entries(hr.getUsername()+"_LoginTime");
            String sessionId = "";*/

            // 从 onlineUsers 获取当前 用户信息 ，判断用户是否还在登录期限内
            if(onlineUsers.get(userInfo)!=null && onlineUsers.get(userInfo).equals(curSessionId)){
                // 获取用户上次登录时间
                Long loginTime = new Long(onlineUsers.get(userInfo + "_loginTime"));
                // 用户 剩余时间 =  当前时间 - 上次登录时间
                long interval = date.getTime()/1000 - loginTime;
                if (interval <= 60){
                    // 该用户已经登录且session还在有效期内
                    return 2;
                }
            }
            // 存储会话信息
            String userSession = curSessionId +"_"+userInfo;
            session.setAttribute("user_session",userSession);
            session.setAttribute("user",hrInfo);

            // 已过有效期,重新保存用户信息
            onlineUsers.put(userInfo, session.getId());
            onlineUsers.put(userInfo + "_loginTime", "" + date.getTime()/1000);
            return 1;
        }
        //账号密码验证失败
        return 0;
    }

    /* 1、增加--------------------------------------------*/
    @Override
    public int insertHr(Hr hr) {
        return hrMapper.insertHr(hr);
    }

    /* 2、删除--------------------------------------------*/
    @Override
    public int delHrById(int id) {
        return hrMapper.delHrById(id);
    }

    /* 3、查询--------------------------------------------*/

    /**
     * 3.1查询用户根据id
     * @param id
     * @return
     */
    @Override
    public Hr findOneHrById(int id) {
        return hrMapper.findOneHrById(id);
    }

    /**
     * 3.2、查询所有用户
     * @return
     */
    @Override
    public List<Hr> findAllHr() {
        return hrMapper.findAllHr();
    }

    /**
     * 3.3、返回用户角色
     * @param username
     * @return
     */
    @Override
    public Set<String> getHrRoleByName(String username) {
        return hrMapper.getHrRoleByName(username);
    }

    /**
     * 3.4、用户权限
     * @param username
     * @return
     */
    @Override
    public Set<String> getHrPermissions(String username) {
        return hrMapper.getHrPermissions(username);
    }

    /**
     * 3.5、根据用户名查询用户
     * @param username
     * @return
     */
    @Override
    public Hr findOneHrByUserName(String username) {
        return hrMapper.findOneHrByUserName(username);
    }




    /* 4、修改--------------------------------------------*/
    @Override
    public int updateHrInfo(Hr hr) {
        return hrMapper.updateHrInfo(hr);
    }

}
