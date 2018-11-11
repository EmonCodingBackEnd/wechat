package com.coding.wechat.exception;

import com.coding.component.system.api.AppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常拦截器，统一处理异常应答.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180502 19:41</font><br>
 * [请在此输入功能详述]
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
@ControllerAdvice
@Slf4j
public class ExceptionInterceptor {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public AppResponse handle(Exception e) {
        AppResponse appResponse = new AppResponse();
        if (e instanceof WechatException) {
            WechatException crmException = (WechatException) e;
            appResponse.setErrorCode(crmException.getCode());
            appResponse.setErrorMessage(crmException.getMessage());
        } else {
            log.error("【系统异常】", e);
            appResponse.setErrorCode(WechatStatus.SYSTEM_ERROR.getCode());
            appResponse.setErrorMessage("服务端错误");
        }
        return appResponse;
    }
}
