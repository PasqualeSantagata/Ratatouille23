package com.example.springclient.authentication;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Si occupa di aggiungere il token ad ogni richiesta http che tenta di accedere ad un risorsa protetta
 * il token è salvato nelle sharedPreferences un file condiviso il cui scope è limitato all'applicazione
 * */
public class AggiungiTokenInterceptor implements Interceptor {
    private String token;
    private SharedPreferences preferences;

    public AggiungiTokenInterceptor() {

    }
    public void setPreferences(SharedPreferences preferences){
        this.preferences = preferences;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        if (request.header("No-Authentication") == null) {
            Log.d("no-auth", "no");
            token = preferences.getString("accessToken", "");

            if ( token == null || token.isEmpty() ) {
                throw new RuntimeException("Session token should be defined for auth apis");
            } else {
                requestBuilder.header("Authorization", "Bearer " + token);
            }
        }
        return chain.proceed(requestBuilder.build());
    }
}
