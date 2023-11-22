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
    private OrdinazioneContract.ViewOrdinazione viewOrdinazione;
    private OrdinazioneContract.ViewPrenotazionePortate viewPrenotazionePortate;
    private final OrdinazioneModel ordinazioneModel = new OrdinazioneModel(RetrofitService.getIstance());

    public OrdinazionePresenter(OrdinazioneContract.ViewPrenotazionePortate viewPrenotazionePortate){
        this.viewPrenotazionePortate = viewPrenotazionePortate;
    }
    public OrdinazionePresenter(OrdinazioneContract.ViewOrdinazione viewOrdinazione){
       this.viewOrdinazione = viewOrdinazione;
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
                    viewPrenotazionePortate.setOrdinazioniSospese(retData.body());
                }
            }
        });
    }


    public void salvaPortate(List<Portata> portataList){
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
                    viewOrdinazione.salvaOrdinazione(portataOrdinazione);
                }
            }
        }, portataList);

    }
    public void salvaOrdinazione(Ordinazione ordinazione){
        ordinazioneModel.aggiungiOrdinazione(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    viewOrdinazione.ordinazioneAvvvenutaConSuccesso();
                }
            }
        }, ordinazione);

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

}
