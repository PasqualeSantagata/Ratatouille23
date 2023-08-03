package com.example.springclient.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.AutenticazioneContract;
import com.example.springclient.authentication.ApiToken;
import com.example.springclient.entity.Ruolo;
import com.example.springclient.model.AutenticazioneModel;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.view.MainActivity;

import retrofit2.Response;

public class AutenticazionePresenter implements AutenticazioneContract.Presenter {
    private AutenticazioneModel autenticazioneModel;
    private RetrofitService retrofitService;
    private MainActivity loginActivity;
    private SharedPreferences sharedPreferences;


    private AutenticazionePresenter(){
        retrofitService = RetrofitService.getIstance();
        retrofitService.setUtentePresenter(this);
        autenticazioneModel = AutenticazioneModel.getIstance();
    }
    public AutenticazionePresenter(MainActivity loginActivity){
        this();
        this.loginActivity = loginActivity;
        sharedPreferences = loginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        retrofitService.getTokenRefreshInterceptor().setMainActivityPresenter(this);
        retrofitService.getMyInterceptor().setPreferences(sharedPreferences);

    }

    @Override
    public void logInUtente(AuthRequest authRequest){
        autenticazioneModel.logInUtente(authRequest, new CallbackResponse<ApiToken>() {
            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(loginActivity, t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e("Failure: ", t.getMessage());
            }
            @Override
            public void onSuccess(Response<ApiToken> retData) {
                if(retData.isSuccessful()){
                    ApiToken apiToken = retData.body();
                    sharedPreferences = loginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                    sharedPreferences.edit().putString("accessToken", apiToken.getAccessToken()).apply();
                    sharedPreferences.edit().putString("refreshToken", apiToken.getRefreshToken()).apply();
                    if(apiToken.getRuolo().equals(Ruolo.ADMIN.name())) {
                        loginActivity.avviaDashboardAdmin();
                    }
                }
                else if(retData.code() == 403){
                   avviaAggiornaPassword();
                }
                else{
                    loginActivity.loginErrore();
                    sharedPreferences.edit().putString("email", "").apply();
                }
            }
        });
    }


    public void avviaAggiornaPassword(){
        loginActivity.dialgPrimoAccesso();
    }

    public MainActivity getActivity(){
        return loginActivity;
    }



}
