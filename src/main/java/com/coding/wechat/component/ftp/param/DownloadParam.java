package com.coding.wechat.component.ftp.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.net.ftp.FTPFileFilter;

import java.util.Map;
import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class DownloadParam extends ListableParam {
    // 文件要下载到的本地目录
    private String localDirectory = "/fileserver/ftproot";
}
