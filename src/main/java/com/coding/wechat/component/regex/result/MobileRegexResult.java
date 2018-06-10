package com.coding.wechat.component.regex.result;

import com.coding.wechat.component.regex.RegexResult;

public class MobileRegexResult extends RegexResult {
    private String mobile;
    private String mobileHead;
    private String mobileTail;

    public static MobileRegexResult instance() {
        return new MobileRegexResult();
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileHead() {
        return this.mobileHead;
    }

    public void setMobileHead(String mobileHead) {
        this.mobileHead = mobileHead;
    }

    public String getMobileTail() {
        return this.mobileTail;
    }

    public void setMobileTail(String mobileTail) {
        this.mobileTail = mobileTail;
    }
}
