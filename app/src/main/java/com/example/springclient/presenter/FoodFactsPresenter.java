package com.example.springclient.presenter;

import android.util.Log;

import com.example.springclient.RetrofitService.FoodFactsRetrofit;
import com.example.springclient.apiUtils.FoodFactsResponse;
import com.example.springclient.apiUtils.ProdottoResponse;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.model.FoodFactsModel;
import com.example.springclient.view.inserimentoNelMenu.InserisciElementoActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class FoodFactsPresenter {
    private InserisciElementoActivity inserisciElementoView;
    private FoodFactsRetrofit foodFactsRetrofit;
    private FoodFactsModel foodFactsModel;


    public FoodFactsPresenter(InserisciElementoActivity inserisciElementoView){
        foodFactsRetrofit = new FoodFactsRetrofit();
        foodFactsModel = new FoodFactsModel(foodFactsRetrofit);
        this.inserisciElementoView = inserisciElementoView;
    }

    public void getElementoMenuDetails(String nome){
        foodFactsModel.getElementoMenuDetails(nome, new CallbackResponse<FoodFactsResponse>() {
            @Override
            public void onFailure(Throwable t) {

            }
            @Override
            public void onSuccess(Response<FoodFactsResponse> response) {
                if(response.isSuccessful()) {

                    List<ProdottoResponse> listOfProdotto = response.body().getProducts();
                    List<String> names = new ArrayList<>();
                    if (listOfProdotto != null) {
                        Log.d("size: ", String.valueOf(listOfProdotto.size()));
                        if (listOfProdotto != null && listOfProdotto.size() > 0) {
                            for (ProdottoResponse p : listOfProdotto) {
                                if (p != null && p.getProduct_name() != null) {
                                    names.add(p.getProduct_name());
                                }
                            }
                            inserisciElementoView.generateNames(names, listOfProdotto);
                        }
                    }
                }
            }
        });
    }



}
