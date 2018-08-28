package com.utils.tyh.permission_library.aop;

import android.content.Context;
import android.os.Build;

import com.utils.tyh.permission_library.interf.IPermissionCallback;
import com.utils.tyh.permission_library.PermissionActivity;
import com.utils.tyh.permission_library.util.PermissionUtil;
import com.utils.tyh.permission_library.annotation.Permission;
import com.utils.tyh.permission_library.annotation.PermissionCancel;
import com.utils.tyh.permission_library.annotation.PermissionDeny;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.List;

/**
 * <p>@author : tyh</p>
 * <p>@time : 2018/08/24</p>
 * <p>@for : 权限切面</p>
 * <p></p>
 */
@Aspect
public class PermissionAspect {

    /**
     * @Pointcut 注解是创建切面的注解,
     * 其后的括号里面是一个正则表达式,
     * 用于找到创建切面的位置
     */
    @Pointcut("execution(@com.utils.tyh.permission_library.annotation.Permission * *(..)) && @annotation(permission)")
    public void requestPermission(Permission permission){
    }

    /**
     * @param joinPoint     切入点
     * @param permission    注解
     *
     */
    @Around("requestPermission(permission)")
    public void handlePermission(final ProceedingJoinPoint joinPoint, final Permission permission) throws Throwable{
        if (permission.value().length==0) {
            joinPoint.proceed();
            return;
        }
        final Object object = joinPoint.getThis();
        Context context = null;
        if (object instanceof Context) {
            context = (Context) object;
        }else if (object instanceof android.support.v4.app.Fragment){
            context = ((android.support.v4.app.Fragment) object).getActivity();
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB&&object instanceof android.app.Fragment) {
            context = ((android.app.Fragment) object).getActivity();
        }
        if (context == null) {
            return;
        }

        if (PermissionUtil.hasPermission(context,permission.value())) {
            //如果已经获取到权限,可以接返回
            joinPoint.proceed();
            return;
        }

        PermissionActivity.requestPermission(context, permission.requestCode(), permission.value(), new IPermissionCallback() {
            @Override
            public void succeed() {
                try {
                    joinPoint.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }

            @Override
            public void canceled(int code, List<String> canceledList) {
                PermissionUtil.getMethodByAnnotationAndCode(object,PermissionCancel.class,code,canceledList);
            }

            @Override
            public void deny(int code,List<String> deniedList) {
                PermissionUtil.getMethodByAnnotationAndCode(object,PermissionDeny.class,code,deniedList);
            }
        });
    }


}
