package com.example.springclient.RetrofitService;

import com.example.springclient.entity.ElementoMenu;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ElementoMenuAPI {

    @POST("api/v1/elementoMenu")
    Call<Void> addElementoMenu(@Body ElementoMenu elementoMenu);


}
