package com.example.springclient.model;

import com.example.springclient.RetrofitService.AnalyticsAPI;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.analytics.AnalyticsData;
import com.example.springclient.contract.CallbackResponse;

import org.jetbrains.annotations.Async;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class AnalyticsModel {
    private AnalyticsAPI anlayticsAPI;

    public AnalyticsModel(RetrofitService retrofitService){
        this.anlayticsAPI = retrofitService.getAnalyticsAPI();
    }

    public void getAnalytics(CallbackResponse<List<AnalyticsData>> callbackResponse, String dataInizio, String dataFine){
        anlayticsAPI.getAnalytics(dataInizio, dataFine).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<List<AnalyticsData>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<List<AnalyticsData>> listResponse) {
                        callbackResponse.onSuccess(listResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        callbackResponse.onFailure(e);
                    }
                });

    }

}
