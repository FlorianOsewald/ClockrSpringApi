package com.osewald.springrest.h2.auth;

import java.util.Collections;

import com.osewald.springrest.h2.controller.UserController;
import com.osewald.springrest.h2.model.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class ClockRRealm extends AuthorizingRealm {

    private UserController userController;

    @Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        final UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        final User user = this.userController.getUserByUsername(upToken.getUsername());

        if (user == null) {
            throw new AuthenticationException("User not found");
        }

        return new SimpleAccount(user, user.getPassword(), "ClockRRealm");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        final User user = (User) principals.getPrimaryPrincipal();
        return new SimpleAuthorizationInfo(Collections.singleton(user.getUserRolle().toString()));
    }
}
