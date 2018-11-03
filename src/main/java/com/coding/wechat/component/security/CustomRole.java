package com.coding.wechat.component.security;

import lombok.Data;

@Data
public class CustomRole {

    public CustomRole(Long id, String name, Integer sortOrder) {
        this.id = id;
        this.name = name;
        this.sortOrder = sortOrder;
    }

    private Long id;

    /** 角色名称 */
    private String name;

    /** 排序值 */
    private Integer sortOrder;
}
