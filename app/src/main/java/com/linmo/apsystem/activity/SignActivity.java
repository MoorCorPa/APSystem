package com.linmo.apsystem.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.linmo.apsystem.R;
import com.linmo.apsystem.api.NetworkApi;
import com.linmo.apsystem.model.RequestBody;
import com.linmo.apsystem.model.Result;
import com.linmo.apsystem.utils.AndroidScheduler;
import com.linmo.apsystem.utils.BaseUtils;

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

public class SignActivity extends AppCompatActivity {
    private static final String TAG = null;

    // 获取控件
    @BindView(R.id.sign)
    Button sing;
    @BindView(R.id.et_personid)
    EditText et_personid;

    Button back;


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
    void SignIn() {
        // 获取工号内容
        String signid = "";
        signid = et_personid.getText().toString();
        Log.d(TAG, "SignIn: "+ signid);


        // 网络请求
//        getPhotoRg(signid,);
    }

    // 回到上一个页面
    @OnClick(R.id.back)
    public void onViewClicked() {
        finish();
    }

    // 网络请求
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
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }
                });
    }




    /**
     * 将图片转换成Base64编码的字符串
     */
    public static String imageToBase64(String path){
        if(TextUtils.isEmpty(path)){
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try{
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data,Base64.NO_CLOSE);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(null !=is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

}