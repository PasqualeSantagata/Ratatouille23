package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.GestioneElementiContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.ElementoMenuModel;


import java.util.List;

import retrofit2.Response;

public class GestioneElementiPresenter implements GestioneElementiContract.Presenter {

    private ElementoMenuModel elementoMenuModel = new ElementoMenuModel(RetrofitService.getIstance());
    private GestioneElementiContract.StartGestioneMenuView startGestioneMenuView;
    private GestioneElementiContract.CercaElementoView cercaElementoView;
    private GestioneElementiContract.HomeModificaElemMenu homeModificaElemMenuView;


    public GestioneElementiPresenter(GestioneElementiContract.StartGestioneMenuView startGestioneMenuView) {
        this.startGestioneMenuView = startGestioneMenuView;

    }
    public GestioneElementiPresenter(GestioneElementiContract.CercaElementoView cercaElementoView) {
        this.cercaElementoView = cercaElementoView;
    }

    public GestioneElementiPresenter(GestioneElementiContract.HomeModificaElemMenu homeModificaElemMenuView) {
        this.homeModificaElemMenuView = homeModificaElemMenuView;
    }

    @Override
    public void mostraStartGestioneMenu() {
        homeModificaElemMenuView.tornaIndietro();
    }
    @Override
    public void mostraEsploraCategorie() {
        homeModificaElemMenuView.mostraEsploraCategorie();
    }

    @Override
    public void mostraCercaElemento() {
        homeModificaElemMenuView.mostraCercaElemento();
    }

    @Override
    public void tornaDashboardAdmin() {
        startGestioneMenuView.tornaIndietro();
    }

    @Override
    public void mostraHomeModificaElementoMenu() {
        startGestioneMenuView.mostraHomeModificaElementoMenu();
    }

    @Override
    public void mostraHomeNuovoElemento() {
        startGestioneMenuView.mostraHomeNuovoElemento();

    }

    @Override
    public void tornaStartGestioneMenu(){
        cercaElementoView.tornaIndietro();
    }


    @Override
    public void avviaRimuoviElemento() {
        cercaElementoView.rimuoviElementoDalMenu();
    }

    @Override
    public void mostraModificaElemento(ElementoMenu elementoMenu){
        cercaElementoView.mostraModificaElemento(elementoMenu);
    }

    @Override
    public void getElementiMenu() {
        elementoMenuModel.getAllElementiMenu(new CallbackResponse<List<ElementoMenu>>() {
            @Override
            public void onFailure(Throwable t) {
                cercaElementoView.impossibileContattareIlServer("Errore di rete: impossibile caricare gli elementi");
            }

            @Override
            public void onSuccess(Response<List<ElementoMenu>> retData) {
                if(retData.isSuccessful()){
                    cercaElementoView.caricaElementi(retData.body());
                }
            }
        });

    }

    @Override
    public void rimuoviElementoMenu(ElementoMenu elementoMenu) {
        elementoMenuModel.rimuoviElemento(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                cercaElementoView.impossibileContattareIlServer("Errore di rete: impossibile rimuovere l'elemento");
            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    cercaElementoView.elementoEliminato();
                }
            }
        }, elementoMenu.getId().toString());
    }

    @Override
    public void restituisciTraduzione(ElementoMenu elementoMenu) {
        elementoMenuModel.restituisciTraduzione(new CallbackResponse<ElementoMenu>() {
            @Override
            public void onFailure(Throwable t) {
                cercaElementoView.impossibileContattareIlServer("Errore di rete: impossibile caricare la traduzione");
            }

            @Override
            public void onSuccess(Response<ElementoMenu> retData) {
                if(retData.isSuccessful()) {
                    cercaElementoView.mostraTraduzione(retData.body());
                }else {
                    cercaElementoView.traduzioneAssente();
                }
            }
        }, elementoMenu.getId().toString());
    }


}
