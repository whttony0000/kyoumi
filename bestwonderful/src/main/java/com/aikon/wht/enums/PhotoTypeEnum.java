package com.aikon.wht.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author haitao.wang
 */
@NoArgsConstructor
@AllArgsConstructor
public enum PhotoTypeEnum {

    // 分类
    CATEGORY(1, "分类"),
    // 标签
    TAG(2, "标签"),
    // 个人
    INDIVIDUAL(3, "个人"),;

    public @Getter
    Integer code;
    public @Getter
    String desc;


}
