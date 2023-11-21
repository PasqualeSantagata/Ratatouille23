package com.example.springclient.model;

import com.example.springclient.RetrofitService.ElementoMenuAPI;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.entity.ElementoMenu;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ElementoMenuModel {
    private ElementoMenuAPI elementoMenuAPI;
    public ElementoMenuModel(RetrofitService retrofitService) {
        this.elementoMenuAPI = retrofitService.getElementoMenuAPI();
    }

    public void salvaElementoMenu(ElementoMenu elementoMenu, String categoria, CallbackResponse<Void> elementoMenuCallback) {
        elementoMenuAPI.salvaElementoMenu(elementoMenu, categoria).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                elementoMenuCallback.onSuccess(response);
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                elementoMenuCallback.onFailure(t);
            }
        });
    }



    public void getAllElementiMenu(CallbackResponse<List<ElementoMenu>> elementoMenuCallback) {
        elementoMenuAPI.getAllElementoMenu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<List<ElementoMenu>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<List<ElementoMenu>> listResponse) {
                        elementoMenuCallback.onSuccess(listResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void rimuoviElemento(CallbackResponse<Void> response, String id){
        elementoMenuAPI.rimuoviElemento(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        response.onSuccess(voidResponse);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void aggiungiLingua(CallbackResponse<Void> callback, String nome, ElementoMenu elementoMenu){
        elementoMenuAPI.aggiungiLingua(nome, elementoMenu)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
    public void restituisciTraduzione(CallbackResponse<ElementoMenu> callback, String id){
        elementoMenuAPI.restituisciTraduzione(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ElementoMenu>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<ElementoMenu> response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public void restituisciLinguaBase(CallbackResponse<ElementoMenu> callback, String id){
        elementoMenuAPI.restituisciLinguaBase(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ElementoMenu>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<ElementoMenu> response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
    public void modificaElementoMenu(ElementoMenu elementoMenu, CallbackResponse<Void> callbackResponse){
        elementoMenuAPI.modificaElementoMenu(elementoMenu)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> response) {
                        callbackResponse.onSuccess(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callbackResponse.onFailure(e);

                    }
                });

    }


}
