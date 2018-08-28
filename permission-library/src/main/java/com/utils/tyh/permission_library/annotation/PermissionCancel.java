package com.utils.tyh.permission_library.annotation;

import com.utils.tyh.permission_library.util.PermissionUtil;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>@author : tyh</p>
 * <p>@time : 2018/08/24</p>
 * <p>@for : 权限请求被取消</p>
 * <p></p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PermissionCancel {
    int requestCode() default PermissionUtil.DEFAULT_REQUEST_CODE;
}
