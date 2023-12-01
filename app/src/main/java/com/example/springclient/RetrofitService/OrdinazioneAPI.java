package com.example.springclient.RetrofitService;


import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;

import java.util.List;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OrdinazioneAPI {

    @GET("api/v1/ordinazione")
    Single<Response<List<Ordinazione>>> getOrdinazioniSospese();

    @POST("api/v1/portata")
    Single<Response<List<Portata>>> savePortate(@Body List<Portata> portataList);

    @POST("api/v1/ordinazione/concludiOrdinazione/{ordinazioneId}")
    Single<Response<Ordinazione>> concludiOrdinazione(@Path(value = "ordinazioneId", encoded = true)String id);

    @POST("api/v1/ordinazione")
    Single<Response<Ordinazione>> aggiungiOrdinazione(@Body Ordinazione ordinazione);


}
