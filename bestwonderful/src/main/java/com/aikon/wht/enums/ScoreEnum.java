package com.aikon.wht.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author haitao.wang
 */
@AllArgsConstructor
public enum ScoreEnum {
    // CREATE ARTICLE
    CREATE_ARTICLE(5, ""),
    ;

    @Getter
    Integer code;

    @Getter
    String desc;
}
