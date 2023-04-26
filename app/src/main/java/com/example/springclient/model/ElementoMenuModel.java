package com.example.springclient.model;

import android.util.Log;

import com.example.springclient.RetrofitService.ElementoMenuAPI;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.errorUtils.ApiError;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ElementoMenuModel implements ElementoMenuContract.Model {
    private ElementoMenuAPI elementoMenuAPI;
    public ElementoMenuModel(RetrofitService retrofitService) {
        this.elementoMenuAPI = retrofitService.getElementoMenuAPI();
    }
    @Override
    public void saveElementoMenu(ElementoMenu elementoMenu, ElementoMenuCallback elementoMenuCallback) {
        elementoMenuAPI.addElementoMenu(elementoMenu).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    elementoMenuCallback.onSuccess(response.body());
                }
                else{
                    assert response.errorBody() != null;
                    ApiError[] apiError = new Gson().fromJson(response.errorBody().charStream(), ApiError[].class);
                    List<String> listOfError = new ArrayList<>();
                    for(ApiError a: apiError)
                        listOfError.add(a.getMessage());
                    elementoMenuCallback.onFinished(listOfError);
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                elementoMenuCallback.onFailure(t);
            }
        });
    }
    @Override
    public void getAllElementiMenu(ElementoMenuCallback elementoMenuCallback) {
        elementoMenuAPI.getAllElementoMenu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<ElementoMenu>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onSuccess(@NonNull List<ElementoMenu> elementoMenus) {
                        elementoMenuCallback.onSuccess(elementoMenus);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                         elementoMenuCallback.onFailure(e);
                    }
                });
    }
}
