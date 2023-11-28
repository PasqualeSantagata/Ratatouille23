package com.example.springclient.presenter;


import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.OrdinazioneContract;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.model.OrdinazioneModel;
import com.example.springclient.entity.Portata;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class OrdinazionePresenter implements OrdinazioneContract.Presenter {
    private OrdinazioneContract.ViewRiepilogoOrdinazione viewRiepilogoOrdinazione;
    private OrdinazioneContract.ViewElementiOrdinazione viewElementiOrdinazione;
    private OrdinazioneContract.StartNuovaOrdinazioneView viewStartNuovaOrdinazione;
    private final OrdinazioneModel ordinazioneModel = new OrdinazioneModel(RetrofitService.getIstance());


    public OrdinazionePresenter(OrdinazioneContract.ViewRiepilogoOrdinazione viewRiepilogoOrdinazione){
       this.viewRiepilogoOrdinazione = viewRiepilogoOrdinazione;
    }

    public OrdinazionePresenter(OrdinazioneContract.StartNuovaOrdinazioneView viewStartNuovaOrdinazione){
        this.viewStartNuovaOrdinazione = viewStartNuovaOrdinazione;
    }

    public OrdinazionePresenter(OrdinazioneContract.ViewElementiOrdinazione viewElementiOrdinazione) {
        this.viewElementiOrdinazione = viewElementiOrdinazione;
    }

    @Override
    public void salvaPortate(List<Portata> portataList){
        ordinazioneModel.savePortate(new CallbackResponse<List<Portata>>() {
            @Override
            public void onFailure(Throwable t) {
                viewRiepilogoOrdinazione.ordinazioneFallita();
            }

            @Override
            public void onSuccess(Response<List<Portata>> retData) {
                if(retData.isSuccessful()){
                    List<Portata> portataOrdinazione = new ArrayList<>();
                    for(Portata p: retData.body()){
                        portataOrdinazione.add(new Portata(p.getId()));
                    }
                    viewRiepilogoOrdinazione.salvaOrdinazione(portataOrdinazione);
                }
            }
        }, portataList);

    }
    @Override
    public void salvaOrdinazione(Ordinazione ordinazione){
        ordinazioneModel.aggiungiOrdinazione(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                viewRiepilogoOrdinazione.ordinazioneFallita();
            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    viewRiepilogoOrdinazione.ordinazioneAvvvenutaConSuccesso();
                }
            }
        }, ordinazione);

    }

    @Override
    public void tornaEsploraCategorie() {
        viewElementiOrdinazione.tornaIndietro();
    }


    @Override
    public void mostraEsploraCategorie(Ordinazione ordinazione) {
        viewStartNuovaOrdinazione.mostraEsploraCategorie(ordinazione);
    }



}
