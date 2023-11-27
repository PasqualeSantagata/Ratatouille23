package com.example.springclient.presenter;

import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.ModificaElementoContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.CategoriaModel;
import com.example.springclient.model.ElementoMenuModel;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Response;

public class ModificaElementoPresenter implements ModificaElementoContract.Presenter {
    private ModificaElementoContract.View modificaElementoMenuView;
    private CategoriaModel categoriaModel = new CategoriaModel(RetrofitService.getIstance());
    private ElementoMenuModel elementoMenuModel = new ElementoMenuModel(RetrofitService.getIstance());
    private ModificaElementoContract.ViewAggiungiLingua aggiungiLinguaView;
    private ModificaElementoContract.ViewDefinisciOrdine definisciOrdineView;
    public ModificaElementoPresenter(ModificaElementoContract.View modificaElementoMenuView) {
        this.modificaElementoMenuView = modificaElementoMenuView;
    }
    public ModificaElementoPresenter(ModificaElementoContract.ViewAggiungiLingua aggiungiLinguaView) {
        this.aggiungiLinguaView = aggiungiLinguaView;
    }

    public ModificaElementoPresenter(ModificaElementoContract.ViewDefinisciOrdine definisciOrdineView) {
        this.definisciOrdineView = definisciOrdineView;
    }

    @Override
    public void getNomiCategoriaDisponibili(String id) {
        categoriaModel.getNomiCategoriaDisponibili(id, new CallbackResponse<List<String>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<String>> retData) {
                if (retData.isSuccessful()) {
                    modificaElementoMenuView.setNomiCategoria(retData.body());
                    restituisciTraduzioneElemento(id);
                }
            }
        });
    }

    public void restituisciTraduzioneElemento(String id){
        elementoMenuModel.restituisciTraduzione(new CallbackResponse<ElementoMenu>() {
            @Override
            public void onFailure(Throwable t) {

            }
            @Override
            public void onSuccess(Response<ElementoMenu> retData) {
                modificaElementoMenuView.initializeComponents();
                if(retData.isSuccessful()) {
                    modificaElementoMenuView.disabilitaFabInserisciTraduzione();
                }

            }
        }, id);
    }
    @Override
    public void aggiungiElementoAllaCategoria(String nome, ElementoMenu elementoMenu){
        categoriaModel.aggiungiElemento(nome, elementoMenu, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }
            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    modificaElementoMenuView.elementoAggiuntoAdUnaCategoria(nome);
                }
                else{
                    modificaElementoMenuView.elementoGiaPresenteNellaCategoria();
                }
            }
        });
    }

    @Override
    public void modificaElementoMenu(ElementoMenu elementoMenu) {
        elementoMenuModel.modificaElementoMenu(elementoMenu, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                Log.d("Error: ", t.toString());
                modificaElementoMenuView.erroreModifica();
            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    modificaElementoMenuView.elementoModificatoCorrettamente();
                }
                else{
                    if(retData.code() == 412){
                        ApiResponse apiResponse = new Gson().fromJson(retData.errorBody().charStream(), ApiResponse.class);
                        String errore = apiResponse.getMessage();
                        modificaElementoMenuView.mostraErrori(errore);
                    }

                }
            }
        });
    }

    @Override
    public void aggiungiLingua(String elementoMenu, ElementoMenu elmentoTradotto){
        elementoMenuModel.aggiungiLingua(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    aggiungiLinguaView.linguaAggiunta();
                }
                else{

                }
            }
        }, elementoMenu,elmentoTradotto);
    }

    @Override
    public void modificaOrdineCategoria(Categoria categoria){
        categoriaModel.modificaOrdineCategoria(categoria, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){

                }
            }
        });


    }




}
