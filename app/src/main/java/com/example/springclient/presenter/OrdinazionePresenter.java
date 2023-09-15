package com.example.springclient.presenter;

import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.model.OrdinazioneModel;
import com.example.springclient.view.nuovaOrdinazione.EsploraCategorieActivity;
import com.example.springclient.view.nuovaOrdinazione.StartNuovaOrdinazioneActivity;

import java.util.List;

import retrofit2.Response;

public class OrdinazionePresenter {
    private StartNuovaOrdinazioneActivity startNuovaOrdinazioneActivity;
    private Ordinazione ordinazione;
    private EsploraCategorieActivity esploraCategorieActivity;
    private OrdinazioneModel ordinazioneModel = new OrdinazioneModel(RetrofitService.getIstance());


    public OrdinazionePresenter(StartNuovaOrdinazioneActivity startNuovaOrdinazioneActivity){
        this.startNuovaOrdinazioneActivity = startNuovaOrdinazioneActivity;
    }

    public OrdinazionePresenter() {
    }

    public void getOrdinazioniSospese(){
        ordinazioneModel.getOrdinazioniSospese(new CallbackResponse<List<Ordinazione>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<Ordinazione>> retData) {
                if(retData.isSuccessful()){
                    Log.d("ORDINAZIONI: ", retData.body().toString());
                    //invia alla schermata dei cuochi
                }
            }
        });

    }







}
