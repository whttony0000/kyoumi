package com.aikon.wht.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author haitao.wang
 */
@AllArgsConstructor
public enum IndividualLogTypeEnum {

    // CREATED
    CREATED(1, "创建"),
    // ACTIVE_MAIL_SENT
    SEND_ACTIVE_MAIL(2, "发送验证邮件"),
    // ACTIVE
    ACTIVE(3, "验证成功"),
    ;

    @Getter Integer code;

    @Getter String desc;
}
