package com.linmo.apsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_in, R.id.btn_entry})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_entry:
                Log.d("entry", "onClick: entry" );
                break;
            case R.id.btn_in:
                Log.d("signIn", "onClick: signIn");
                break;
        }
    }
}