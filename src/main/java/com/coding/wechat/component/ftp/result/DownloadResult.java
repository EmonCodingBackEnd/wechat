package com.coding.wechat.component.ftp.result;

import com.coding.wechat.component.ftp.config.ServerConfig;
import com.coding.wechat.component.ftp.param.DeleteParam;
import com.coding.wechat.component.ftp.param.DownloadParam;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class DownloadResult {

    private ServerConfig serverConfig;
    private DownloadParam downloadParam;

    private List<DownloadResultItem> successList = new ArrayList<>();
    private List<DownloadResultItem> failureList = new ArrayList<>();

    public boolean hasFailure() {
        return !failureList.isEmpty();
    }

    public void addResultItem(DownloadResultItem item) {
        if (item.isSuccess()) {
            successList.add(item);
        } else {
            failureList.add(item);
        }
    }
}
