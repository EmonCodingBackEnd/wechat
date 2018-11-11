/*
 * 文件名称：EnumUtil.java
 * 系统名称：[系统名称]
 * 模块名称：[模块名称]
 * 软件版权：杭州闪宝科技有限公司.
 * 功能说明：[请在此处输入功能说明]
 * 开发人员：Rushing0711
 * 创建日期：20180423 15:31
 * 修改记录：
 * <Version>        <DateSerial>        <Author>        <Description>
 * 1.0.0            20180423-01         Rushing0711     M201804231531 新建文件
 ********************************************************************************/
package com.coding.component.enums;

/**
 * 枚举类翻译工具.
 *
 * <p>创建时间: <font style="color:#00FFFF">20180423 15:31</font><br>
 *
 * <p>使用方式： <code>EnumUtils.getByCode(code, clazz)</code><br>
 * 其中clazz代表实现了BaseEnum接口的枚举类
 *
 * @author Rushing0711
 * @version 1.0.0
 * @since 1.0.0
 */
public class EnumUtils {

    public static <T extends BaseEnum> T getByCode(T code, Class<T> enumClass) {
        for (T each : enumClass.getEnumConstants()) {
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
