package com.example.springclient.RetrofitService;

import com.google.gson.Gson;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {

    private static final  String base_URL ="http://192.168.1.4:8080/";
    private static RetrofitService ISTANCE;

    private UtenteAPI utenteAPI;
    private ElementoMenuAPI elementoMenuAPI;



    public static RetrofitService getIstance(){

        if(ISTANCE == null)
            ISTANCE = new RetrofitService();

        return  ISTANCE;
    }

    private RetrofitService(){
        OkHttpClient.Builder okHttp = new OkHttpClient.Builder();

        /** PER TESTING **/
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttp.addInterceptor(loggingInterceptor);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(base_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttp.build());

        Retrofit retrofitClient = retrofitBuilder.build();

        utenteAPI = retrofitClient.create(UtenteAPI.class);
        elementoMenuAPI = retrofitClient.create(ElementoMenuAPI.class);
    }

    public UtenteAPI getUtenteAPI(){
        return  utenteAPI;
    }

    public ElementoMenuAPI getElementoMenuAPI(){
        return elementoMenuAPI;
    }

}
