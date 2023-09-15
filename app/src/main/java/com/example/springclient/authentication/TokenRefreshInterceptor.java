package com.example.springclient.authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.springclient.RetrofitService.UtenteAPI;
import com.example.springclient.presenter.AutenticazionePresenter;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenRefreshInterceptor implements Interceptor {
    private SharedPreferences sharedPreferences;
    private AutenticazionePresenter mainActivityPresenter;

    public TokenRefreshInterceptor(){

    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder newRequestBuilder = request.newBuilder();
        Response response = chain.proceed(request);

        sharedPreferences = mainActivityPresenter.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE);
        if (response.code() == 401 && request.header("No-Authentication") == null) {
            response.close();
            String refreshToken = sharedPreferences.getString("refreshToken", "");
            if(!refreshToken.isEmpty()) {
                String token = getNewToken("Bearer " + refreshToken);
                if (token.isEmpty() || token == null) {
                    /**
                     * gestire meglio
                     */
                    throw new RuntimeException("Session token should be defined for auth apis");
                }
                newRequestBuilder.header("Authorization", "Bearer " + token);
                return chain.proceed(newRequestBuilder.build());
            }
        }
        return response;
    }

    private String getNewToken(String refreshToken) throws IOException {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.8:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        UtenteAPI service = retrofit.create(UtenteAPI.class);
        retrofit2.Response<ApiToken> response= service.refreshToken(refreshToken).execute();
        if(response.code() == 401){
            /**
             * mostrare di nuovo schermata login
             */
            Log.d("Token: ", "Sessone scaduta");
            //throw new RuntimeException("Sessione scaduta");
        }
        ApiToken apiToken = response.body();
        if(apiToken != null) {
            sharedPreferences.edit().putString("accessToken", apiToken.getAccessToken()).commit();
            sharedPreferences.edit().putString("refreshToken", apiToken.getRefreshToken()).commit();
        }
        String token = sharedPreferences.getString("accessToken", "");
        return token;
    }

    public void setMainActivityPresenter(AutenticazionePresenter mainActivityPresenter) {
        this.mainActivityPresenter = mainActivityPresenter;
    }

}
