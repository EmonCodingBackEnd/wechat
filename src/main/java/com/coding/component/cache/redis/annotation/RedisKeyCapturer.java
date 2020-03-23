package com.coding.component.cache.redis.annotation;

import com.coding.component.cache.redis.RedisKeyType;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisKeyCapturer {

    String value();

    RedisKeyType prefix();
}
