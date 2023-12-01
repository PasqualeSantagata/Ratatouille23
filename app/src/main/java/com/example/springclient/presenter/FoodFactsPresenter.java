package com.example.springclient.presenter;

import android.util.Log;

import com.example.springclient.RetrofitService.FoodFactsRetrofit;
import com.example.springclient.apiUtils.FoodFactsResponse;
import com.example.springclient.apiUtils.ProdottoResponse;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.InserisciElementoContract;
import com.example.springclient.model.FoodFactsModel;
import com.example.springclient.view.inserisciElemento.InserisciElementoActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class FoodFactsPresenter {
    private InserisciElementoContract.InserisciElementoView inserisciElementoView;
    private FoodFactsModel foodFactsModel;


    public FoodFactsPresenter(InserisciElementoContract.InserisciElementoView inserisciElementoView){
        foodFactsModel = new FoodFactsModel(new FoodFactsRetrofit());
        this.inserisciElementoView = inserisciElementoView;
    }

    public void getElementoMenuDetails(String nome){
        foodFactsModel.getElementoMenuDetails(nome, new CallbackResponse<FoodFactsResponse>() {
            @Override
            public void onFailure(Throwable t) {
                inserisciElementoView.autocompletamentoIrrangiungibile();
            }
            @Override
            public void onSuccess(Response<FoodFactsResponse> response) {
                if(response.isSuccessful()) {

                    List<ProdottoResponse> listOfProdotto = response.body().getProducts();
                    List<String> nomi = new ArrayList<>();
                    if (listOfProdotto != null) {
                        if (listOfProdotto.size() > 0) {
                            for (ProdottoResponse p : listOfProdotto) {
                                if (p != null && p.getProduct_name() != null) {
                                    nomi.add(p.getProduct_name());
                                }
                            }
                            inserisciElementoView.generaNomi(nomi);
                        }
                    }
                }
            }
        });
    }



}
