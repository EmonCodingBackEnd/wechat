package com.coding.wechat.component.ftp.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.net.ftp.FTPFileFilter;

import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListParam extends BaseParam {
    private int limit = 1024;
    private Pattern filenamePattern = null;
    private FilenameFilter filenameFilter = null;
    private FTPFileFilter ftpFileFilter = null;
}
