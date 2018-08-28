package com.utils.tyh.permission_library.menu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.utils.tyh.permission_library.menu.base.IMenu;

/**
 * <p>@author : tyh</p>
 * <p>@time : 2018/08/24</p>
 * <p>@for : $s$</p>
 * <p></p>
 */
public class HuaWei implements IMenu {
    @Override
    public Intent getMenuIntent(Context context) {
        Intent intent = new Intent(context.getPackageName());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
        intent.setComponent(comp);
        return null;
    }
}
