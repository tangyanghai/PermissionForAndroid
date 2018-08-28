package com.utils.tyh.permission_library;

import android.annotation.NonNull;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.utils.tyh.permission_library.interf.IPermissionCallback;
import com.utils.tyh.permission_library.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>@author : tyh</p>
 * <p>@time : 2018/08/24</p>
 * <p>@for : $s$</p>
 * <p></p>
 */
public class PermissionActivity extends Activity {

    protected static final String KEY_REQUEST_CODE = "request_code";
    protected static final String KEY_REQUEST_ARR = "request_arr";
    private static IPermissionCallback mCallBack;

    public static void requestPermission(@NonNull Context context, int code, @NonNull String[] request_arr, IPermissionCallback callback) {
        mCallBack = callback;
        Intent intent = new Intent(context, PermissionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(KEY_REQUEST_CODE, code);
        intent.putExtra(KEY_REQUEST_ARR, request_arr);
        context.startActivity(intent);
        if (context instanceof Activity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
                ((Activity) context).overridePendingTransition(0, 0);
            }
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        int code = getIntent().getIntExtra(KEY_REQUEST_CODE, -1);
        String[] permissions = getIntent().getStringArrayExtra(KEY_REQUEST_ARR);
        ActivityCompat.requestPermissions(this, permissions, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @android.support.annotation.NonNull String[] permissions, @android.support.annotation.NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PermissionUtil.veryfyGrandResults(grantResults)) {
            mCallBack.succeed();
            finish();
            return;
        }

        List<String> cancelList = new ArrayList<>();
        //获取未被同意的权限集合
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                cancelList.add(permissions[i]);
            }
        }

        //勾选了不再显示的
        if (!PermissionUtil.shouldShowRequestPermissionRationale(this, cancelList)) {
            mCallBack.deny(requestCode,cancelList);
            finish();
            return;
        }

        mCallBack.canceled(requestCode,cancelList);
        finish();
    }

    @Override
    public void finish() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            overridePendingTransition(0, 0);
        }
        super.finish();
    }
}
