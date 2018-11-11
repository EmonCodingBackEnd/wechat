/*
package com.coding.component.cache.redis.aspect;

import com.coding.component.cache.redis.annotation.RedisKeyCapturer;
import com.coding.component.cache.redis.cache.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;

@Aspect
@Component
@Slf4j
public class RedisAspect {

    // @Pointcut("execution(* com.ishanshan.eden.service..*(..)) && @annotation(redisAnnotation)")

    @Pointcut("@annotation(redisKeyCapturer)")
    public void publicMethods(RedisKeyCapturer redisKeyCapturer) {}

    @AfterReturning(value = "publicMethods(redisKeyCapturer)", argNames = "jp,redisKeyCapturer")
    public void instrumentMetered(JoinPoint jp, RedisKeyCapturer redisKeyCapturer) {
        Object[] args = jp.getArgs();
        for (Object obj : args) {
            Class clas = obj.getClass();
            if (!ClassUtils.isPrimitiveOrWrapper(clas) && !"String".equals(clas.getName())) {
                Field[] fls = clas.getDeclaredFields();
                for (Field f : fls) {
                    f.setAccessible(true);
                    if (redisKeyCapturer.value().equals(f.getName())) {
                        try {
                            String key = redisKeyCapturer.prefix() + String.valueOf(f.get(obj));
                            RedisCache.deleteInfo(key);
                        } catch (IllegalAccessException e) {
                            log.error("【redis切面】异常", e);
                        }
                    }
                }

            } else {
                if (redisKeyCapturer.value().equals(String.valueOf(obj))) {
                    String key = redisKeyCapturer.prefix() + String.valueOf(obj);
                    RedisCache.deleteInfo(key);
                }
            }
        }
    }
}
*/
