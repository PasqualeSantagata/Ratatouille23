package com.example.springclient.RetrofitService;

import com.example.springclient.authentication.AddTokenInterceptor;

import com.example.springclient.authentication.TokenRefreshInterceptor;
import com.example.springclient.presenter.UtentePresenter;
import com.google.gson.Gson;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {

    private static final  String base_URL ="http://localhost:8080/";
    private static RetrofitService INSTANCE;
    private UtenteAPI utenteAPI;
    private ElementoMenuAPI elementoMenuAPI;
    private CategoriaAPI categoriaAPI;
    private OkHttpClient.Builder okHttp;
    private AddTokenInterceptor addTokenInterceptor;
    private TokenRefreshInterceptor tokenRefreshInterceptor;

    private UtentePresenter utentePresenter;

    public static RetrofitService getIstance(){

        if(INSTANCE == null)
            INSTANCE = new RetrofitService();

        return  INSTANCE;
    }

    private RetrofitService(){
         okHttp = new OkHttpClient.Builder();
         addTokenInterceptor = new AddTokenInterceptor();
         tokenRefreshInterceptor = new TokenRefreshInterceptor();

        /** PER TESTING */
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttp.addInterceptor(addTokenInterceptor);
        okHttp.addInterceptor(tokenRefreshInterceptor);
        okHttp.addInterceptor(loggingInterceptor);
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .baseUrl(base_URL)
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .client(okHttp.build());

        Retrofit retrofitClient = retrofitBuilder.build();

        utenteAPI = retrofitClient.create(UtenteAPI.class);
        elementoMenuAPI = retrofitClient.create(ElementoMenuAPI.class);
        categoriaAPI = retrofitClient.create(CategoriaAPI.class);
    }

    public UtenteAPI getUtenteAPI(){
        return  utenteAPI;
    }

    public ElementoMenuAPI getElementoMenuAPI(){
        return elementoMenuAPI;
    }

    public CategoriaAPI getCategoriaAPI() { return categoriaAPI; }

    public AddTokenInterceptor getMyInterceptor(){return addTokenInterceptor;}

    public void setUtentePresenter(UtentePresenter utentePresenter) {
        this.utentePresenter = utentePresenter;
    }
    public TokenRefreshInterceptor getTokenRefreshInterceptor() {
        return tokenRefreshInterceptor;
    }

}
