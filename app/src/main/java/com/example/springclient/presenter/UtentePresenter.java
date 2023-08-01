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
    private SharedPreferences sharedPreferences;

    private CreaNuovaUtenza creaNuovaUtenza;

    public UtentePresenter(MainActivity loginActivity){
        if(retrofitService == null)
            retrofitService = RetrofitService.getIstance();

        retrofitService.setUtentePresenter(this);
        utenteModel = new UtenteModel(retrofitService, this);
        this.loginActivity = loginActivity;
        sharedPreferences = loginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
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
                sharedPreferences.edit().putString("email", "").apply();

            }
            @Override
            public void onFailure(Throwable t) {

            }
            @Override
            public void onSuccess(ApiToken retData) {
                Intent intent = null;
                sharedPreferences = loginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("accessToken", retData.getAccessToken()).apply();
                sharedPreferences.edit().putString("refreshToken", retData.getRefreshToken()).apply();
                intent = new Intent(loginActivity, InserisciElementoActivity.class);
                loginActivity.startActivity(intent);//TODO

            }
        });

    }

    @Override
    public void saveUtente(Utente utente) {


    }

    @Override
    public void reimpostaPassword(String email) {
        //Al primo accesso l'utente (addetto sala, addetto alla cucina e supervisore)
        //deve reimpostare la pass

    }

    @Override
    public void passwordDimenticata() {

    }

    public void avviaAggiornaPassword(){
        loginActivity.dialgPrimoAccesso();
    }

    public MainActivity getActivity(){
        return loginActivity;
    }



}
