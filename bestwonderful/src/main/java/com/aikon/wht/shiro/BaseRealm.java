package com.aikon.wht.shiro;

import com.aikon.wht.entity.Individual;
import com.aikon.wht.enums.IndividualStatusEnum;
import com.aikon.wht.enums.RoleEnum;
import com.aikon.wht.service.IndividualService;
import com.aikon.wht.utils.ShiroUtils;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * realm for shiro.
 *
 * @author haitao.wang
 */
@Slf4j
public class BaseRealm extends AuthorizingRealm {

    @Autowired
    IndividualService individualService;

    /**
     * 授权.
     *
     * @param principals
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Individual individual = (Individual) principals.getPrimaryPrincipal();
        log.info("authorization : {}.................,", individual.getMail());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setRoles(Sets.newHashSet(RoleEnum.getDesc(individual.getRole())));
        return info;
    }

    /**
     * 验权.
     *
     * @param token
     * @return AuthorizationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String mail = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());

        //查询用户信息
        Individual individual = individualService.getIndividualByMail(mail);

        //账号不存在
        if (individual == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }

        //密码错误
        if (!password.equals(individual.getPasswd())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }

        //账号锁定
        if (IndividualStatusEnum.LOCKED.getCode().equals(individual.getStatus())) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        Subject subject = ShiroUtils.getSubject();
        Session session = subject.getSession();
        Individual individualSession = new Individual();
        individualSession.setStatus(individual.getStatus());
        individualSession.setId(individual.getId());
        session.setAttribute("individual", individual);

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(individual, password, getName());
        return info;
    }
}
