package com.example.springclient.RetrofitService;


import com.example.springclient.apiUtils.AuthRequest;
import com.example.springclient.entity.Utente;
import com.example.springclient.apiUtils.ApiToken;

import java.util.List;


import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UtenteAPI {

    @GET("api/v1/utente")
    Call<List<Utente>> getAllUtenti();

    @POST("api/v1/auth/register")
    Single<Response<String>> registerAddettoSala(@Body Utente utente);
    @POST("api/v1/auth/authenticate")
    @Headers("No-Authentication: true")
    Single<Response<ApiToken>> logInUtente(@Body AuthRequest authRequest);


}
