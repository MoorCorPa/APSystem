package com.linmo.apsystem.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.linmo.apsystem.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignActivity extends AppCompatActivity {
    private static final String TAG = null;
    // 获取控件
    @BindView(R.id.sign)
    Button sing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        // 绑定处理
        ButterKnife.bind(this);
    }

    @OnClick(R.id.sign)
    public void onViewClicked() {

        Log.d(TAG, "onViewClicked: 这是一个测试");
    }
}