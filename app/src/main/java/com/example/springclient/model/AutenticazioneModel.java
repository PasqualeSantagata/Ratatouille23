package com.example.springclient.model;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.RetrofitService.UtenteAPI;
import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.authentication.ApiToken;
import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.contract.AdminContract;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.AutenticazioneContract;
import com.example.springclient.entity.Utente;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class AutenticazioneModel implements AutenticazioneContract.Model, AdminContract.Model {

    private final UtenteAPI utenteAPI;
    private static AutenticazioneModel ISTANCE;
    private AutenticazioneModel(){
        utenteAPI = RetrofitService.getIstance().getUtenteAPI();
    }
    public static AutenticazioneModel getIstance(){
        if(ISTANCE == null){
            ISTANCE = new AutenticazioneModel();
        }
        return ISTANCE;
    }

    @Override
    public void registraUtente(Utente utente, CallbackResponse<ApiResponse> callbackResponse) {
        utenteAPI.registraUtente(utente)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ApiResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<ApiResponse> apiResponse) {
                        callbackResponse.onSuccess(apiResponse);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
    @Override
    public void logInUtente(AuthRequest authRequest, CallbackResponse<ApiToken> callbackResponse) {
        utenteAPI.logInUtente(authRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<ApiToken>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {


                    }
                    @Override
                    public void onSuccess(@NonNull Response<ApiToken> apiTokenResponse) {
                        callbackResponse.onSuccess(apiTokenResponse);

                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        callbackResponse.onFailure(e);
                    }
                });
    }


}
