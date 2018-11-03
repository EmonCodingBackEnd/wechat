/*
 * 文件名称：CustomWebSecurityConfigurerAdapter.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181103 09:59
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181103-01         Rushing0711     M201811030959 新建文件
 ********************************************************************************/
package com.coding.wechat.component.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired private CustomUserDetailsService customUserDetailsService;

    @Autowired private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomFilterInvocationSecurityMetadataSource
            customFilterInvocationSecurityMetadataSource;

    @Autowired CustomAccessDecisionManager customAccessDecisionManager;

    @Autowired private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/templates/**", "/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.authorizeRequests()
                .withObjectPostProcessor(
                        new ObjectPostProcessor<FilterSecurityInterceptor>() {
                            @Override
                            public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                                object.setSecurityMetadataSource(
                                        customFilterInvocationSecurityMetadataSource);
                                object.setAccessDecisionManager(customAccessDecisionManager);
                                return object;
                            }
                        })
                .and()
                .formLogin()
                .loginPage("/login_prepare")
                .loginProcessingUrl("/login") // 登录请求路径
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler) // 验证成功处理器
                .failureHandler(customAuthenticationFailureHandler) // 验证失败处理器
                .and()
                .authorizeRequests()
                .antMatchers("/login") // 登录请求路径不进行过滤
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .logout()
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll()
                .and()
                .csrf()
                .disable() // 取消跨站请求伪造防护
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler); // 权限不足
    }
}
