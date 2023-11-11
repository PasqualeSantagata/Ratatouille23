package com.example.springclient.model;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.RetrofitService.UtenteAPI;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.entity.Utente;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class UtenteModel {
    private UtenteAPI utenteAPI;
    public UtenteModel(RetrofitService retrofitService){
        utenteAPI = retrofitService.getUtenteAPI();
    }

    public void getAllCuochi(CallbackResponse<List<String>> callbackResponse){
        utenteAPI.getAllCuochi()
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





}
