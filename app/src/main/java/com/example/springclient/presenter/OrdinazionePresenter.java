package com.example.springclient.presenter;

import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.model.OrdinazioneModel;
import com.example.springclient.entity.Portata;
import com.example.springclient.view.nuovaOrdinazione.EsploraCategorieActivity;
import com.example.springclient.view.nuovaOrdinazione.RiepilogoOrdinazioneActivity;
import com.example.springclient.view.nuovaOrdinazione.StartNuovaOrdinazioneActivity;
import com.example.springclient.view.statoOrdinazioni.HomeStatoOrdinazione;

import java.util.List;

import retrofit2.Response;

public class OrdinazionePresenter {
    private StartNuovaOrdinazioneActivity startNuovaOrdinazioneActivity;
    private Ordinazione ordinazione;
    private EsploraCategorieActivity esploraCategorieActivity;
    private RiepilogoOrdinazioneActivity riepilogoOrdinazioneActivity;
    private HomeStatoOrdinazione homeStatoOrdinazione;
    private OrdinazioneModel ordinazioneModel = new OrdinazioneModel(RetrofitService.getIstance());


    public OrdinazionePresenter(StartNuovaOrdinazioneActivity startNuovaOrdinazioneActivity){
        this.startNuovaOrdinazioneActivity = startNuovaOrdinazioneActivity;
    }
    public OrdinazionePresenter(HomeStatoOrdinazione homeStatoOrdinazione){
        this.homeStatoOrdinazione = homeStatoOrdinazione;
    }
    public OrdinazionePresenter(RiepilogoOrdinazioneActivity riepilogoOrdinazioneActivity){
       this.riepilogoOrdinazioneActivity = riepilogoOrdinazioneActivity;
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
                    homeStatoOrdinazione.setOrdinazioniSospese(retData.body());
                    //invia alla schermata dei cuochi
                }
            }
        });
    }
    public void savePortate(List<Portata> portataList){
        ordinazioneModel.savePortate(new CallbackResponse<List<Portata>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<Portata>> retData) {
                if(retData.isSuccessful()){
                    for(Portata p: retData.body()){
                        Log.d("PortataSalvata: ", p.getId().toString());
                    }
                }
            }
        }, portataList);



    }






}
