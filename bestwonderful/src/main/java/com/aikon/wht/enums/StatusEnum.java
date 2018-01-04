package com.aikon.wht.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 基本状态enum.
 *
 * @author haitao.wang
 */
@AllArgsConstructor
public enum StatusEnum {

    // 无效
    INVALID(0, "无效"),
    // 有效
    VALID(1, "有效"),
    ;

    public @Getter
    Integer code;
    public @Getter String desc;
}
