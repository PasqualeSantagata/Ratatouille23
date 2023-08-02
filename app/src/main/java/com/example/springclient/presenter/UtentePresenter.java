package com.example.springclient.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.contract.UtenteContract;
import com.example.springclient.authentication.ApiToken;
import com.example.springclient.entity.Role;
import com.example.springclient.model.UtenteModel;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.entity.Utente;
import com.example.springclient.view.DashboardAdmin;
import com.example.springclient.view.creaNuovaUtenza.StartNuovaUtenza;
import com.example.springclient.view.MainActivity;

public class UtentePresenter implements UtenteContract.Presenter {
    private UtenteModel utenteModel;
    private RetrofitService retrofitService;
    private MainActivity loginActivity;
    private SharedPreferences sharedPreferences;
    private StartNuovaUtenza startNuovaUtenza;
    private DashboardAdmin dashboardAdmin;

    private UtentePresenter(){
        retrofitService = RetrofitService.getIstance();
        retrofitService.setUtentePresenter(this);
        utenteModel = UtenteModel.getIstance();
        utenteModel.setPresenter(this);
    }
    public UtentePresenter(MainActivity loginActivity){
        this();
        this.loginActivity = loginActivity;
        sharedPreferences = loginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        retrofitService.getTokenRefreshInterceptor().setMainActivityPresenter(this);
        retrofitService.getMyInterceptor().setPreferences(sharedPreferences);

    }
    public UtentePresenter(StartNuovaUtenza startNuovaUtenza){
        this();
        this.startNuovaUtenza = startNuovaUtenza;
    }

    public UtentePresenter(DashboardAdmin dashboardAdmin){
        this();
        this.dashboardAdmin = dashboardAdmin;
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
                sharedPreferences = loginActivity.getSharedPreferences("prefs", Context.MODE_PRIVATE);
                sharedPreferences.edit().putString("accessToken", retData.getAccessToken()).apply();
                sharedPreferences.edit().putString("refreshToken", retData.getRefreshToken()).apply();
                if(retData.getRole().equals(Role.ADMIN.name())) {
                    loginActivity.avviaDashboardAdmin();
                }
            }
        });
    }

    @Override
    public void saveUtente(Utente utente) {
        utenteModel.saveUtente(utente, new UtenteContract.UtenteCallback<Void>() {
            @Override
            public void onFinished() {

            }

            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Void retData) {

            }
        });

    }

    @Override
    public void reimpostaPassword(String email) {
        utenteModel.forgotPassword(email, new UtenteContract.UtenteCallback<Void>() {
            @Override
            public void onFinished() {

            }
            @Override
            public void onFailure(Throwable t) {

            }
            @Override
            public void onSuccess(Void retData) {

            }
        });
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
