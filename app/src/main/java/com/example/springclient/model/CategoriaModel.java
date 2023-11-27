package com.example.springclient.model;

import android.util.Log;

import com.example.springclient.RetrofitService.CategoriaAPI;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.MostraCategoriaContract;
import com.example.springclient.entity.Categoria;
import com.example.springclient.entity.ElementoMenu;

import java.io.File;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class CategoriaModel implements MostraCategoriaContract.Model {
    private CategoriaAPI categoriaAPI;
    public CategoriaModel(RetrofitService retrofitService) {
        this.categoriaAPI = retrofitService.getCategoriaAPI();
    }

    @Override
    public void saveCategoria(Categoria categoria, CallbackResponse<Categoria> categoriaCallback) {
        categoriaAPI.saveCategoria(categoria)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Categoria>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onSuccess(@NonNull Response<Categoria> categoriaResponse) {
                        categoriaCallback.onSuccess(categoriaResponse);

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
    public void addFotoCategoria(String id, File foto, CallbackResponse<Void> categoriaCallback){
        RequestBody requestFile = RequestBody.create(foto, MediaType.parse("multipart/form-data"));
        MultipartBody.Part fileBody = MultipartBody.Part.createFormData("image", foto.getName(), requestFile);
        categoriaAPI.addFotoCategoria(id, fileBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> response) {
                        categoriaCallback.onSuccess(response);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
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
                        categoriaCallback.onFailure(e);

                    }
                });

    }

    @Override
    public void getFotoCategoriaById( String id, CallbackResponse<ResponseBody> categoriaCallback) {
        categoriaAPI.getFotoCategoriaById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ResponseBody>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }
                    @Override
                    public void onSuccess(@NonNull Response<ResponseBody> objectResponse) {
                        categoriaCallback.onSuccess(objectResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d("e", "error");

                    }
                });
    }

    public void getNomiCategorie(CallbackResponse<List<String>> callbackResponse){
        categoriaAPI.getNomiCategorie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<List<String>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<List<String>> listResponse) {
                        callbackResponse.onSuccess(listResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });


    }
    public void getNomiCategoriaDisponibili(String id, CallbackResponse<List<String>> callbackResponse){
        categoriaAPI.getNomiCategoriaDisponibili(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<List<String>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<List<String>> responseBodyResponse) {
                        callbackResponse.onSuccess(responseBodyResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
    public void aggiungiElemento(String nome, ElementoMenu elementoMenu, CallbackResponse<Void> callbackResponse){
        categoriaAPI.aggiungiElemento(nome, elementoMenu)
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

                    }
                });

    }

    public void getCategoriaByNome(String nomeCategoria, CallbackResponse<Categoria> callbackResponse){
        categoriaAPI.getCategoriaByNome(nomeCategoria)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Categoria>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Categoria> categoriaResponse) {
                        callbackResponse.onSuccess(categoriaResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callbackResponse.onFailure(e);

                    }
                });

    }

    public void modificaOrdineCategoria(Categoria categoria, CallbackResponse<Void> callbackResponse){
        categoriaAPI.modificaOrdineCategoria(categoria)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        callbackResponse.onSuccess(voidResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                            callbackResponse.onFailure(e);
                    }
                });


    }

    public void eliminaElementoDallaCategoria(String idCategoira, ElementoMenu elementoMenu, CallbackResponse<Void> callbackResponse){
        categoriaAPI.eliminaElementoDallaCategoira(idCategoira, elementoMenu)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        callbackResponse.onSuccess(voidResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callbackResponse.onFailure(e);
                    }
                });

    }


}
