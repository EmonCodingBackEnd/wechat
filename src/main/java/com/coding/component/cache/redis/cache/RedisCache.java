package com.coding.component.cache.redis.cache;

import com.coding.component.cache.redis.RedisKeyType;
import com.coding.component.security.auth.AuthService;
import com.coding.component.security.auth.LoginSession;
import com.coding.component.security.auth.SystemInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RedisCache {
    @Autowired AuthService authService;

    private static StringRedisTemplate redisTemplate;

    @Autowired
    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        RedisCache.redisTemplate = redisTemplate;
    }

    @Cacheable(
        value = "db1",
        key =
                "T(com.coding.component.cache.redis.RedisKeyType).USERINFO_SESSION.key + T(String).valueOf(#userId)"
    )
    public LoginSession userSession(Long userId) {
        LoginSession loginSession = authService.loginSession(userId);
        log.info("【缓存】载入用户会话信息到缓存中, key={}{}", RedisKeyType.USERINFO_SESSION.getKey(), userId);
        return loginSession;
    }

    @Cacheable(
        value = "system",
        key = "T(com.coding.component.cache.redis.RedisKeyType).SYSTEM_INFO.key"
    )
    public SystemInfo systemInfo() {
        SystemInfo systemInfo = authService.systemInfo();
        log.info("【缓存】载入系统信息到缓存中, key={}{}", RedisKeyType.SYSTEM_INFO.getKey());
        return systemInfo;
    }

    public static void deleteInfo(String key) {
        if (redisTemplate.opsForValue().getOperations().hasKey(key)) {
            redisTemplate.opsForValue().getOperations().delete(String.valueOf(key));
            log.info("【缓存】删除缓存信息, key={}{}", RedisKeyType.USERINFO_SESSION.getKey(), key);
        } else {
            log.info("【缓存】删除缓存信息, 缓存不存在, key={}", key);
        }
    }
}
