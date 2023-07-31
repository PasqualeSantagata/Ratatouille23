package com.example.springclient.RetrofitService;

import com.example.springclient.entity.Categoria;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface CategoriaAPI {

    @GET("api/v1/categoria")
    Single<Response<List<Categoria>>> getAllCategorie();

}
