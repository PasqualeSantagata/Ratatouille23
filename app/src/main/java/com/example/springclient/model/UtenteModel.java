package com.example.springclient.model;

import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.RetrofitService.UtenteAPI;
import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.contract.UtenteContract;
import com.example.springclient.entity.Utente;
import com.example.springclient.authentication.ApiToken;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class UtenteModel implements UtenteContract.Model {

    private final UtenteAPI utenteAPI;

    public UtenteModel(RetrofitService retrofitService){
        utenteAPI = retrofitService.getUtenteAPI();

    }

    @Override
    public void saveUtente(Utente utente, UtenteContract.UtenteCallback utenteCallbackCallback) {


    }
    @Override
    public void logInUtente(AuthRequest authRequest, UtenteContract.UtenteCallback utenteCallback) {
        utenteAPI.logInUtente(authRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ApiToken>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {


                    }

                    @Override
                    public void onSuccess(@NonNull Response<ApiToken> apiTokenResponse) {
                        if(apiTokenResponse.isSuccessful()){
                            utenteCallback.onSuccess(apiTokenResponse.body());
                        }
                        else if(apiTokenResponse.code() == 403){

                        }
                        else {
                            utenteCallback.onFinished();
                            Log.d("login response: ", String.valueOf(apiTokenResponse.code()));

                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }


}
