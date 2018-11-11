/*
 * 文件名称：AuthServiceImpl.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181109 13:50
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181109-01         Rushing0711     M201811091350 新建文件
 ********************************************************************************/
package com.coding.component.security.auth;

import com.coding.component.cache.redis.RedisKeyType;
import com.coding.component.cache.redis.annotation.RedisKeyCapturer;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    /** 模拟用户可访问的门店. */
    private Long shopId = 1L;
    /** 模拟用户可访问的系统类型. */
    private Integer systemType = 1;

    public static final String ADMIN = "admin";
    public static final String EMPTY = "";

    @Autowired JdbcTemplate jdbcTemplate;

    @Override
    public LoginAuthority login(String authType, String username) {
        LoginAuthority loginAuthority = new LoginAuthority();

        // 封装
        loginAuthority.setUserId(1000010000L);
        loginAuthority.setUsername("测试用户名称");
        loginAuthority.setPassword("$2a$10$0XqnxQjjK48jqAKA9QvgBOYEGpvVTB5/uVC2/n79P8BxutO3hIAra");
        loginAuthority.setRoleIdList(Lists.newArrayList("1", "2", "3"));
        return loginAuthority;
    }

    @Override
    public LoginSession loginSession(Long userId) {
        LoginSession loginSession = new LoginSession();

        loginSession.setUserId(1000010000L);
        loginSession.setTenantId(10000);
        loginSession.setCurrentShopId(shopId);
        loginSession.setCurrentSystem(systemType);
        loginSession.setUsername("测试用户名称");
        loginSession.setPassword("$2a$10$0XqnxQjjK48jqAKA9QvgBOYEGpvVTB5/uVC2/n79P8BxutO3hIAra");
        loginSession.setSex(1);
        loginSession.setAge(18);
        loginSession.setIsAdmin(1);
        loginSession.setMobile("18767188240");
        loginSession.setSystemTypeList(Lists.newArrayList("1", "2"));
        loginSession.setShopIdList(Lists.newArrayList("1", "2", "3"));
        loginSession.setMenuIdList(Lists.newArrayList("1", "2", "3", "4", "5"));

        // 封装
        LoginAuthority loginAuthority = new LoginAuthority();
        loginAuthority.setUserId(userId);
        loginAuthority.setUsername("真实名称:" + userId);
        loginAuthority.setPassword("$2a$10$0XqnxQjjK48jqAKA9QvgBOYEGpvVTB5/uVC2/n79P8BxutO3hIAra");
        loginAuthority.setRoleIdList(Lists.newArrayList("1", "2", "3"));
        loginSession.setLoginAuthority(loginAuthority);

        return loginSession;
    }

    @Transactional
    @Override
    @RedisKeyCapturer(value = "userId", prefix = RedisKeyType.USERINFO_SESSION)
    public void switchSystem(Long userId, Integer targetSystem) {
        systemType = targetSystem;
    }

    @Transactional
    @Override
    public void switchShop(Long userId, Long targetShop) {
        shopId = targetShop;
    }

    @Override
    public SystemInfo systemInfo() {
        SystemInfo systemInfo = new SystemInfo();

        // 刷新菜单信息
        SystemInfo.MenuDTO menuDTO = new SystemInfo.MenuDTO();
        menuDTO.setMenuId(1L);
        menuDTO.setSystemType(1);
        menuDTO.setMenuKey("10100000");
        menuDTO.setMenuName("用户Token刷新");
        menuDTO.setMenuPath("1");
        menuDTO.setUrlPattern("/auth/refresh");
        menuDTO.setParentId(0L);
        menuDTO.getRoleList().addAll(Lists.newArrayList("1", "2"));

        systemInfo.getMenuDTOList().add(menuDTO);
        return systemInfo;
    }
}
