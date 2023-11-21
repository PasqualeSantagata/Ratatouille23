package com.example.springclient.presenter;

import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.CreaCategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.model.CategoriaModel;

import java.io.File;

import retrofit2.Response;


public class CreaCategoriaPresenter implements CreaCategoriaContract.Presenter {
    private CreaCategoriaContract.View creaCategoriaView;
    private CategoriaModel categoriaModel = new CategoriaModel(RetrofitService.getIstance());

    public CreaCategoriaPresenter(CreaCategoriaContract.View creaCategoriaView){
        this.creaCategoriaView = creaCategoriaView;
    }

    @Override
    public void salavaCategoria(Categoria categoria) {
        categoriaModel.saveCategoria(categoria, new CallbackResponse<Categoria>() {
            @Override
            public void onFailure(Throwable t) {
                creaCategoriaView.mostraDialogErrore(creaCategoriaView.getContext(), "Impossibile comunicare con il server");
            }
            @Override
            public void onSuccess(Response<Categoria> retData) {
                if(retData.isSuccessful()){
                    creaCategoriaView.salvaImmagine(retData.body().getId());
                    Log.d("retData: ", retData.body().toString());
                }
            }
        });
    }

    @Override
    public void aggiungiFotoCategoria(String id, File foto) {
        categoriaModel.addFotoCategoria(id, foto, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    creaCategoriaView.continuaInserimento();
                }
            }
        });
    }
}
