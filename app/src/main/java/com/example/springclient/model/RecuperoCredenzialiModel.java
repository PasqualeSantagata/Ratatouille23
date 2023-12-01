package com.example.springclient.model;

import com.example.springclient.RetrofitService.RecuperoCredenzialiAPI;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.RecuperoCredenzialiContract;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.schedulers.TestScheduler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperoCredenzialiModel {
    private final RecuperoCredenzialiAPI recuperoCredenzialiAPI;

    public RecuperoCredenzialiModel(RetrofitService retrofitService){
        recuperoCredenzialiAPI = retrofitService.getRecuperoCredenzialiAPI();
    }

    public void recuperaPassword(String email, CallbackResponse<Void> callbackResponse) {
        recuperoCredenzialiAPI.passwordDimenticata(email)
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