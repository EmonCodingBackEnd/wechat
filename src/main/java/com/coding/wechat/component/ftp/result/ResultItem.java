package com.coding.wechat.component.ftp.result;

import com.coding.wechat.component.ftp.property.ReplayCode;
import lombok.Data;

@Data
public class ResultItem {
    private static final Integer SUCCESS_CODE = 2000;
    private static final String SUCCESS_MSG = "上传成功";
    private static final Integer FAILURE_CODE = 9999;
    private static final String FAILURE_MSG = "上传失败";

    private boolean success;
    private Integer errorCode;
    private String errorMessage;
    private String originalFilename;
    private String virtualFilename;
    private String url;

    private ResultItem() {}

    public static ResultItem newSuccess() {
        ResultItem resultItem = new ResultItem();
        resultItem.setSuccess(true);
        resultItem.setErrorCode(SUCCESS_CODE);
        resultItem.setErrorMessage(SUCCESS_MSG);
        return resultItem;
    }

    public static ResultItem newFailure() {
        ResultItem resultItem = new ResultItem();
        resultItem.setSuccess(false);
        resultItem.setErrorCode(FAILURE_CODE);
        resultItem.setErrorMessage(FAILURE_MSG);
        return resultItem;
    }

    public static ResultItem newFailure(ReplayCode replayCode) {
        ResultItem resultItem = new ResultItem();
        resultItem.setSuccess(false);
        resultItem.setErrorCode(replayCode.getCode());
        resultItem.setErrorMessage(replayCode.getMsg());
        return resultItem;
    }
}
