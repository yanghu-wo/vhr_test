package com.study.vhr.service;

import com.study.vhr.entity.Hr;

import java.util.List;
import java.util.Set;

public interface IHrService {
    /* 1、增加--------------------------------------------*/
    int insertHr(Hr hr);

    /* 2、删除--------------------------------------------*/
    int delHrById(int id);

    /* 3、查询--------------------------------------------*/
    /**
     * 3.1查询用户根据id
     * @param id
     * @return
     */
    Hr findOneHrById(int id);

    /**
     * 3.2、查询所有用户
     * @return
     */
    List<Hr> findAllHr();

    /**
     * 3.3、返回用户角色
     * @param username
     * @return
     */
    Set<String> getHrRoleByName(String username);

    /**
     * 3.4、用户权限
     * @param username
     * @return
     */
    Set<String> getHrPermissions(String username);

    /**
     * 3.5、根据用户名查询用户
     * @param username
     * @return
     */
    Hr findOneHrByUserName(String username);



    /* 4、修改--------------------------------------------*/
    int updateHrInfo(Hr hr);



}
