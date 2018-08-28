package com.utils.tyh.permission_library.annotation;

import com.utils.tyh.permission_library.util.PermissionUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>@author : tyh</p>
 * <p>@time : 2018/08/24</p>
 * <p>@for : 请求权限列表注解</p>
 * <p></p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Permission {
    String[] value();
    int requestCode() default PermissionUtil.DEFAULT_REQUEST_CODE;
}
