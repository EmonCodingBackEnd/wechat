package com.coding.component.regex;

import com.coding.component.regex.result.MobileRegexResult;
import org.junit.Assert;
import org.junit.Test;

public class RegexSupportTest {

    @Test
    public void match$R() throws Exception {}

    @Test
    public void matchLogFormater() throws Exception {}

    @Test
    public void matchMobile() throws Exception {
        String mobile = "18767188240";
        MobileRegexResult result = RegexSupport.matchMobile(mobile);
        Assert.assertTrue("手机号校验失败", result.isMatched());
        if (result.isMatched()) {
            System.out.println(result.getMobileTail());
        }
    }
}
