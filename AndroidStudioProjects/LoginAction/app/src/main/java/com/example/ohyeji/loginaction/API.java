package com.example.ohyeji.loginaction;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by ohyeji on 2016. 11. 30..
 */

public interface API {
    @FormUrlEncoded
    @POST("/sign/in")
    Call<Members> LoginAction(@Field("email") String id, @Field("password") String pw);
}
