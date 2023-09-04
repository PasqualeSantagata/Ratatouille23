package com.example.springclient.model;

import com.example.springclient.RetrofitService.CategoriaAPI;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.CategoriaContract;
import com.example.springclient.entity.Categoria;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class CategoriaModel implements CategoriaContract.Model {
    private CategoriaAPI categoriaAPI;

    public CategoriaModel(RetrofitService retrofitService) {
        this.categoriaAPI = retrofitService.getCategoriaAPI();
    }

    @Override
    public void saveCategoria(Categoria categoria, CallbackResponse<Void> categoriaCallback) {

    }

    @Override
    public void getAllCategorie(CallbackResponse<List<Categoria>> categoriaCallback) {
        categoriaAPI.getAllCategorie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<List<Categoria>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<List<Categoria>> listResponse) {
                       categoriaCallback.onSuccess(listResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
}
