package com.example.springclient.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.contract.UtenteContract;
import com.example.springclient.authentication.ApiToken;
import com.example.springclient.model.UtenteModel;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.entity.Utente;
import com.example.springclient.model.UtenteModel;
import com.example.springclient.view.InserisciElementoActivity;
import com.example.springclient.view.MainActivity;

public class UtentePresenter implements UtenteContract.Presenter {
    private UtenteModel utenteModel;
    private RetrofitService retrofitService;
    private MainActivity registerActivity;


    public UtentePresenter(MainActivity registerActivity){
        if(retrofitService == null)
            retrofitService = RetrofitService.getIstance();

        retrofitService.setUtentePresenter(this);
        utenteModel = new UtenteModel(retrofitService);
        this.registerActivity = registerActivity;
        SharedPreferences sharedPreferences = registerActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        retrofitService.getMyInterceptor().setPreferences(sharedPreferences);
        retrofitService.getTokenRefreshInterceptor().setUtentePresenter(this);
    }

    @Override
    public void getAllUtenti() {

    }

    @Override
    public void logInUtente(AuthRequest authRequest){
        utenteModel.logInUtente(authRequest, new  UtenteContract.UtenteCallback <ApiToken>() {
            @Override
            public void onFinished() {

            }
            @Override
            public void onFailure(Throwable t) {

            }
            @Override
            public void onSuccess(ApiToken retData) {
                SharedPreferences sharedPreferences = registerActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("accessToken", retData.getAccessToken()).apply();
                sharedPreferences.edit().putString("refreshToken", retData.getRefreshToken()).apply();
                Intent intent = new Intent(registerActivity, InserisciElementoActivity.class);
                registerActivity.startActivity(intent);
            }
        });

    }

    @Override
    public void saveUtente(Utente utente) {


    }

    @Override
    public void reimpostaPassword(Utente utente) {
        //Al primo accesso l'utente (addetto sala, addetto alla cucina e supervisore)
        //deve reimpostare la pass

    }

    @Override
    public void passwordDimenticata(Utente utente) {

    }

    public MainActivity getActivity(){
        return registerActivity;
    }


}
