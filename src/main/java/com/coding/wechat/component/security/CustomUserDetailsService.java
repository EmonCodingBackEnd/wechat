/*
 * 文件名称：CustomUserDetailsService.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181103 09:58
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181103-01         Rushing0711     M201811030958 新建文件
 ********************************************************************************/
package com.coding.wechat.component.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 打印前端传过来的用户数据
        log.info("前端请求的用户名：{}", username);

        // 模拟数据库中的数据
        String password = passwordEncoder.encode("123456");

        // 查询用户所有的角色
        List<CustomRole> roleList = new ArrayList<>();
        roleList.add(new CustomRole(1007439783886909440L, "角色A", 1));
        roleList.add(new CustomRole(1007439907706957824L, "角色B", 2));
        roleList.add(new CustomRole(1011921894543003648L, "角色C", 3));

        // 封装成权限列表
        List<GrantedAuthority> authorityList = new ArrayList<>();
        for (CustomRole role : roleList) {
            authorityList.add(new SimpleGrantedAuthority(String.valueOf(role.getId())));
        }

        // 返回一个User对象（技巧01：这个User对象的密码是从数据库中取出来的密码）
        //      // 技巧02：数据库中的密码是在创建用户时将用户的密码利用SpreingSecurity配置中相同的密码加密解密工具加密过的
        return new User(username, password, authorityList);
    }
}
