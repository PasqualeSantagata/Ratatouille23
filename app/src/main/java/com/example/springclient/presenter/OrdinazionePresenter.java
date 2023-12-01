package com.example.springclient.presenter;


import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.BaseView;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.OrdinazioneContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.model.ElementoMenuModel;
import com.example.springclient.model.OrdinazioneModel;
import com.example.springclient.entity.Portata;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class OrdinazionePresenter implements OrdinazioneContract.Presenter {
    private OrdinazioneContract.ViewRiepilogoOrdinazione viewRiepilogoOrdinazione;
    private OrdinazioneContract.ViewElementiOrdinazione viewElementiOrdinazione;
    private OrdinazioneContract.StartNuovaOrdinazioneView viewStartNuovaOrdinazione;
    private BaseView baseView;
    private final OrdinazioneModel ordinazioneModel = new OrdinazioneModel(RetrofitService.getIstance());
    private final ElementoMenuModel elementoMenuModel = new ElementoMenuModel(RetrofitService.getIstance());

    public OrdinazionePresenter(OrdinazioneContract.ViewRiepilogoOrdinazione viewRiepilogoOrdinazione){
       this.viewRiepilogoOrdinazione = viewRiepilogoOrdinazione;
       baseView = viewRiepilogoOrdinazione;
    }

    public OrdinazionePresenter(OrdinazioneContract.StartNuovaOrdinazioneView viewStartNuovaOrdinazione){
        this.viewStartNuovaOrdinazione = viewStartNuovaOrdinazione;
    }

    public OrdinazionePresenter(OrdinazioneContract.ViewElementiOrdinazione viewElementiOrdinazione) {
        this.viewElementiOrdinazione = viewElementiOrdinazione;
        baseView = viewElementiOrdinazione;
    }

    @Override
    public void salvaPortate(List<Portata> portataList){
        viewRiepilogoOrdinazione.mostraPorgressBar();
        ordinazioneModel.savePortate(new CallbackResponse<List<Portata>>() {
            @Override
            public void onFailure(Throwable t) {
                viewRiepilogoOrdinazione.nascondiProgressBar();
                viewRiepilogoOrdinazione.ordinazioneFallita();
            }

            @Override
            public void onSuccess(Response<List<Portata>> retData) {
                viewRiepilogoOrdinazione.nascondiProgressBar();
                if(retData.isSuccessful()){
                   viewRiepilogoOrdinazione.ordinazioneAvvenutaConSuccesso();
                }
            }
        }, portataList);

    }
    @Override
    public void salvaOrdinazione(Ordinazione ordinazione){
        ordinazione.setElementiOrdinati(null);
        viewElementiOrdinazione.mostraPorgressBar();
        ordinazioneModel.aggiungiOrdinazione(new CallbackResponse<Ordinazione>() {
            @Override
            public void onFailure(Throwable t) {
                viewRiepilogoOrdinazione.nascondiProgressBar();
                if(viewRiepilogoOrdinazione.isVisibile()) {
                    viewRiepilogoOrdinazione.ordinazioneFallita();
                }
            }

            @Override
            public void onSuccess(Response<Ordinazione> retData) {
                viewRiepilogoOrdinazione.nascondiProgressBar();
                if(retData.isSuccessful() && viewRiepilogoOrdinazione.isVisibile()){
                    viewRiepilogoOrdinazione.salvaPortate(retData.body());
                }
            }
        }, ordinazione);

    }

    @Override
    public void tornaEsploraCategorie() {
        baseView.tornaIndietro();
    }
    @Override
    public void mostraEsploraCategorie(Ordinazione ordinazione) {
        viewStartNuovaOrdinazione.mostraEsploraCategorie(ordinazione);
    }
    @Override
    public void mostraFiltraCategoria(String nomeCategoria){
        viewElementiOrdinazione.mostraPorgressBar();
        elementoMenuModel.trovaElementiPerNomeCategoria(nomeCategoria, new CallbackResponse<List<ElementoMenu>>() {
            @Override
            public void onFailure(Throwable t) {
                viewElementiOrdinazione.nascondiProgressBar();
                if(viewElementiOrdinazione.isVisibile()) {
                    viewElementiOrdinazione.impossibileFiltrareElementi("Errore di connessione, impossibile caricare gli elementi da filtrare");
                }
            }
            @Override
            public void onSuccess(Response<List<ElementoMenu>> retData) {
                viewElementiOrdinazione.nascondiProgressBar();
                if(retData.isSuccessful() && viewRiepilogoOrdinazione.isVisibile()) {
                    List<Portata> portataList = new ArrayList<>();
                    for(ElementoMenu e: retData.body()){
                        portataList.add(new Portata(e, false));
                    }
                    viewElementiOrdinazione.mostraFiltraCategoriaMenu(portataList);
                }

            }
        });


    }



}
