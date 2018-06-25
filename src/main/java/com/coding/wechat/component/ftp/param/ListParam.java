package com.coding.wechat.component.ftp.param;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ListParam extends BaseParam {
    private int limit = 1024;
}
