package com.example.springclient.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.contract.UtenteContract;
import com.example.springclient.authentication.ApiToken;
import com.example.springclient.model.UtenteModel;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.entity.Utente;
import com.example.springclient.view.creaNuovaUtenza.CreaNuovaUtenza;
import com.example.springclient.view.inserimentoNelMenu.InserisciElementoActivity;
import com.example.springclient.view.MainActivity;

public class UtentePresenter implements UtenteContract.Presenter {
    private UtenteModel utenteModel;
    private RetrofitService retrofitService;
    private MainActivity loginActivity;
    private CreaNuovaUtenza creaNuovaUtenza;

    public UtentePresenter(MainActivity loginActivity){
        if(retrofitService == null)
            retrofitService = RetrofitService.getIstance();

        retrofitService.setUtentePresenter(this);
        utenteModel = new UtenteModel(retrofitService);
        this.loginActivity = loginActivity;
        SharedPreferences sharedPreferences = loginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        retrofitService.getMyInterceptor().setPreferences(sharedPreferences);
        retrofitService.getTokenRefreshInterceptor().setUtentePresenter(this);
    }

    public UtentePresenter(CreaNuovaUtenza creaNuovaUtenza) {
        this.creaNuovaUtenza = creaNuovaUtenza;
    }

    @Override
    public void getAllUtenti() {

    }

    @Override
    public void logInUtente(AuthRequest authRequest){
        utenteModel.logInUtente(authRequest, new UtenteContract.UtenteCallback <ApiToken>() {
            @Override
            public void onFinished() {
                loginActivity.loginError();

            }
            @Override
            public void onFailure(Throwable t) {

            }
            @Override
            public void onSuccess(ApiToken retData) {
                SharedPreferences sharedPreferences = loginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("accessToken", retData.getAccessToken()).apply();
                sharedPreferences.edit().putString("refreshToken", retData.getRefreshToken()).apply();
                Intent intent = new Intent(loginActivity, InserisciElementoActivity.class);
                loginActivity.startActivity(intent);
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
        return loginActivity;
    }


}
