package com.linmo.apsystem.activity.entry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
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
import android.view.SurfaceView;
import android.view.TextureView;
import android.widget.Toast;

import com.linmo.apsystem.R;
import com.linmo.apsystem.utils.BaseUtils;
import com.linmo.apsystem.utils.maxiaozhou1234.camera2.CameraConfig;
import com.linmo.apsystem.utils.maxiaozhou1234.camera2.CameraUtil;
import com.linmo.apsystem.utils.maxiaozhou1234.camera2.OnImageAvailableListener;

import java.nio.ByteBuffer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EntryActivity extends AppCompatActivity {

    @BindView(R.id.entry_surfaceView)
    TextureView textureView;

    private CameraUtil cameraUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        ButterKnife.bind(this);

        initCamera();
    }

    private void initCamera(){
        cameraUtil = new CameraUtil(this, textureView, null);
        cameraUtil.setDefaultCamera(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //启动预览
        cameraUtil.startPreview(this);
    }
    //在 onPause 或者 onDestroy 中释放资源
    //建议在 onPause 中，因为 onDestroy 中系统已先断开与相机的连接
    @Override
    protected void onPause() {
        if (cameraUtil != null) {
            cameraUtil.release();
        }
        super.onPause();
    }
}