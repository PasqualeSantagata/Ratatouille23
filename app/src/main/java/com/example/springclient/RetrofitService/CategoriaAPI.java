package com.example.springclient.RetrofitService;

import com.example.springclient.entity.Categoria;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import kotlin.ParameterName;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CategoriaAPI {

    /*@GET("api/v1/categoria")
    Call<List<Categoria>> getAllCategorie();*/

    @GET("api/v1/categoria")
    Single<Response<List<Categoria>>> getAllCategorie();
    @POST("api/v1/categoria/addCategoria")
    Single<Response<Categoria>> saveCategoria(@Body Categoria categoria);

    @GET("api/v1/categoria/getFoto/{id}")
    Single<Response<ResponseBody>> getFotoCategoriaById(@Path(value = "id", encoded = true)String id);

    @GET("api/v1/categoria/getNomiCategorie")
    Single<Response<List<String>>> getNomiCategorie();


}
