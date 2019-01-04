package com.ctrip.train.kefu.system.offline.common.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;


import java.util.HashSet;
import java.util.Set;

@Component
public class UserRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
       // String staffNum = getEmpInfos().getExtNo();

        String role="rolsetest";
        String perm="permstest";

        Set<String> roleSet = new HashSet<>();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        roleSet.add(role);
        //设置该用户拥有的角色
        info.setRoles(roleSet);

        Set<String> permsSet = new HashSet<>();
        permsSet.add(perm);
        //设置该用户拥有的权限
        info.addStringPermissions(permsSet);
        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        return new SimpleAuthenticationInfo(token.getPrincipal(), "123456", getName());
    }
}
