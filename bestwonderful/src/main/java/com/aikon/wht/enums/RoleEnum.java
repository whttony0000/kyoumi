package com.aikon.wht.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 角色种类enum.
 *
 * @author haitao.wang
 */
@AllArgsConstructor
public enum RoleEnum {

    // ADMIN
    ADMIN(1, "admin"),
    // USER
    ACTIVE_USER(2, "user"),
    // TMP
    TMP_USER(3, "tmpUser"),
    // GUEST
    GUEST(4, "guest"),
    ;

    @Getter Integer code;

    @Getter String desc;

    public static String getDesc(Integer code) {
        RoleEnum[] roleEnums = RoleEnum.values();
         RoleEnum val = Arrays.stream(roleEnums).filter(roleEnum -> code.equals(roleEnum.getCode())).findFirst().orElse(null);
        if (val == null) {
            return null;
        }
        return val.getDesc();
    }

}
