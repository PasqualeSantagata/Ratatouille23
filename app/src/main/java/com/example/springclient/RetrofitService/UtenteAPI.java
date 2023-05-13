package com.example.springclient.RetrofitService;


import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.entity.Utente;
import com.example.springclient.authentication.ApiToken;

import java.util.List;


import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface UtenteAPI {

    @GET("api/v1/utente")
    Call<List<Utente>> getAllUtenti();

    @POST("api/v1/auth/register")
    Single<Response<ApiToken>> registerAddettoSala(@Body Utente utente);
    @POST("api/v1/auth/authenticate")
    @Headers("No-Authentication: true")
    Single<Response<ApiToken>> logInUtente(@Body AuthRequest authRequest);

    @POST("api/v1/auth/refreshToken")
    @Headers("No-Authentication: true")
    Call<ApiToken> refreshToken(@Header("Authorization") String token);


}
