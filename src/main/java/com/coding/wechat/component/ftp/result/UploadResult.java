package com.coding.wechat.component.ftp.result;

import com.coding.wechat.component.ftp.config.ServerConfig;
import com.coding.wechat.component.ftp.param.UploadParam;
import lombok.Data;

import java.util.Map;

@Data
public class UploadResult {

    private boolean hasFailure;
    private ServerConfig serverConfig;
    private UploadParam uploadParam;

    private Map<String, UploadResult> successResultMap;
    private Map<String, UploadResult> failureResultMap;

    public boolean hasFailure() {
        return hasFailure || !failureResultMap.isEmpty();
    }
}
