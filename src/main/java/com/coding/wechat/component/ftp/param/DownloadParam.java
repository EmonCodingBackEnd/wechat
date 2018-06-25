package com.coding.wechat.component.ftp.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class DownloadParam extends BaseParam {
    /**
     * key与value，分别表示下载后本地文件名和下载前服务器文件名.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180625 20:44</font><br>
     * [请在此输入功能详述]
     *
     * @since 1.0.0
     */
    private Map<String, String> filePairs;
}
