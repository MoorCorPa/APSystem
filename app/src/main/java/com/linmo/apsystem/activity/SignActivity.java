package com.linmo.apsystem.activity;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.linmo.apsystem.R;
import com.linmo.apsystem.api.NetworkApi;
import com.linmo.apsystem.model.RequestBody;
import com.linmo.apsystem.model.Result;
import com.linmo.apsystem.utils.AndroidScheduler;
import com.linmo.apsystem.utils.BaseUtils;
import com.linmo.apsystem.utils.Camera2Helper;
import com.linmo.apsystem.utils.ToastUtils;
import com.linmo.apsystem.view.AutoFitTextureView;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignActivity extends AppCompatActivity {
    private static final String TAG = null;

    // 获取控件
    // 拍照签到
    @BindView(R.id.sign)
    Button sing;
    // 输入框
    @BindView(R.id.et_personid)
    EditText et_personid;
    // 视频框
    @BindView(R.id.entry_surfaceView)
    AutoFitTextureView textureView;
    // 回到上一个页面
    @BindView(R.id.back)
    Button back;

    private SharedPreferences address;
    private NetworkApi networkApi;
    private Camera2Helper helper;
    private String base64Data;
    private Retrofit retrofit;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        // 绑定处理
        ButterKnife.bind(this);
        // 实时监控
        init();
    }

    //点击签到
    @OnClick(R.id.sign)
    void SignIn() {
        // 获取工号内容
        String signid = "";
        signid = et_personid.getText().toString();
        Log.d(TAG, "SignIn: "+ signid);

        // 网络请求
        getPhotoRg(signid,base64Data);
    }




    // 回到上一个页面
    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    // 网络请求（识别图片）
    private void getPhotoRg(String personId, String imgdata) {
        networkApi.getPhotoRg(personId, imgdata)
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

                        if ( result.getFace_distances() <= 0.38){
                            ToastUtils.show(SignActivity.this, "签到成功");
                        }

                        ToastUtils.show(SignActivity.this, "签到失败,精度为：" + result.getFace_distances()+"");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                        ToastUtils.show(SignActivity.this, e.getMessage());
                    }
                });
    }


    private void init(){
        helper = new Camera2Helper(this, textureView);
        helper.setImageFormat(ImageFormat.JPEG);
        address = getSharedPreferences("address", MODE_PRIVATE);
        String url = address.getString("address", "http://10.0.0.229:5001/"); //http://10.0.2.2:5001/ http://10.0.0.229:5001/
        gson = new Gson();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        networkApi = retrofit.create(NetworkApi.class);

        helper.setOnImageAvailableListener(new Camera2Helper.OnPreviewCallbackListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onImageAvailable(Image image) {
                Log.d(TAG, "helper onImageAvailable");
                base64Data = BaseUtils.image2base64(image);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        helper.open();  //会动态请求权限，请重写 onRequestPermissionsResult
    }

    @Override
    public void onPause() {
        helper.closeCamera();
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == helper.getCameraRequestCode()) {
            if (grantResults.length != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ToastUtils.show(this,  "我号了");
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}