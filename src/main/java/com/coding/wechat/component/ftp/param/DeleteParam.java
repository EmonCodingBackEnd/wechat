package com.coding.wechat.component.ftp.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class DeleteParam extends BaseParam {

    /**
     * 删除FTP服务器目录下所有文件.
     *
     * <p>创建时间: <font style="color:#00FFFF">20180625 20:27</font><br>
     * 默认false， 指定后fileNameList无效
     *
     * @since 1.0.0
     */
    private boolean deleteAll = false;

    private List<String> fileNameList = new ArrayList<>();
}
