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
package com.coding.component.security;

import com.coding.component.security.jwt.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true) // 启用权限认证的注解方式
public class CustomWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired CustomWebAuthenticationDetailsSource customWebAuthenticationDetailsSource;

    @Autowired private CustomUserDetailsService customUserDetailsService;

    @Bean
    public CustomDaoAuthenticationProvider customDaoAuthenticationProvider() {
        CustomDaoAuthenticationProvider provider = new CustomDaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }

    @Autowired private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    @Autowired private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    private CustomFilterInvocationSecurityMetadataSource
            customFilterInvocationSecurityMetadataSource;

    @Autowired CustomAccessDecisionManager customAccessDecisionManager;

    @Autowired private CustomUnauthorizedEntryPoint customUnauthorizedEntryPoint;

    @Autowired private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Autowired private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // [lm's ps]: 20181108 18:52 非自定义Provider的使用方式
        /*auth.userDetailsService(customUserDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());*/

        // [lm's ps]: 20181108 18:52 自定义Provider的使用方式
        auth.authenticationProvider(customDaoAuthenticationProvider()); // 自定义Dao认证提供者
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/templates/**", "/static/**", "/pub/**", "/auth/switchSystem/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);
        http.exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler) // 权限不足
                .authenticationEntryPoint(customUnauthorizedEntryPoint) // 未认证
                .and()
                .cors() // CORS - Cross Origin Resourse-Sharing - 跨站资源共享
                .and()
                .csrf() // CSRF - Cross-Site Request Forgery - 跨站请求伪造
                .disable() // 取消跨站请求伪造防护：由于使用的是JWT，我们这里不需要csrf
                .authorizeRequests()
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
                .authorizeRequests()
                .anyRequest()
                .authenticated() // access和authenticated二选一
                /*.access(
                "@rbacauthorityservice.hasPermission(request,authentication)") // 使用rbac角色绑定资源的方式：access和authenticated二选一*/
                .and()
                .addFilterAt(
                        customUsernamePasswordAuthenticationFilter(this.authenticationManager()),
                        UsernamePasswordAuthenticationFilter.class)
                /*.formLogin() // 自定义登录过滤器与默认登录过滤器二选一
                .loginProcessingUrl("/auth/login") // 登录请求路径
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(customAuthenticationSuccessHandler) // 验证成功处理器
                .failureHandler(customAuthenticationFailureHandler) // 验证失败处理器
                .authenticationDetailsSource(customWebAuthenticationDetailsSource)  // 登录请求的额外信息构建源
                .and()*/
                .logout()
                .logoutUrl("/auth/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 基于token，所以不需要session
                .and()
                .addFilterBefore(
                        authenticationTokenFilterBean(),
                        UsernamePasswordAuthenticationFilter.class) // 添加JWT filter
                .headers()
                .cacheControl(); // 禁用缓存
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter(
            AuthenticationManager authenticationManager) {
        CustomUsernamePasswordAuthenticationFilter filter =
                new CustomUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManager);
        filter.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/auth/login", "POST")); // 登录请求路径
        filter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler); // 验证成功处理器
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler); // 验证失败处理器
        filter.setAuthenticationDetailsSource(customWebAuthenticationDetailsSource); // 登录请求的额外信息构建源
        return filter;
    }
}
