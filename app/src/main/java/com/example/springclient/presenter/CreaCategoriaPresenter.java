package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.CreaCategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.model.CategoriaModel;

import java.io.File;

import retrofit2.Response;


public class CreaCategoriaPresenter implements CreaCategoriaContract.Presenter {
    private CreaCategoriaContract.CreaCategoriaView creaCategoriaView;
    private CreaCategoriaContract.ScegliFotoView scegliFotoView;
    private CategoriaModel categoriaModel = new CategoriaModel(RetrofitService.getIstance());


    public CreaCategoriaPresenter(CreaCategoriaContract.CreaCategoriaView creaCategoriaView){
        this.creaCategoriaView = creaCategoriaView;
    }

    public CreaCategoriaPresenter(CreaCategoriaContract.ScegliFotoView scegliFotoView) {
        this.scegliFotoView = scegliFotoView;
    }

    @Override
    public void salavaCategoria(Categoria categoria) {
        categoriaModel.saveCategoria(categoria, new CallbackResponse<Categoria>() {
            @Override
            public void onFailure(Throwable t) {
                creaCategoriaView.erroreSalvataggioCategoria();
            }
            @Override
            public void onSuccess(Response<Categoria> retData) {
                if(retData.isSuccessful()){
                    creaCategoriaView.salvaImmagine(retData.body().getId());
                }else{

                }
            }
        });
    }

    @Override
    public void aggiungiFotoCategoria(String id, File foto) {
        categoriaModel.addFotoCategoria(id, foto, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                creaCategoriaView.impossibileContattareIlServer("Errore di connessione durante il caricamento della foto");
            }
            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    creaCategoriaView.continuaInserimento();
                }
            }
        });
    }

    @Override
    public void tornaHomeNuovoElemento() {
        creaCategoriaView.tornaIndietro();
    }


    @Override
    public void mostraScegliFoto() {
        creaCategoriaView.mostraScegliFoto();
    }

    @Override
    public void annullaInserisciImmagine() {
        scegliFotoView.tornaIndietro();
    }

    @Override
    public void caricaImmagine(byte[] immagine) {
        scegliFotoView.caricaImmagine(immagine);
    }


}
