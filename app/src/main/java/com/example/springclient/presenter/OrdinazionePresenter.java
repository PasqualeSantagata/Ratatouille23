package com.example.springclient.presenter;


import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.BaseViewContract;
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
    private OrdinazioneContract.RiepilogoOrdinazioneViewContract riepilogoOrdinazioneView;
    private OrdinazioneContract.ElementiOrdinazioneViewContract elementiOrdinazioneView;
    private OrdinazioneContract.StartNuovaOrdinazioneViewContract viewStartNuovaOrdinazione;
    private BaseViewContract baseViewContract;
    private final OrdinazioneModel ordinazioneModel = new OrdinazioneModel(RetrofitService.getIstance());
    private final ElementoMenuModel elementoMenuModel = new ElementoMenuModel(RetrofitService.getIstance());

    public OrdinazionePresenter(OrdinazioneContract.RiepilogoOrdinazioneViewContract riepilogoOrdinazioneView){
       this.riepilogoOrdinazioneView = riepilogoOrdinazioneView;
       baseViewContract = riepilogoOrdinazioneView;
    }

    public OrdinazionePresenter(OrdinazioneContract.StartNuovaOrdinazioneViewContract viewStartNuovaOrdinazione){
        this.viewStartNuovaOrdinazione = viewStartNuovaOrdinazione;
    }

    public OrdinazionePresenter(OrdinazioneContract.ElementiOrdinazioneViewContract elementiOrdinazioneView) {
        this.elementiOrdinazioneView = elementiOrdinazioneView;
        baseViewContract = elementiOrdinazioneView;
    }

    @Override
    public void salvaPortate(List<Portata> portataList){
        riepilogoOrdinazioneView.mostraPorgressBar();
        ordinazioneModel.savePortate(new CallbackResponse<List<Portata>>() {
            @Override
            public void onFailure(Throwable t) {
                riepilogoOrdinazioneView.nascondiProgressBar();
                riepilogoOrdinazioneView.ordinazioneFallita();
            }

            @Override
            public void onSuccess(Response<List<Portata>> retData) {
                riepilogoOrdinazioneView.nascondiProgressBar();
                if(retData.isSuccessful()){
                   riepilogoOrdinazioneView.ordinazioneAvvenutaConSuccesso();
                }
            }
        }, portataList);

    }
    @Override
    public void salvaOrdinazione(Ordinazione ordinazione){
        ordinazione.setElementiOrdinati(null);
        elementiOrdinazioneView.mostraPorgressBar();
        ordinazioneModel.aggiungiOrdinazione(new CallbackResponse<Ordinazione>() {
            @Override
            public void onFailure(Throwable t) {
                riepilogoOrdinazioneView.nascondiProgressBar();
                if(riepilogoOrdinazioneView.isVisibile()) {
                    riepilogoOrdinazioneView.ordinazioneFallita();
                }
            }

            @Override
            public void onSuccess(Response<Ordinazione> retData) {
                riepilogoOrdinazioneView.nascondiProgressBar();
                if(retData.isSuccessful() && riepilogoOrdinazioneView.isVisibile()){
                    riepilogoOrdinazioneView.salvaPortate(retData.body());
                }
            }
        }, ordinazione);

    }

    @Override
    public void tornaEsploraCategorie() {
        baseViewContract.tornaIndietro();
    }
    @Override
    public void mostraEsploraCategorie(Ordinazione ordinazione) {
        viewStartNuovaOrdinazione.mostraEsploraCategorie(ordinazione);
    }
    @Override
    public void mostraFiltraCategoria(String nomeCategoria){
        elementiOrdinazioneView.mostraPorgressBar();
        elementoMenuModel.trovaElementiPerNomeCategoria(nomeCategoria, new CallbackResponse<List<ElementoMenu>>() {
            @Override
            public void onFailure(Throwable t) {
                elementiOrdinazioneView.nascondiProgressBar();
                if(elementiOrdinazioneView.isVisibile()) {
                    elementiOrdinazioneView.impossibileFiltrareElementi("Errore di connessione, impossibile caricare gli elementi da filtrare");
                }
            }
            @Override
            public void onSuccess(Response<List<ElementoMenu>> retData) {
                elementiOrdinazioneView.nascondiProgressBar();
                if(retData.isSuccessful() && riepilogoOrdinazioneView.isVisibile()) {
                    List<Portata> portataList = new ArrayList<>();
                    for(ElementoMenu e: retData.body()){
                        portataList.add(new Portata(e, false));
                    }
                    elementiOrdinazioneView.mostraFiltraCategoriaMenu(portataList);
                }

            }
        });


    }



}
