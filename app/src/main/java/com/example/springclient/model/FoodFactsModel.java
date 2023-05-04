package com.example.springclient.model;

import android.util.Log;

import com.example.springclient.RetrofitService.FoodFactsAPI;
import com.example.springclient.RetrofitService.FoodFactsRetrofit;
import com.example.springclient.apiUtils.FoodFactsResponse;
import com.example.springclient.contract.ElementoMenuContract;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;


public class FoodFactsModel {
    private FoodFactsAPI foodFactsAPI;

    public FoodFactsModel(FoodFactsRetrofit foodFactsRetrofit){
        foodFactsAPI = foodFactsRetrofit.getFoodFactsAPI();
    }

    public void getElementoMenuDetails(String nome, ElementoMenuContract.Model.ElementoMenuCallback<FoodFactsResponse> callback){
        foodFactsAPI.getElementoMenuDetails(nome)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<FoodFactsResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<FoodFactsResponse> foodFactsResponseResponse) {
                        callback.onSuccess(foodFactsResponseResponse.body());

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

}
