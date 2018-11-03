package com.coding.wechat.component.security;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CustomMenu {

    public CustomMenu(Long id, String urlPattern) {
        this.id = id;
        this.urlPattern = urlPattern;
    }

    private Long id;

    /** 菜单匹配的API模式 */
    private String urlPattern;

    /** 包含该菜单的所有角色. */
    List<CustomRole> roleList = new ArrayList<>();
}
