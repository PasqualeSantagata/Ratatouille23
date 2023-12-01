package com.example.springclient.presenter;

import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.InserisciElementoContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.CategoriaModel;
import com.example.springclient.model.ElementoMenuModel;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Response;

public class InserisciElementoPresenter implements InserisciElementoContract.Presenter{
    private ElementoMenuModel elementoMenuModel= new ElementoMenuModel(RetrofitService.getIstance());
    private CategoriaModel categoriaModel = new CategoriaModel(RetrofitService.getIstance());
    private InserisciElementoContract.InserisciElementoView inserisciElementoView;
    private InserisciElementoContract.HomeNuovoElmentoView homeNuovoElementoView;

    public InserisciElementoPresenter(InserisciElementoContract.InserisciElementoView inserisciElementoView){
        this.inserisciElementoView = inserisciElementoView;
    }

    public InserisciElementoPresenter(InserisciElementoContract.HomeNuovoElmentoView homeNuovoElementoView) {
        this.homeNuovoElementoView = homeNuovoElementoView;
    }

    @Override
    public void inserisciElementoMenu(ElementoMenu elementoMenu, String categoria) {
        elementoMenuModel.salvaElementoMenu(elementoMenu, categoria, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.getMessage());
                inserisciElementoView.erroreInserimentoElemento();
            }

            @Override
            public void onSuccess(Response<Void> response) {
                if(response.isSuccessful()){
                    inserisciElementoView.elementoInseritoCorrettamente();
                }
                else{
                    if(response.code() == 412) {
                        ApiResponse apiResponse = new Gson().fromJson(response.errorBody().charStream(), ApiResponse.class);
                        String errore = apiResponse.getMessage();
                        inserisciElementoView.mostraErroreInserimentoElemento(errore);
                    }
                }
            }
        });
    }

    @Override
    public void getNomiCategorie() {
        categoriaModel.getNomiCategorie(new CallbackResponse<List<String>>() {
            @Override
            public void onFailure(Throwable t) {
                homeNuovoElementoView.erroreComunicazioneServer("Errore di connessione con il server riporvare succesivamente");
            }
            @Override
            public void onSuccess(Response<List<String>> retData) {
                if(retData.isSuccessful()){
                    homeNuovoElementoView.setNomiCategorie(retData.body());
                }
            }
        });
    }

    @Override
    public void mostraCreaCategoria() {
        homeNuovoElementoView.mostraCreaCategoria();
    }
    @Override
    public void mostraInserisciElemento() {
        homeNuovoElementoView.mostraInserisciElemento();
    }
    @Override
    public void mostraStartGestioneMenu() {
        homeNuovoElementoView.tornaIndietro();
    }
    @Override
    public void tornaStartGestioneMenu(){
        inserisciElementoView.tornaIndietro();
    }

    @Override
    public void mostraHomeNuovoElemento() {
        inserisciElementoView.mostraHomeNuovoElemento();
    }

    @Override
    public void mostraSelezioneNuovaLingua() {
        inserisciElementoView.mostraSelezioneNuovaLingua();
    }
}
