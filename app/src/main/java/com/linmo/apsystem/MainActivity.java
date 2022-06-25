package com.linmo.apsystem;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.linmo.apsystem.activity.SignActivity;
import com.linmo.apsystem.activity.entry.EntryActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)//判断是否获得权限
        {  ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);}//获取权限

    }

    @OnClick({R.id.btn_in, R.id.btn_entry})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_entry:
                startActivity(new Intent(this, EntryActivity.class));
                Log.d("entry", "onClick: entry" );
                break;
            case R.id.btn_in:
                startActivity(new Intent(this, SignActivity.class));
                Log.d("signIn", "onClick: signIn");
                break;
        }
    }
}