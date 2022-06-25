package com.linmo.apsystem.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.ImageFormat;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.Image;
import android.os.Build;
import android.os.MemoryFile;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import org.greenrobot.greendao.annotation.Convert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.Base64;

public class BaseUtils {

    public static final int YUV420P = 0;
    public static final int YUV420SP = 1;
    public static final int NV21 = 2;
    private static final String TAG = "BaseUtils";

    private static Bitmap mbitmap = null;
    private static byte[] mbytes = null;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String byte2Base64(byte[] src) {
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(src);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String image2base64(String imagePath) {
        return image2base64(new File(imagePath));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String image2base64(File file) {
        byte[] data = null;
        if (file.exists()) {
            try {
                InputStream in = new FileInputStream(file);
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return byte2Base64(data);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String image2base64(Image image) {
        mbitmap = jpeg2bitmap(image);
        mbytes = bitmap2Byte(mbitmap);

        return byte2Base64(mbytes);
    }

    public static Bitmap jpeg2bitmap(Image image){
        ByteBuffer buffer = image.getPlanes()[0].getBuffer();
        byte[] bytes = new byte[buffer.capacity()];
        buffer.get(bytes);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);
    }

    public static byte[] bitmap2Byte(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //把bitmap100%高质量压缩 到 output对象里
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        return outputStream.toByteArray();
    }


}