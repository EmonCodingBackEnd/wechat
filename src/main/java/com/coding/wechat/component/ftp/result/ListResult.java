package com.coding.wechat.component.ftp.result;

import com.coding.wechat.component.ftp.config.ServerConfig;
import com.coding.wechat.component.ftp.param.ListParam;
import com.coding.wechat.component.ftp.property.ReplayCode;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
public class ListResult {
    private static final Integer SUCCESS_CODE = 2000;
    private static final String SUCCESS_MSG = "列表查询成功";
    private static final Integer FAILURE_CODE = 9999;
    private static final String FAILURE_MSG = "列表查询失败";

    private ServerConfig serverConfig;
    private ListParam listParam;

    private boolean success;
    private Integer errorCode;
    private String errorMessage;
    private List<String> filenameList = new ArrayList<>();

    public static ListResult newSuccess() {
        ListResult listResult = new ListResult();
        listResult.setSuccess(true);
        listResult.setErrorCode(SUCCESS_CODE);
        listResult.setErrorMessage(SUCCESS_MSG);
        return listResult;
    }

    public static ListResult newFailure() {
        ListResult listResult = new ListResult();
        listResult.setSuccess(false);
        listResult.setErrorCode(FAILURE_CODE);
        listResult.setErrorMessage(FAILURE_MSG);
        return listResult;
    }

    public static ListResult newFailure(ReplayCode replayCode) {
        ListResult listResult = new ListResult();
        listResult.setSuccess(false);
        listResult.setErrorCode(replayCode.getCode());
        listResult.setErrorMessage(replayCode.getMsg());
        return listResult;
    }

    public void addFilename(String filename) {
        this.filenameList.add(filename);
    }

    public void addAll(String[] filenames) {
        this.filenameList.addAll(Arrays.asList(filenames));
    }

    public void addAll(List<String> filenameList) {
        this.filenameList.addAll(filenameList);
    }
}
