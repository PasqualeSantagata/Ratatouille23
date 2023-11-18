package com.example.springclient.presenter;

import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.contract.AdminContract;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.entity.Utente;
import com.example.springclient.model.AutenticazioneModel;
import com.example.springclient.view.DashboardAdminActivity;
import com.example.springclient.view.creaNuovaUtenza.StartNuovaUtenzaActivity;
import com.google.gson.Gson;

import retrofit2.Response;

public class AdminPresenter implements AdminContract.Presenter {
    private DashboardAdminActivity dashboardAdminActivity;
    private StartNuovaUtenzaActivity startNuovaUtenzaActivity;
    private AutenticazioneModel autenticazioneModel = AutenticazioneModel.getIstance();

    public AdminPresenter(DashboardAdminActivity dashboardAdminActivity){
        this.dashboardAdminActivity = dashboardAdminActivity;
    }
    public AdminPresenter(StartNuovaUtenzaActivity startNuovaUtenzaActivity){
        this.startNuovaUtenzaActivity = startNuovaUtenzaActivity;
    }

    @Override
    public void registraUtente(Utente utente) {
        autenticazioneModel.registraUtente(utente,new CallbackResponse<ApiResponse>() {
            @Override
            public void onFailure(Throwable t) {
                startNuovaUtenzaActivity.registrazioneFallita();
            }

            @Override
            public void onSuccess(Response<ApiResponse> retData) {
                if(retData.isSuccessful()){
                    startNuovaUtenzaActivity.registrazioneAvvenutaConSuccesso();

                }
                else if(retData.code() == 412){
                    ApiResponse error = new Gson().fromJson(retData.errorBody().charStream(), ApiResponse.class);
                    startNuovaUtenzaActivity.mostraErrore(error.getMessage());
                }
            }
        });

    }

}
