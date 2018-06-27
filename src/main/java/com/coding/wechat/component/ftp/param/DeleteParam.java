package com.coding.wechat.component.ftp.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.commons.net.ftp.FTPFileFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class DeleteParam extends ListableParam {}
