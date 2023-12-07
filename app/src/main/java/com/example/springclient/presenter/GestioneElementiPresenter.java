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
    private GestioneElementiContract.StartGestioneMenuViewContract startGestioneMenuView;
    private GestioneElementiContract.CercaElementoViewContract cercaElementoView;
    private GestioneElementiContract.HomeModificaElementoMenuView homeModificaElementoMenuView;


    public GestioneElementiPresenter(GestioneElementiContract.StartGestioneMenuViewContract startGestioneMenuView) {
        this.startGestioneMenuView = startGestioneMenuView;

    }
    public GestioneElementiPresenter(GestioneElementiContract.CercaElementoViewContract cercaElementoView) {
        this.cercaElementoView = cercaElementoView;
    }

    public GestioneElementiPresenter(GestioneElementiContract.HomeModificaElementoMenuView homeModificaElementoMenuView) {
        this.homeModificaElementoMenuView = homeModificaElementoMenuView;
    }

    @Override
    public void mostraStartGestioneMenu() {
        homeModificaElementoMenuView.tornaIndietro();
    }
    @Override
    public void mostraEsploraCategorie() {
        homeModificaElementoMenuView.mostraEsploraCategorie();
    }

    @Override
    public void mostraCercaElemento() {
        homeModificaElementoMenuView.mostraCercaElemento();
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
    public boolean filtraElementi(String elementoCercato, List<ElementoMenu> elementoMenuList, List<ElementoMenu> elementoMenuListApp){
        boolean modificato = false;
        if (elementoCercato != null && !elementoCercato.isEmpty()) {
            for (ElementoMenu e : elementoMenuListApp) {
                if (!e.getNome().toLowerCase().contains(elementoCercato.toLowerCase()) && elementoMenuList.contains(e)) {
                    modificato = elementoMenuList.remove(e);
                } else if (e.getNome().toLowerCase().contains(elementoCercato.toLowerCase()) && !elementoMenuList.contains(e)) {
                    modificato = elementoMenuList.add(e);
                }
            }
        } else {
            for (ElementoMenu e : elementoMenuListApp) {
                if (!elementoMenuList.contains(e)) {
                    modificato = elementoMenuList.add(e);
                }
            }
        }
        return modificato;
    }

    @Override
    public void getElementiMenu() {
        cercaElementoView.mostraPorgressBar();
        elementoMenuModel.getAllElementiMenu(new CallbackResponse<List<ElementoMenu>>() {
            @Override
            public void onFailure(Throwable t) {
                cercaElementoView.nascondiProgressBar();
                if(cercaElementoView.isVisibile()) {
                    cercaElementoView.impossibileContattareIlServer("Errore di rete: impossibile caricare gli elementi");
                }
            }

            @Override
            public void onSuccess(Response<List<ElementoMenu>> retData) {
                cercaElementoView.nascondiProgressBar();
                if(retData.isSuccessful() && cercaElementoView.isVisibile()){
                    cercaElementoView.caricaElementi(retData.body());
                }
            }
        });

    }

    @Override
    public void rimuoviElementoMenu(ElementoMenu elementoMenu) {
        cercaElementoView.mostraPorgressBar();
        elementoMenuModel.rimuoviElemento(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                cercaElementoView.nascondiProgressBar();
                if(cercaElementoView.isVisibile()) {
                    cercaElementoView.impossibileContattareIlServer("Errore di rete: impossibile rimuovere l'elemento");
                }
            }

            @Override
            public void onSuccess(Response<Void> retData) {
                cercaElementoView.nascondiProgressBar();
                if(retData.isSuccessful() && cercaElementoView.isVisibile()){
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
                if(cercaElementoView.isVisibile()) {
                    cercaElementoView.impossibileContattareIlServer("Errore di rete: impossibile caricare la traduzione");
                }
            }

            @Override
            public void onSuccess(Response<ElementoMenu> retData) {
                if(cercaElementoView.isVisibile()) {
                    if (retData.isSuccessful()) {
                        cercaElementoView.mostraTraduzione(retData.body());
                    } else {
                        cercaElementoView.traduzioneAssente();
                    }
                }
            }
        }, elementoMenu.getId().toString());
    }


}
