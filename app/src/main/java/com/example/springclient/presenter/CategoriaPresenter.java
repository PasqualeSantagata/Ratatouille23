package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.CategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.model.CategoriaModel;
import com.example.springclient.view.nuovaOrdinazione.EsploraCategorieActivity;

import java.util.List;

import retrofit2.Response;

public class CategoriaPresenter implements CategoriaContract.Presenter {

    private CategoriaModel categoriaModel;
    private EsploraCategorieActivity esploraCategorieActivity;

    public CategoriaPresenter(EsploraCategorieActivity esploraCategorieActivity) {
        this.esploraCategorieActivity = esploraCategorieActivity;
        categoriaModel = new CategoriaModel(RetrofitService.getIstance());
    }


    @Override
    public void getAllCategorie() {
        categoriaModel.getAllCategorie(new CallbackResponse<List<Categoria>>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<List<Categoria>> retData) {
                if(retData.isSuccessful()){
                    esploraCategorieActivity.setCategorie(retData.body());
                }
            }
        });
    }


}
