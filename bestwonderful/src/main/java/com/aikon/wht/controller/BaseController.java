package com.aikon.wht.controller;

import com.aikon.wht.enums.RoleEnum;
import com.aikon.wht.exception.InactiveException;
import com.aikon.wht.exception.InvalidInputException;
import com.aikon.wht.exception.NoPermissionException;
import com.aikon.wht.utils.ShiroUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.subject.Subject;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * controller基类.
 *
 * @author haitao.wang
 */
@Slf4j
public class BaseController {

    /**
     * 是否临时用户.
     *
     * @throws InactiveException
     */
    public void isTmpUser() throws InactiveException {
        Subject subject = ShiroUtils.getSubject();
        if (subject.hasRole(RoleEnum.TMP_USER.getDesc())) {
            throw new InactiveException(subject.getPrincipal());
        }
    }

    /**
     * 是否管理员.
     *
     * @throws NoPermissionException
     */
    public void isAdmin() throws NoPermissionException {
        Subject subject = ShiroUtils.getSubject();
        if (!subject.hasRole(RoleEnum.ADMIN.getDesc())) {
            throw new NoPermissionException();
        }
    }

    /**
     * 输入校验.
     *
     * @param object
     * @param fieldNames
     * @throws InvalidInputException
     */
    public void checkInput(Object object, List<String> fieldNames) throws InvalidInputException {
        if (object == null || CollectionUtils.isEmpty(fieldNames)) {
            return;
        }
        Class<?> parentClass = object.getClass();
        while (parentClass != null) {
            Field[] fields = parentClass.getDeclaredFields();
            if (fields == null) {
                parentClass = parentClass.getSuperclass();
                continue;
            }
            AccessibleObject.setAccessible(fields, true);
            try {
                for (Field field : fields) {
                    if (field.get(object) == null) {
                        continue;
                    }
                    if (field.getType() != String.class) {
                        continue;
                    }
                    if (Modifier.isFinal(field.getModifiers())) {
                        continue;
                    }
                    if (!fieldNames.contains(field.getName())) {
                        continue;
                    }
                    String val = (String) field.get(object);
                    if (val.contains("$")) {
                        throw new InvalidInputException("输入包含非法字符($)");
                    }
                }
            } catch (IllegalAccessException e) {
                log.info("Error Accessing Field {}",e);
            }

            parentClass = parentClass.getSuperclass();
        }
    }

}
