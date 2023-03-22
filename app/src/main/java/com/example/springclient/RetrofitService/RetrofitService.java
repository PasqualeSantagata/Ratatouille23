package com.example.springclient.RetrofitService;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    private static final  String base_URL ="http://192.168.1.4:8080/";
    private static RetrofitService ISTANCE;

    private UtenteAPI utenteAPI;



    public static RetrofitService getIstance(){

        if(ISTANCE == null)
            ISTANCE = new RetrofitService();

        return  ISTANCE;
    }

    private RetrofitService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .build();

        utenteAPI = retrofit.create(UtenteAPI.class);

    }

    public UtenteAPI getUtenteAPI(){
        return  utenteAPI;
    }




}
