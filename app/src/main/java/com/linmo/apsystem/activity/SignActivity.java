package com.linmo.apsystem.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.linmo.apsystem.R;
import com.linmo.apsystem.api.NetworkApi;
import com.linmo.apsystem.model.Result;
import com.linmo.apsystem.utils.AndroidScheduler;
import com.linmo.apsystem.utils.BaseUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SignActivity extends AppCompatActivity {
    private static final String TAG = null;
    // 获取控件
    @BindView(R.id.sign)
    Button sing;
    Button back;
    EditText et_personid;

    private Retrofit retrofit;
    private SharedPreferences address;
    private NetworkApi networkApi;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        // 绑定处理
        ButterKnife.bind(this);
    }

    //点击签到
    @OnClick(R.id.sign)
    public void SignIn() {
        // 获取工号内容
        String signid = "";
        EditText et_personid = (EditText) findViewById(R.id.et_personid);
        signid = et_personid.getText().toString();



        // 网络请求
//        getPhotoRg(signid,);
        Log.d(TAG, "SignIn: "+ signid);
    }

    // 回到上一个页面
    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    // 网络请求
    private void getPhotoRg(String personId, String base64Data) {
        networkApi.getPhotoRg(personId, base64Data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new SingleObserver<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onSuccess(@NonNull Result result) {
                        Log.d(TAG, "onSuccess: " + result.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }
}