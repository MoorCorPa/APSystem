package com.linmo.apsystem.api;

import com.linmo.apsystem.model.RequestBody;
import com.linmo.apsystem.model.Result;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NetworkApi {

    //训练模型照片上传
    @POST("face/upload_rq")
    Single<Result> uploadRg(@Body RequestBody body);

    //训练模型
    @POST("face/trainer_rq")
    Single<Result> trainerRg(@Body RequestBody body);

    //识别
    @POST("face/facerecognition")
    Single<Result> getPhotoRg(@Body RequestBody body);

}
