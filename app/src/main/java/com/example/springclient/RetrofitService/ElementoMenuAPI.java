package com.example.springclient.RetrofitService;

import com.example.springclient.entity.ElementoMenu;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ElementoMenuAPI {

    @POST("api/v1/elementoMenu")
    Call<Void> addElementoMenu(@Body ElementoMenu elementoMenu);

    @GET("api/v1/elementoMenu")
    Single<List<ElementoMenu>> getAllElementoMenu();

}
