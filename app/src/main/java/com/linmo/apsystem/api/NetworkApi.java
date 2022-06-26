package com.linmo.apsystem.api;

import com.linmo.apsystem.model.RequestBody;
import com.linmo.apsystem.model.Result;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface NetworkApi {

    //训练模型照片上传
    @FormUrlEncoded
    @POST("face/upload_rg")
    Single<Result> uploadRg(@Field("personId") String personId, @Field("imgdata") String imgdata);

    //训练模型
    @FormUrlEncoded
    @POST("face/trainer_rg")
    Single<Result> trainerRg(@Field("personId") String personId);

    //识别
    @FormUrlEncoded
    @POST("face/facerecognition")
    Single<Result> getPhotoRg(@Field("personId") String personId, @Field("imgdata") String imgdata);

}
