package com.example.springclient.model;

import com.example.springclient.RetrofitService.OrdinazioneAPI;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.entity.Ordinazione;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class OrdinazioneModel {
    private OrdinazioneAPI ordinazioneAPI;

    public OrdinazioneModel(RetrofitService retrofitService){
        this.ordinazioneAPI = retrofitService.getOrdinazioneAPI();
    }

    public void getOrdinazioniSospese(CallbackResponse<List<Ordinazione>> ordinazioneCallback){
        ordinazioneAPI.getOrdinazioniSospese()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<List<Ordinazione>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<List<Ordinazione>> listResponse) {
                        ordinazioneCallback.onSuccess(listResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        ordinazioneCallback.onFailure(e);

                    }
                });
    }

}
