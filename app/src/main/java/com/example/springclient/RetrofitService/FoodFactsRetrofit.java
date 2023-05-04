package com.example.springclient.RetrofitService;

import com.google.gson.Gson;

import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class FoodFactsRetrofit {


    private static final  String base_URL ="https://it.openfoodfacts.org";
    private FoodFactsAPI foodFactsAPI;

    public FoodFactsRetrofit(){
        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(base_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttpClient);

        Retrofit retrofitClient = retrofitBuilder.build();
        foodFactsAPI = retrofitClient.create(FoodFactsAPI.class);
    }

    public FoodFactsAPI getFoodFactsAPI() {
        return foodFactsAPI;
    }
}
