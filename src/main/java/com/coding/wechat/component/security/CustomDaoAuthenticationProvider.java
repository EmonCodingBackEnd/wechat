/*
 * 文件名称：CustomLoginAuthenticationProvider.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181108 12:12
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181108-01         Rushing0711     M201811081212 新建文件
 ********************************************************************************/
package com.coding.wechat.component.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 自定义数据库认证提供者.
 *
 * <p>创建时间: <font style="color:#00FFFF">20181108 13:19</font><br>
 * 如果使用普通的用户名密码认证，可以不通过这里；如果使用特殊形式的认证，通过这里自定义实现。
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    public CustomDaoAuthenticationProvider() {
        super();
    }

    @Override
    protected void additionalAuthenticationChecks(
            UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
            throws AuthenticationException {

        Object salt = null;

        if (this.getSaltSource() != null) {
            salt = this.getSaltSource().getSalt(userDetails);
        }

        if (authentication.getCredentials() == null) {
            logger.debug("Authentication failed: no credentials provided");

            throw new BadCredentialsException(
                    messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials",
                            "Bad credentials"));
        }

        // 加入额外参数的校验
        CustomWebAuthenticationDetails details =
                (CustomWebAuthenticationDetails) authentication.getDetails();
        if (StringUtils.isEmpty(details.getAuthType())) {
            logger.debug("Authentication failed: no authType provided");
            throw new CustomAuthTypeAuthenticationException("no authType provided");
        }

        String presentedPassword = authentication.getCredentials().toString();

        if (!getPasswordEncoder()
                .isPasswordValid(userDetails.getPassword(), presentedPassword, salt)) {
            logger.debug("Authentication failed: password does not match stored value");

            throw new BadCredentialsException(
                    messages.getMessage(
                            "AbstractUserDetailsAuthenticationProvider.badCredentials",
                            "Bad credentials"));
        }
    }
}
