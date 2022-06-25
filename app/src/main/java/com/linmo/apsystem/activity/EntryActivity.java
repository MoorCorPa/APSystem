package com.linmo.apsystem.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.google.gson.Gson;
import com.linmo.apsystem.R;
import com.linmo.apsystem.api.NetworkApi;
import com.linmo.apsystem.model.Result;
import com.linmo.apsystem.utils.AndroidScheduler;
import com.linmo.apsystem.utils.BaseUtils;
import com.linmo.apsystem.utils.maxiaozhou1234.camera2.CameraConfig;
import com.linmo.apsystem.utils.maxiaozhou1234.camera2.CameraUtil;
import com.linmo.apsystem.utils.maxiaozhou1234.camera2.OnImageAvailableListener;

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

public class EntryActivity extends AppCompatActivity {
    private static final String TAG = "EntryActivity";
    
    @BindView(R.id.entry_surfaceView)
    TextureView textureView;

    private CameraUtil cameraUtil;
    private Retrofit retrofit;
    private SharedPreferences address;
    private NetworkApi networkApi;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ButterKnife.bind(this);

        init();
//        initCamera();
    }

    private void init(){
        address = getSharedPreferences("address", MODE_PRIVATE);
        String url = address.getString("address", "http://127.0.0.1/");
        gson = new Gson();
        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        networkApi = retrofit.create(NetworkApi.class);
    }

    private void upload(String personId, String base64Data){
        networkApi.uploadRg(personId, base64Data)
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

// 有bug 弃用
//    private void initCamera(){
//        cameraUtil = new CameraUtil(this, textureView, null);
//        cameraUtil.setPreviewImageFormat(ImageFormat.DEPTH16);
//        cameraUtil.setDefaultCamera(false);
//        cameraUtil.setAutoFixSurface(true);
//    }
//
//    @OnClick(R.id.btn_rentry)
//    void rentry(){
//        cameraUtil.capturePicture();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //启动预览
//        cameraUtil.startPreview(this);
//    }
//    //在 onPause 或者 onDestroy 中释放资源
//    //建议在 onPause 中，因为 onDestroy 中系统已先断开与相机的连接
//    @Override
//    protected void onPause() {
//        if (cameraUtil != null) {
//            cameraUtil.release();
//        }
//        super.onPause();
//    }
}