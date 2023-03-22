package com.example.springclient.RetrofitService;

import com.example.springclient.entity.Utente;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UtenteAPI {

    @GET("api/v1/utente")
    Call<List<Utente>> getAllUtenti();

    @POST("api/v1/utente")
    Call<Void> registerNewUtente(@Body Utente utente);
}
