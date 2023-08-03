package com.example.springclient.presenter;

import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.contract.AdminContract;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.entity.Utente;
import com.example.springclient.model.AutenticazioneModel;
import com.example.springclient.view.DashboardAdmin;
import com.example.springclient.view.creaNuovaUtenza.StartNuovaUtenzaActivity;
import com.google.gson.Gson;

import retrofit2.Response;

public class AdminPresenter implements AdminContract.Presenter {
    private DashboardAdmin dashboardAdmin;
    private StartNuovaUtenzaActivity startNuovaUtenzaActivity;
    private AutenticazioneModel autenticazioneModel = AutenticazioneModel.getIstance();

    public AdminPresenter(DashboardAdmin dashboardAdmin){
        this.dashboardAdmin = dashboardAdmin;
    }
    public AdminPresenter(StartNuovaUtenzaActivity startNuovaUtenzaActivity){
        this.startNuovaUtenzaActivity = startNuovaUtenzaActivity;
    }

    @Override
    public void registraUtente(Utente utente) {
        autenticazioneModel.registraUtente(utente,new CallbackResponse<ApiResponse>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<ApiResponse> retData) {
                if(retData.isSuccessful()){


                }
                else if(retData.code() == 412){
                    ApiResponse error = new Gson().fromJson(retData.errorBody().charStream(), ApiResponse.class);
                    startNuovaUtenzaActivity.mostraErrore(error.getMessage());
                }
            }
        });

    }

}
