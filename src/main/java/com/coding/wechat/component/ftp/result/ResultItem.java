package com.coding.wechat.component.ftp.result;

import lombok.Data;

@Data
public class ResultItem {
    private static final String SUCCESS_MSG = "上传成功";
    private static final String FAILURE_MSG = "上传失败";

    private boolean success;
    private String errorMessage;
    private String originalFilename;
    private String virtualFilename;
    private String url;

    private ResultItem() {}

    public static ResultItem newSuccess() {
        ResultItem resultItem = new ResultItem();
        resultItem.setSuccess(true);
        resultItem.setErrorMessage(SUCCESS_MSG);
        return resultItem;
    }

    public static ResultItem newFailure() {
        ResultItem resultItem = new ResultItem();
        resultItem.setSuccess(false);
        resultItem.setErrorMessage(FAILURE_MSG);
        return resultItem;
    }
}
