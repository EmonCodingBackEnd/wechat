package com.coding.component.security.jwt;

import com.coding.component.security.CustomWebAuthenticationDetails;
import com.coding.component.security.CustomWebAuthenticationDetailsSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired private UserDetailsService userDetailsService;

    @Autowired private JwtTokenUtil jwtTokenUtil;

    @Autowired private StringRedisTemplate stringRedisTemplate;

    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails>
            authenticationDetailsSource;

    {
        // [lm's ps]: 20181108 18:52 非自定义Provider的使用方式
        //        authenticationDetailsSource = new WebAuthenticationDetailsSource();
        // [lm's ps]: 20181108 18:52 自定义Provider的使用方式
        authenticationDetailsSource = new CustomWebAuthenticationDetailsSource();
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String authHeader = request.getHeader(JwtTokenUtil.TOKEN_HEADER);
        if (authHeader != null && authHeader.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            final String authToken =
                    authHeader.substring(
                            JwtTokenUtil.TOKEN_PREFIX.length()); // The part after "Bearer "
            String username = jwtTokenUtil.getUsernameFromToken(authToken);
            logger.info("checking authentication " + username);

            if (username != null) {
                // Redis中是否还存在（比如登出删除/过期丢弃等）
                String redisKey = JwtRedisKeyUtil.getRedisKeyByUsername(username);
                boolean existAuthToken =
                        stringRedisTemplate.opsForValue().getOperations().hasKey(redisKey);
                if (existAuthToken
                        && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                    if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        userDetails.getPassword(),
                                        userDetails.getAuthorities());
                        authentication.setDetails(
                                authenticationDetailsSource.buildDetails(request));
                        // 存入当前有效Token
                        ((CustomWebAuthenticationDetails) authentication.getDetails())
                                .setToken(authToken);
                        logger.info(
                                "authenticated user " + username + ", setting security context");
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                }
            }
        }
        chain.doFilter(request, response);
    }
}
