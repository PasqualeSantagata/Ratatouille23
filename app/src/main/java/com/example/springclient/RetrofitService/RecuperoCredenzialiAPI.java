package com.example.springclient.RetrofitService;

import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RecuperoCredenzialiAPI {

   @POST("api/v1/auth/forgot_password")
   @Headers("No-Authentication: true")
   Call<Void> passwordDimenticata(@Query(value = "email", encoded = true) String email);
}
