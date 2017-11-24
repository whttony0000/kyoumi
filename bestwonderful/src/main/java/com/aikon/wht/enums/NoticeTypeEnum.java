package com.aikon.wht.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author haitao.wang
 */
@AllArgsConstructor
public enum NoticeTypeEnum {

    // 关注者新建文章
    CREATE_ARTICLE(1),
    // 本人文章收到回复
    ARTICLE_COMMENTED(2),
    // 回复收到回复
    COMMENT_COMMENTED(3),
    // 新关注者
    NEW_FAN(4),
    ;

    private @Getter Integer code;

}
