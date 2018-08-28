# PermissionForAndroid
使用AOP注解,动态申请权限,可以在Activity,Fragment和Service中直接使用

#### 使用方法(Activity,Fragment,Service都一样)

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

          /**
           * 请求权限-->在需要请求权限的方法上添加这个注解
           * requestCode 请求码-->可以不写
           * value 权限列表
           */
          @Permission(requestCode = 100, value = {Manifest.permission.ACCESS_COARSE_LOCATION})
          public void requestLocation(View view) {
              // 申请权限完成的逻辑
              Toast.makeText(this, "获取定位权限成功", Toast.LENGTH_SHORT).show();
          }

          /**
           * 请求权限
           */
          @Permission({Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA})
          public void requestPermission(View view) {
              Toast.makeText(this, "获取相机和文件读写权限成功", Toast.LENGTH_SHORT).show();
          }

          /**
           * 权限取消
           *
           * @param code         请求码
           * @param canceldeList 被取消的权限列表
           */
          @PermissionCancel
          public void onCancle(int code, List<String> canceldeList) {
              Toast.makeText(this, "请求权限取消" + code, Toast.LENGTH_SHORT).show();
              //用户点击了取消,并且没有勾选不再提示选框
              //这里可以弹出提示权限作用的对话框
          }


          /**
           * 权限被拒绝
           *
           * @param code       请求码
           * @param deniedList 被拒绝的权限(如果同时请求了多个权限,有一个权限被拒绝,
           *                    那么所有没有同意的权限都会存在于列表中)
           */
          @PermissionDeny
          public void onDeny(int code, List<String> deniedList) {
              //用户点击了取消,并且勾选了不再提示选框
              //跳转到设置界面
              PermissionUtil.goToMenu(this);
              Toast.makeText(this, ("拒绝了: " + code) + "  " + deniedList.toString() + " 等权限", Toast.LENGTH_SHORT).show();
          }

      }
