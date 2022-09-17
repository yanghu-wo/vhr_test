package com.study.config.realm;

import com.study.vhr.entity.Hr;
import com.study.vhr.service.IHrService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @ClassName: UserRealme
 * @Author: LiuYun
 * @Description:
 * @Data: Create in 9:42 2022/9/3
 */
public class UserRealm extends AuthorizingRealm {

    @Resource
    private IHrService hrService;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = (String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        // 给该用户设置角色，角色信息存在role表中取
        authorizationInfo.setRoles(hrService.getHrRoleByName(username));
        // 给该用户设置权限，权限信息存在t_permission表中取
        authorizationInfo.setStringPermissions(hrService.getHrPermissions(username));
        return authorizationInfo;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String username = (String)authenticationToken.getPrincipal();
        Hr hr = hrService.findOneHrByUserName(username);
        if (hr != null){
            SecurityUtils.getSubject().getSession().setAttribute("user",hr);
            SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(hr.getUsername(),hr.getPassword(),this.getName());
            return authcInfo;
        }
        return null;
    }
}
