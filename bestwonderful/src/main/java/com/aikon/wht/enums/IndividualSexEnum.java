package com.aikon.wht.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 个人性别enum.
 *
 * @author haitao.wang
 */
@AllArgsConstructor
public enum IndividualSexEnum {

    // MALE
    MALE(0,"男"),
    // FEMALE
    FEMALE(1,"女"),
    ;

    @Getter Integer code;

    @Getter String desc;

}
