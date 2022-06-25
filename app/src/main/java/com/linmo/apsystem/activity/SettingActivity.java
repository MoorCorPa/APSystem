package com.linmo.apsystem.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;

import com.linmo.apsystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    @BindView(R.id.address)
    EditText address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_savesetting)
    private void save(){
        SharedPreferences preferences = getSharedPreferences("address", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("address", "http://"+address.getText().toString()+"/");
        editor.commit();
    }
}