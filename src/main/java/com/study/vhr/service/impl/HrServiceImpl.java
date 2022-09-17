package com.study.vhr.service.impl;

import com.study.vhr.entity.Hr;
import com.study.vhr.mapper.HrMapper;
import com.study.vhr.service.IHrService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

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
