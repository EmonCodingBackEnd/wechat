package com.coding.wechat.component.security;

import java.io.Serializable;

/** Created by xiaogang on 2016/12/25. */
public class CustomResponse<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = -4605251603996640240L;

    protected Integer errorCode = 9000;

    protected String errorMessage = "成功";

    private String token;

    protected T data;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
