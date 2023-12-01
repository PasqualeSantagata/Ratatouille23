package com.example.springclient.RetrofitService;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RecuperoCredenzialiAPI {

   @POST("api/v1/auth/forgot_password")
   @Headers("No-Authentication: true")
   Single<Response<Void>> passwordDimenticata(@Query(value = "email", encoded = true) String email);
}
