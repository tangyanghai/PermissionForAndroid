package com.utils.tyh.utils;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.utils.tyh.permission_library.annotation.Permission;
import com.utils.tyh.permission_library.annotation.PermissionCancel;
import com.utils.tyh.permission_library.annotation.PermissionDeny;
import com.utils.tyh.permission_library.util.PermissionUtil;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Permission(requestCode = 100,value = {Manifest.permission.ACCESS_COARSE_LOCATION})
    public void requestLocation(View view) {
        Toast.makeText(this, "获取定位权限成功", Toast.LENGTH_SHORT).show();
    }

    @Permission(value = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA},requestCode = 200)
    public void requestPermission(View view) {
        Toast.makeText(this, "获取相机和文件读写权限成功", Toast.LENGTH_SHORT).show();
    }

    @PermissionCancel
    public void onCancle(int code,List<String> canceldeList){
        Toast.makeText(this, "请求权限取消"+code, Toast.LENGTH_SHORT).show();
    }

    @PermissionDeny
    public void onDeny(int code, List<String> deniedList){
        PermissionUtil.goToMenu(this);
        Toast.makeText(this, ("拒绝了: "+code)+"  "+deniedList.toString()+" 等权限", Toast.LENGTH_SHORT).show();
    }

}
