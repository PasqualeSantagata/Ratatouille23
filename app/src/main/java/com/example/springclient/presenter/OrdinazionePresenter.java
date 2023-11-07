package com.example.springclient.presenter;

import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.OrdinazioneContract;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.model.OrdinazioneModel;
import com.example.springclient.entity.Portata;
import com.example.springclient.view.nuovaOrdinazione.EsploraCategorieActivity;
import com.example.springclient.view.nuovaOrdinazione.RiepilogoOrdinazioneActivity;
import com.example.springclient.view.nuovaOrdinazione.StartNuovaOrdinazioneActivity;
import com.example.springclient.view.statoOrdinazioni.HomeStatoOrdinazioneActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class OrdinazionePresenter implements OrdinazioneContract.Presenter {
    private StartNuovaOrdinazioneActivity startNuovaOrdinazioneActivity;
    private Ordinazione ordinazione;
    private EsploraCategorieActivity esploraCategorieActivity;
    private RiepilogoOrdinazioneActivity riepilogoOrdinazioneActivity;
    private HomeStatoOrdinazioneActivity homeStatoOrdinazioneActivity;
    private OrdinazioneModel ordinazioneModel = new OrdinazioneModel(RetrofitService.getIstance());


    public OrdinazionePresenter(StartNuovaOrdinazioneActivity startNuovaOrdinazioneActivity){
        this.startNuovaOrdinazioneActivity = startNuovaOrdinazioneActivity;
    }
    public OrdinazionePresenter(HomeStatoOrdinazioneActivity homeStatoOrdinazioneActivity){
        this.homeStatoOrdinazioneActivity = homeStatoOrdinazioneActivity;
    }
    public OrdinazionePresenter(RiepilogoOrdinazioneActivity riepilogoOrdinazioneActivity){
       this.riepilogoOrdinazioneActivity = riepilogoOrdinazioneActivity;
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
                    homeStatoOrdinazioneActivity.setOrdinazioniSospese(retData.body());
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
                    List<Portata> portataOrdinazione = new ArrayList<>();
                    for(Portata p: retData.body()){
                        portataOrdinazione.add(new Portata(p.getId()));
                    }
                    riepilogoOrdinazioneActivity.salvaOrdinazione(portataOrdinazione);
                }
            }
        }, portataList);

    }

    public void concludiOrdinazione(long idOrdinazione) {
        ordinazioneModel.concludiOrdinazione(new CallbackResponse<Ordinazione>() {
            @Override
            public void onFailure(Throwable t) {

            }
            @Override
            public void onSuccess(Response<Ordinazione> retData) {
                if(retData.isSuccessful()){


                }
            }
        },idOrdinazione);
    }

    public void aggiungiOrdinazione(Ordinazione ordinazione){
        ordinazioneModel.aggiungiOrdinazione(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    riepilogoOrdinazioneActivity.dialogOrdinazioneAvvvenutaConSuccesso();

                }
            }
        }, ordinazione);

    }
}
