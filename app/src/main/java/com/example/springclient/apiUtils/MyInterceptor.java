package com.example.springclient.apiUtils;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


@Singleton
public class MyInterceptor implements Interceptor {
    private String token;
    private SharedPreferences preferences;

    @Inject
    public MyInterceptor() {

    }
    public void setSessionToken(String sessionToken) {
        this.token = sessionToken;
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
            token = preferences.getString("token", "");

            if (token.isEmpty() || token == null) {
                throw new RuntimeException("Session token should be defined for auth apis");
            } else {
                requestBuilder.addHeader("Authorization", "Bearer " + token);
            }
        }
        return chain.proceed(requestBuilder.build());
    }
}
