package com.example.springclient.RetrofitService;

import com.example.springclient.analytics.AnalyticsData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface AnalyticsAPI {

    @GET("api/v1/analytics/{dataInizio}/{dataFine}")
    Single<Response<List<AnalyticsData>>> getAnalytics(@Path(value = "dataInizio", encoded = true) String dataInizio,
                                                       @Path(value = "dataFine", encoded = true) String dataFine);

}
