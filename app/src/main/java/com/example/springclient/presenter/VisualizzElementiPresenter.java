package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.VisualizzElementiContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.model.CategoriaModel;
import com.example.springclient.model.ElementoMenuModel;

import java.util.List;

import retrofit2.Response;

public class VisualizzElementiPresenter implements VisualizzElementiContract.Presenter {

    private ElementoMenuModel elementoMenuModel = new ElementoMenuModel(RetrofitService.getIstance());
    private CategoriaModel categoriaModel = new CategoriaModel(RetrofitService.getIstance());
    private VisualizzElementiContract.View visualizzaElementiView;
    public VisualizzElementiPresenter(VisualizzElementiContract.View visualizzaElementiView){
        this.visualizzaElementiView = visualizzaElementiView;
    }


    @Override
    public void getElementiMenu() {
        elementoMenuModel.getAllElementiMenu(new CallbackResponse<List<ElementoMenu>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<ElementoMenu>> retData) {
                if(retData.isSuccessful()){
                    visualizzaElementiView.setElementi(retData.body());
                }
            }
        });

    }

    @Override
    public void rimuoviElementoMenu(String idElemento) {
        elementoMenuModel.rimuoviElemento(new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    visualizzaElementiView.rimuoviElemento();
                }
            }
        }, idElemento);
    }

    @Override
    public void eliminaElementoDallaCategoria(Long idCategoria, ElementoMenu elementoMenu){
        categoriaModel.eliminaElementoDallaCategoria(idCategoria.toString(), elementoMenu, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    visualizzaElementiView.rimuoviElemento();
                }

            }
        });

    }

    @Override
    public void restituisciTraduzione(String idElemento) {
        elementoMenuModel.restituisciTraduzione(new CallbackResponse<ElementoMenu>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<ElementoMenu> retData) {
                if(retData.isSuccessful()) {
                    visualizzaElementiView.mostraTraduzione(retData.body());
                }else {
                    visualizzaElementiView.traduzioneAssente();
                }
            }
        }, idElemento);
    }

    @Override
    public void restituisciLinguaBase(String idElemento) {
        elementoMenuModel.restituisciLinguaBase(new CallbackResponse<ElementoMenu>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<ElementoMenu> retData) {

            }
        }, idElemento);
    }

    @Override
    public void aggiornaElementiCategoria(String nomeCategoria){
        categoriaModel.getCategoriaByNome(nomeCategoria, new CallbackResponse<Categoria>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Categoria> retData) {
                if(retData.isSuccessful()){
                    Categoria categoria = retData.body();
                    categoria.ordinaCategoria();
                    visualizzaElementiView.setElementi(categoria.getElementi());
                }
                else{

                }
            }
        });

    }


}
