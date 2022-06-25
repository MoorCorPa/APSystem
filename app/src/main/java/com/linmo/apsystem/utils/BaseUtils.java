package com.linmo.apsystem.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class BaseUtils {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String image2base64(String imagePath){
        byte[] data = null;
        if (new File(imagePath).exists()){
            try {
                InputStream in = new FileInputStream(imagePath);
                data = new byte[in.available()];
                in.read(data);
                in.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
        Base64.Encoder encoder = Base64.getEncoder();
        return encoder.encodeToString(data);
    }

}
