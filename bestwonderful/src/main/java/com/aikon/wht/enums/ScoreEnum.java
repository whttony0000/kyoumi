package com.aikon.wht.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分值奖励enum.
 *
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
