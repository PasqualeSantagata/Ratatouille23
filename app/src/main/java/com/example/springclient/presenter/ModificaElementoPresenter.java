package com.example.springclient.presenter;

import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.ModificaElementoContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.CategoriaModel;
import com.example.springclient.model.ElementoMenuModel;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Response;

public class ModificaElementoPresenter implements ModificaElementoContract.Presenter {
    private ModificaElementoContract.ViewContract modificaElementoMenuView;
    private CategoriaModel categoriaModel = new CategoriaModel(RetrofitService.getIstance());
    private ElementoMenuModel elementoMenuModel = new ElementoMenuModel(RetrofitService.getIstance());
    private ModificaElementoContract.ViewDefinisciOrdineContract definisciOrdineView;
    public ModificaElementoPresenter(ModificaElementoContract.ViewContract modificaElementoMenuView) {
        this.modificaElementoMenuView = modificaElementoMenuView;
    }

    public ModificaElementoPresenter(ModificaElementoContract.ViewDefinisciOrdineContract definisciOrdineView) {
        this.definisciOrdineView = definisciOrdineView;
    }

    @Override
    public void tornaIndietro(){
        modificaElementoMenuView.tornaIndietro();
    }
    @Override
    public void mostraSelezionaNuovaLingua() {
        modificaElementoMenuView.mostraSelezionaNuovaLingua();
    }

    @Override
    public void mostraVisualizzaElementiDellaCategoria() {
        definisciOrdineView.tornaIndietro();
    }

    @Override
    public void getNomiCategoriaDisponibili(String id) {
        modificaElementoMenuView.mostraPorgressBar();
        categoriaModel.getNomiCategoriaDisponibili(id, new CallbackResponse<List<String>>() {
            @Override
            public void onFailure(Throwable t) {
                modificaElementoMenuView.nascondiProgressBar();
                if(modificaElementoMenuView.isVisibile()) {
                    modificaElementoMenuView.erroreModifica("Errore di connessione: impossibile caricare le categorie disponibili");
                }
            }
            @Override
            public void onSuccess(Response<List<String>> retData) {
                modificaElementoMenuView.nascondiProgressBar();
                if (retData.isSuccessful() && modificaElementoMenuView.isVisibile()) {
                    modificaElementoMenuView.setNomiCategoria(retData.body());
                    restituisciTraduzioneElemento(id);
                }
            }
        });
    }
    @Override
    public void restituisciTraduzioneElemento(String id){
        modificaElementoMenuView.mostraPorgressBar();
        elementoMenuModel.restituisciTraduzione(new CallbackResponse<ElementoMenu>() {
            @Override
            public void onFailure(Throwable t) {
                modificaElementoMenuView.nascondiProgressBar();
            }
            @Override
            public void onSuccess(Response<ElementoMenu> retData) {
                modificaElementoMenuView.nascondiProgressBar();
                modificaElementoMenuView.inizializzaComponenti();
                if(retData.isSuccessful() && modificaElementoMenuView.isVisibile()) {
                    modificaElementoMenuView.disabilitaFabInserisciTraduzione();
                }

            }
        }, id);
    }
    @Override
    public void aggiungiElementoAllaCategoria(String nome, ElementoMenu elementoMenu){
        modificaElementoMenuView.mostraPorgressBar();
        categoriaModel.aggiungiElemento(nome, elementoMenu, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                modificaElementoMenuView.nascondiProgressBar();
                if(modificaElementoMenuView.isVisibile()) {
                    modificaElementoMenuView.erroreModifica("Errore di connessione: impossibile aggiungere l'elemento alla categorai");
                }
            }
            @Override
            public void onSuccess(Response<Void> retData) {
                modificaElementoMenuView.nascondiProgressBar();
                if(modificaElementoMenuView.isVisibile()) {
                    if (retData.isSuccessful()) {
                        modificaElementoMenuView.elementoAggiuntoAdUnaCategoria(nome);
                    } else {
                        modificaElementoMenuView.elementoGiaPresenteNellaCategoria();
                    }
                }
            }
        });
    }

    @Override
    public void modificaElementoMenu(ElementoMenu elementoMenu) {
        modificaElementoMenuView.mostraPorgressBar();
        elementoMenuModel.modificaElementoMenu(elementoMenu, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                modificaElementoMenuView.nascondiProgressBar();
                Log.d("Error: ", t.toString());
                if(modificaElementoMenuView.isVisibile()) {
                    modificaElementoMenuView.erroreModifica("Errore di connessione con il server");
                }
            }

            @Override
            public void onSuccess(Response<Void> retData) {
                modificaElementoMenuView.nascondiProgressBar();
                if(retData.isSuccessful() && modificaElementoMenuView.isVisibile()){
                    modificaElementoMenuView.elementoModificatoCorrettamente();
                }
                else{
                    if(retData.code() == 412 && modificaElementoMenuView.isVisibile()){
                        ApiResponse apiResponse = new Gson().fromJson(retData.errorBody().charStream(), ApiResponse.class);
                        String errore = apiResponse.getMessage();
                        modificaElementoMenuView.mostraErrori(errore);
                    }

                }
            }
        });
    }

    @Override
    public void modificaOrdineCategoria(String nomeCategoria, int flagOrdinamento){
        definisciOrdineView.mostraPorgressBar();
        categoriaModel.modificaOrdineCategoria(nomeCategoria, flagOrdinamento, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                definisciOrdineView.nascondiProgressBar();
                if(definisciOrdineView.isVisibile()) {
                    definisciOrdineView.impossibileModificareOrdine("Errore di connessione, impossibile modificare l'ordine degli elementi");
                }
            }

            @Override
            public void onSuccess(Response<Void> retData) {
                definisciOrdineView.nascondiProgressBar();
                if(retData.isSuccessful() && definisciOrdineView.isVisibile()){
                    mostraVisualizzaElementiDellaCategoria();
                }
            }
        });


    }




}
