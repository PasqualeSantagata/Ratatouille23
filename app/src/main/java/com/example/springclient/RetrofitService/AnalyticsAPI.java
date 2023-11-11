package com.example.springclient.RetrofitService;

import com.example.springclient.analytics.AnalyticsData;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;

public interface AnalyticsAPI {

    @GET("api/v1/analytics")
    Single<Response<List<AnalyticsData>>> getAnalytics();

}
