package com.linmo.apsystem.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.TextureView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.linmo.apsystem.R;
import com.linmo.apsystem.api.NetworkApi;
import com.linmo.apsystem.model.RequestBody;
import com.linmo.apsystem.model.Result;
import com.linmo.apsystem.utils.AndroidScheduler;
import com.linmo.apsystem.utils.BaseUtils;
import com.linmo.apsystem.utils.Camera2Helper;
import com.linmo.apsystem.utils.ToastUtils;
import com.linmo.apsystem.utils.maxiaozhou1234.camera2.CameraConfig;
import com.linmo.apsystem.utils.maxiaozhou1234.camera2.CameraUtil;
import com.linmo.apsystem.utils.maxiaozhou1234.camera2.OnImageAvailableListener;
import com.linmo.apsystem.view.AutoFitTextureView;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;

public class EntryActivity extends AppCompatActivity {
    private static final String TAG = "EntryActivity";
    
    @BindView(R.id.entry_surfaceView)
    AutoFitTextureView textureView;

    @BindView(R.id.et_personid)
    EditText personId;

    @BindView(R.id.btn_train)
    Button btn_train;

    private Camera2Helper helper;
    private Retrofit retrofit;
    private SharedPreferences address;
    private NetworkApi networkApi;
    private Gson gson;
    private String base64Data;
    private Image img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ButterKnife.bind(this);

        init();
//        initCamera();
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @OnClick(R.id.btn_rentry)
    void rentry(){
        Log.d(TAG, "rentry: " + base64Data);
        String id = personId.getText().toString();
        if (id!=null && !id.equals("")){
            upload(id, base64Data);
        }
    }

    @OnClick(R.id.btn_train)
    void train(){
        String id = personId.getText().toString();
        try {
            if (id!=null && !id.equals("")){
                btn_train.setEnabled(false);
                trainerRg(id);
                Thread.sleep(3000);
                btn_train.setEnabled(true);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }

    }

    private void upload(String personId, String imgdata){
        networkApi.uploadRg(personId, imgdata)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new SingleObserver<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onSuccess(@NonNull Result result) {
                        ToastUtils.show(EntryActivity.this, result.getMsg());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtils.show(EntryActivity.this, e.getMessage());
                    }
                });
    }

    private void trainerRg(String personId) {
        networkApi.trainerRg(personId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidScheduler.mainThread())
                .subscribe(new SingleObserver<Result>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe");
                    }

                    @Override
                    public void onSuccess(@NonNull Result result) {
                        ToastUtils.show(EntryActivity.this, result.getMsg());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ToastUtils.show(EntryActivity.this, e.getMessage());
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