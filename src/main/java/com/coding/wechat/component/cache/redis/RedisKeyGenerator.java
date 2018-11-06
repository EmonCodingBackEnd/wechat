/*
 * 文件名称：RedisKeyGenerator.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：Copyright (c) 2011-2018, liming20110711@163.com All Rights Reserved.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20181106 10:49
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20181106-01         Rushing0711     M201811061049 新建文件
 ********************************************************************************/
package com.coding.wechat.component.cache.redis;

import com.google.common.hash.Hashing;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.nio.charset.Charset;

@Component
@Slf4j
public class RedisKeyGenerator implements KeyGenerator {

    @Value("${redis.key.prefix:eden}")
    private String prefix;

    private static final String delimiter = ":";

    private static final int NO_PARAM_KEY = 0;

    private static final int NULL_PARAM_KEY = 53;

    /**
     * 生成RedisKey.
     *
     * <p>创建时间: <font style="color:#00FFFF">20181106 12:20</font><br>
     *
     * <ul>
     *   <li>
     *       <h3>命名组成</h3>
     *       <br>
     *       <div> <span color="#00FFFF">key= 系统名:服务名:用途:数据类型:业务值</span> <br>
     *       示例1： eden:user:token:string:&lt;username&gt;<br>
     *       示例2： eden:user:cache:hash:&lt;user&gt;<br>
     *       示例3： eden:order:lock:string:&lt;order_id&gt;<br>
     *       </div>
     *   <li>
     *       <h3>key采用小写结构，不采用驼峰命名方式；中间可以用下划线_ 连接</h3>
     *       <br>
     *       <div>示例1： saas_eden:order:lock:string:<order_id></div>
     *   <li>
     *       <h3>key的长度限制在128字节之内，既可以节省空间，又可以加快查询速度</h3>
     * </ul>
     *
     * @param serverName - 服务名：比如用户(user),字典(dict),系统参数(param)等
     * @param purpose - 用途：比如 token:授权用 cache:缓存用 lock:锁定用 等等
     * @param dataType - 数据类型：Redis的数据类型 字符串类型(string),散列类型(hash),列表类型(list),集合类型(set),有序集合类型(zset)
     * @param businessKey - 业务值：比如用户的ID，商品的ID等具有特殊标识的数据
     * @return java.lang.String
     * @author Rushing0711
     * @since 1.0.0
     */
    public String generate(String serverName, String purpose, String dataType, String businessKey) {
        Object key = generate(null, null, serverName, purpose, dataType, businessKey);
        return key.toString();
    }

    @Override
    public Object generate(Object target, Method method, Object... params) {
        StringBuilder key = new StringBuilder();
        key.append(prefix).append(delimiter);
        if (target != null && method != null) {
            key.append(target.getClass().getSimpleName())
                    .append(".")
                    .append(method.getName())
                    .append(delimiter);
        }
        if (params.length == 0) {
            return key.append(NO_PARAM_KEY).toString();
        }
        for (Object param : params) {
            if (param == null) {
                log.warn("input null param for Spring cache, use default key={}", NULL_PARAM_KEY);
                key.append(NULL_PARAM_KEY);
            } else if (ClassUtils.isPrimitiveArray(param.getClass())) {
                int length = Array.getLength(param);
                for (int i = 0; i < length; i++) {
                    key.append(Array.get(param, i));
                    key.append(',');
                }
            } else if (ClassUtils.isPrimitiveOrWrapper(param.getClass())
                    || param instanceof String) {
                key.append(param);
            } else {
                StringBuilder warnMsg = new StringBuilder();
                warnMsg.append("Using an object as a cache key may lead to unexpected results. ");
                warnMsg.append("Either use @Cacheable(key=..) or implement CacheKey. ");
                if (target != null && method != null) {
                    warnMsg.append(
                            String.format("Method is %s#%s", target.getClass(), method.getName()));
                }
                log.warn(warnMsg.toString());
                key.append(param.hashCode());
            }
            key.append(delimiter);
        }
        String finalKey = key.deleteCharAt(key.lastIndexOf(delimiter)).toString();
        long cacheKeyHash =
                Hashing.murmur3_128().hashString(finalKey, Charset.defaultCharset()).asLong();
        log.debug("using cache key={} hashCode={}", finalKey, cacheKeyHash);
        return finalKey;
    }
}
