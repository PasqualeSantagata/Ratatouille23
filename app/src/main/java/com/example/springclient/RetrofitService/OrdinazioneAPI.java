package com.example.springclient.RetrofitService;


import com.example.springclient.entity.Ordinazione;
import java.util.List;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface OrdinazioneAPI {

    @GET("api/v1/ordinazione")
    Single<Response<List<Ordinazione>>> getOrdinazioniSospese();

}
