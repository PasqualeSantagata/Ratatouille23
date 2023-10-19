package com.example.springclient.RetrofitService;

import com.example.springclient.entity.ElementoMenu;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ElementoMenuAPI {

    @POST("api/v1/elementoMenu/addElemento/{categoria}")
    Call<Void> salvaElementoMenu(@Body ElementoMenu elementoMenu, @Path(value = "categoria", encoded = true) String categoria);

    @GET("api/v1/elementoMenu")
    Single<Response<List<ElementoMenu>>> getAllElementoMenu();

    @GET("api/v1/elementoMenu/rimuoviElemento/{id}")
    Single<Response<Void>> rimuoviElemento(@Path(value = "id", encoded = true)String id);

    @POST("api/v1/elementoMenu/aggiungiLingua/{id}")
    Single<Response<Void>> aggiungiLingua(@Path(value = "id", encoded = true)String id, @Body ElementoMenu elementoMenu);
    @GET("api/v1/elementoMenu/restituisciTraduzione/{id}")
    Single<Response<ElementoMenu>> restituisciTraduzione(@Path(value = "id", encoded = true)String id);
    @POST("api/v1/elementoMenu/restituisciLinguaBase/{id}")
    Single<Response<ElementoMenu>> restituisciLinguaBase(@Path(value = "id", encoded = true)String id);
    @POST("api/v1/elementoMenu/modificaElemento")
    Single<Response<Void>> modificaElementoMenu(@Body ElementoMenu elementoMenu);


}
