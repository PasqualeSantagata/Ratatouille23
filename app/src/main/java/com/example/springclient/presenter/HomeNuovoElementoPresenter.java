package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.HomeNuovoElementoContract;
import com.example.springclient.model.CategoriaModel;

import java.util.List;

import retrofit2.Response;

public class HomeNuovoElementoPresenter implements HomeNuovoElementoContract.Presenter {
    private HomeNuovoElementoContract.View homeNuovoElementoView;
    private CategoriaModel categoriaModel = new CategoriaModel(RetrofitService.getIstance());

    public HomeNuovoElementoPresenter(HomeNuovoElementoContract.View homeNuovoElementoView){
        this.homeNuovoElementoView = homeNuovoElementoView;
    }

    @Override
    public void getNomiCategorie() {
        categoriaModel.getNomiCategorie(new CallbackResponse<List<String>>() {
            @Override
            public void onFailure(Throwable t) {

            }
            @Override
            public void onSuccess(Response<List<String>> retData) {
                if(retData.isSuccessful()){
                    homeNuovoElementoView.setNomiCategorie(retData.body());
                }
            }
        });
    }
}
