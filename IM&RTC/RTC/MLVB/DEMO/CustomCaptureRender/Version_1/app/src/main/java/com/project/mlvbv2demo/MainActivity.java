package com.project.mlvbv2demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.project.mlvbv2demo.activity.CustomActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions(new String[]{Manifest.permission.CAMERA,  Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.RECORD_AUDIO
                ,  Manifest.permission.READ_PHONE_STATE});
    }

    /**
     * 获取多个权限
     *
     * @param permissions 获取权限的数组
     */
    private void requestPermissions(String[] permissions) {
        ActivityCompat.requestPermissions(this, permissions, 1);
    }

    public void btn(View view) {
        startActivity(new Intent(this, CustomActivity.class));
    }
}