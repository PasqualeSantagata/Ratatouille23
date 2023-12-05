package com.example.springclient.RetrofitService;

import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface CategoriaAPI {

    @GET("api/v1/categoria")
    Single<Response<List<Categoria>>> getAllCategorie();
    @POST("api/v1/categoria/addCategoria")
    Single<Response<Categoria>> saveCategoria(@Body Categoria categoria);

    @GET("api/v1/categoria/getFoto/{id}")
    Single<Response<ResponseBody>> getFotoCategoriaById(@Path(value = "id", encoded = true)String id);

    @GET("api/v1/categoria/getNomiCategorie")
    Single<Response<List<String>>> getNomiCategorie();

    @POST("api/v1/categoria/addFoto/{categoriaId}")
    @Multipart
    Single<Response<Void>> addFotoCategoria(@Path(value = "categoriaId", encoded = true)String id,
                                            @Part MultipartBody.Part filepart);
    @GET("api/v1/categoria/getNomiCategorie/{id}")
    Single<Response<List<String>>> getNomiCategoriaDisponibili(@Path(value = "id", encoded = true)String id);
    @POST("api/v1/categoria/aggiungiElementoAllaCategoria/{categoria}")
    Single<Response<Void>> aggiungiElemento(@Path(value = "categoria", encoded = true)String nome, @Body ElementoMenu elementoMenu);

    @GET("/api/v1/categoria/getCategoriaByNome/{nomeCategoria}")
    Single<Response<Categoria>> getCategoriaByNome(@Path(value = "nomeCategoria", encoded = true)String nomeCategoria);

    @POST("/api/v1/categoria/modificaOrdine/{nome}")
    Single<Response<Void>> modificaOrdineCategoria(@Path(value = "nome", encoded = true)String nome, @Body int flagOrdinamento);
    @POST("/api/v1/categoria/eliminaElemento/{idCategoria}")
    Single<Response<Void>> eliminaElementoDallaCategoria(@Path(value = "idCategoria", encoded = true)String idCategoria, @Body ElementoMenu elementoMenu);

}
