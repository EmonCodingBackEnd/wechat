package com.coding.wechat.component.cache.redis;

import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

@Slf4j
public class CompressUtilTest {

    @Test
    public void test() throws Exception {
        String token = "eden:user:token:string:username";
        byte[] compressedBytes = CompressUtil.compress(token.getBytes());
        log.info(new String(compressedBytes));
        byte[] uncompressedBytes = CompressUtil.uncompress(compressedBytes);
        log.info(new String(uncompressedBytes));
        Assert.assertArrayEquals("压缩前与解压后不一致", token.getBytes(), uncompressedBytes);
    }
}
