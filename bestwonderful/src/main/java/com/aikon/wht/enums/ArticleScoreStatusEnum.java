package com.aikon.wht.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author haitao.wang
 */
@AllArgsConstructor
public enum ArticleScoreStatusEnum {
    // NOT LIKE
    NOT_LIKE(-1,""),
    // NOTHING
    NOTHING(0, ""),
    // LIKE
    LIKE(1, ""),
    ;

    @Getter
    Integer code;

    @Getter
    String desc;
}
