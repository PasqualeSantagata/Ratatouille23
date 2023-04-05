package com.example.springclient.RetrofitService;

import com.example.springclient.entity.Allergene;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AllergeneAPI {

    @POST("api/v1/allergene")
    Call<Void> saveAllergene(@Body Allergene allergene);

}
