package com.example.springclient.RetrofitService;


import com.example.springclient.apiUtils.FoodFactsResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodFactsAPI {

    @GET("/cgi/search.pl?page_size=4&json=true&fields=brands,product_name,allergens,generic_name,allergens")
    Single<Response<FoodFactsResponse>> getElementoMenuDetails(@Query("search_terms") String nome);

}
