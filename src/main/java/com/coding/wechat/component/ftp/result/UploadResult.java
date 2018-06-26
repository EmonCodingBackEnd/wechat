package com.coding.wechat.component.ftp.result;

import com.coding.wechat.component.ftp.config.ServerConfig;
import com.coding.wechat.component.ftp.param.UploadParam;
import lombok.Data;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Data
public class UploadResult {

    private ServerConfig serverConfig;
    private UploadParam uploadParam;
    private ResultItem resultItem;

    private Map<String, ResultItem> successMap = new HashMap<>();
    private Map<String, ResultItem> failureMap = new HashMap<>();

    public boolean hasFailure() {
        return !failureMap.isEmpty();
    }

    public void addResultItem(ResultItem resultItem) {
        if (resultItem.isSuccess()) {
            successMap.put(resultItem.getOriginalFilename(), resultItem);
        } else {
            failureMap.put(resultItem.getOriginalFilename(), resultItem);
        }
    }
}
