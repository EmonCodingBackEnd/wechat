package com.coding.wechat.component.security;

import com.coding.wechat.component.jwt.JwtRedisKeyUtil;
import com.coding.wechat.component.jwt.JwtTokenUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired private ObjectMapper objectMapper; // Json转化工具
    @Autowired private JwtTokenUtil jwtTokenUtil;
    @Autowired private StringRedisTemplate stringRedisTemplate;

    @Override
    public void onLogoutSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        log.info("登出成功");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8"); // 响应类型

        CustomResponse appResponse = new CustomResponse();
        appResponse.setErrorCode(5100);
        appResponse.setErrorMessage("尚未认证！");

        String authHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        if (authHeader != null && authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            final String authToken =
                    authHeader.substring(
                            JwtTokenUtil.TOKEN_PREFIX.length()); // The part after "Bearer "
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            if (username != null) {
                String redisKey = JwtRedisKeyUtil.getRedisKeyByUsername(username);
                if (stringRedisTemplate.opsForValue().getOperations().hasKey(redisKey)) {
                    stringRedisTemplate.opsForValue().getOperations().delete(redisKey);
                }
                appResponse.setErrorCode(9000);
                appResponse.setErrorMessage("登出成功！");
            }
        }
        response.getWriter().write(objectMapper.writeValueAsString(appResponse));
    }
}
