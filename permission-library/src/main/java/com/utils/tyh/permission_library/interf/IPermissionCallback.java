package com.utils.tyh.permission_library.interf;

import java.util.List;

/**
 * <p>@author : tyh</p>
 * <p>@time : 2018/08/24</p>
 * <p>@for : 权限请求回调</p>
 * <p></p>
 */
public interface IPermissionCallback {

    /**
     * 请求成功
     */
    void succeed();

    /**
     * 请求被取消
     */
    void canceled(int code, List<String> canceledList);

    /**
     * 请求被禁止了
     */
    void deny(int code,List<String> deniedList);
}
