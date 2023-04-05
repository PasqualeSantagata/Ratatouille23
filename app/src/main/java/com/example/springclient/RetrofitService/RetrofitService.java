package com.example.springclient.RetrofitService;

import com.example.springclient.entity.Allergene;
import com.google.gson.Gson;

import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final  String base_URL ="http://192.168.1.4:8080/";
    private static RetrofitService ISTANCE;

    private UtenteAPI utenteAPI;
    private ElementoMenuAPI elementoMenuAPI;
    private AllergeneAPI allergeneAPI;


    public static RetrofitService getIstance(){

        if(ISTANCE == null)
            ISTANCE = new RetrofitService();

        return  ISTANCE;
    }

    private RetrofitService(){
        OkHttpClient.Builder okHttp = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttp.addInterceptor(loggingInterceptor);

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(base_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttp.build());

        Retrofit retrofitClient = retrofitBuilder.build();

        utenteAPI = retrofitClient.create(UtenteAPI.class);
        elementoMenuAPI = retrofitClient.create(ElementoMenuAPI.class);
        allergeneAPI = retrofitClient.create(AllergeneAPI.class);

    }

    public UtenteAPI getUtenteAPI(){
        return  utenteAPI;
    }

    public ElementoMenuAPI getElementoMenuAPI(){
        return elementoMenuAPI;
    }

    public AllergeneAPI getAllergeneAPI(){
        return allergeneAPI;
    }


}
