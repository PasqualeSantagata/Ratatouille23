package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.contract.CreaUtenzaContract;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.entity.Utente;
import com.example.springclient.model.AutenticazioneModel;
import com.example.springclient.view.creaNuovaUtenza.StartNuovaUtenzaActivity;
import com.google.gson.Gson;

import org.checkerframework.checker.units.qual.A;

import retrofit2.Response;

public class AdminPresenter implements CreaUtenzaContract.Presenter {
    private CreaUtenzaContract.View adminView;
    private AutenticazioneModel autenticazioneModel = new AutenticazioneModel(RetrofitService.getIstance());

    public AdminPresenter(StartNuovaUtenzaActivity adminView){
        this.adminView = adminView;
    }

    @Override
    public void registraUtente(Utente utente) {
        autenticazioneModel.registraUtente(utente,new CallbackResponse<ApiResponse>() {
            @Override
            public void onFailure(Throwable t) {
                adminView.registrazioneFallita();
            }

            @Override
            public void onSuccess(Response<ApiResponse> retData) {
                if(retData.isSuccessful()){
                    adminView.registrazioneAvvenutaConSuccesso();

                }
                else if(retData.code() == 412){
                    ApiResponse error = new Gson().fromJson(retData.errorBody().charStream(), ApiResponse.class);
                    adminView.mostraErrore(error.getMessage());
                }
            }
        });

    }

}
