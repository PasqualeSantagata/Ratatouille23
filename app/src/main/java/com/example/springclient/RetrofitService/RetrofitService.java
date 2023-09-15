package com.example.springclient.RetrofitService;

import com.example.springclient.authentication.AggiungiTokenInterceptor;

import com.example.springclient.authentication.TokenRefreshInterceptor;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.presenter.AutenticazionePresenter;
import com.google.gson.Gson;

import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitService {

    private static final String base_URL ="http://192.168.1.8:8080/";
    private static RetrofitService INSTANCE;
    private UtenteAPI utenteAPI;
    private ElementoMenuAPI elementoMenuAPI;
    private CategoriaAPI categoriaAPI;
    private OrdinazioneAPI ordinazioneAPI;
    private RecuperoCredenzialiAPI recuperoCredenzialiAPI;
    private OkHttpClient.Builder okHttp;
    private AggiungiTokenInterceptor aggiungiTokenInterceptor;
    private TokenRefreshInterceptor tokenRefreshInterceptor;

    private AutenticazionePresenter autenticazionePresenter;

    public static RetrofitService getIstance(){

        if(INSTANCE == null)
            INSTANCE = new RetrofitService();

        return  INSTANCE;
    }

    private RetrofitService(){
         okHttp = new OkHttpClient.Builder();
         aggiungiTokenInterceptor = new AggiungiTokenInterceptor();
         tokenRefreshInterceptor = new TokenRefreshInterceptor();

        /** PER TESTING */
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttp.addInterceptor(aggiungiTokenInterceptor);
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
        recuperoCredenzialiAPI = retrofitClient.create(RecuperoCredenzialiAPI.class);
        ordinazioneAPI = retrofitClient.create(OrdinazioneAPI.class);

    }

    public UtenteAPI getUtenteAPI(){
        return  utenteAPI;
    }

    public ElementoMenuAPI getElementoMenuAPI(){
        return elementoMenuAPI;
    }

    public CategoriaAPI getCategoriaAPI() { return categoriaAPI; }

    public OrdinazioneAPI getOrdinazioneAPI() {
        return ordinazioneAPI;
    }

    public RecuperoCredenzialiAPI getRecuperoCredenzialiAPI(){return recuperoCredenzialiAPI;}

    public AggiungiTokenInterceptor getMyInterceptor(){return aggiungiTokenInterceptor;}

    public void setUtentePresenter(AutenticazionePresenter autenticazionePresenter) {
        this.autenticazionePresenter = autenticazionePresenter;
    }
    public TokenRefreshInterceptor getTokenRefreshInterceptor() {
        return tokenRefreshInterceptor;
    }

}
