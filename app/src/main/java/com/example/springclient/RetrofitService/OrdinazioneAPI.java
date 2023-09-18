package com.example.springclient.RetrofitService;


import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;

import java.util.List;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface OrdinazioneAPI {

    @GET("api/v1/ordinazione")
    Single<Response<List<Ordinazione>>> getOrdinazioniSospese();

    @POST("api/v1/portata")
    Single<Response<List<Portata>>> savePortate();

}
