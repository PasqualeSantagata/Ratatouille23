package com.example.springclient.model;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.RetrofitService.UtenteAPI;
import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.authentication.AuthenticationResponse;
import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.model.entity.Utente;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutenticazioneModel {

    private final UtenteAPI utenteAPI;

    public AutenticazioneModel(RetrofitService retrofitService){
        utenteAPI = retrofitService.getUtenteAPI();
    }


    public void registraUtente(Utente utente, CallbackResponse<ApiResponse> callbackResponse) {
        utenteAPI.registraUtente(utente).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                callbackResponse.onSuccess(response);
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                callbackResponse.onFailure(t);

            }
        });
    }

    public void logInUtente(AuthRequest authRequest, CallbackResponse<AuthenticationResponse> callbackResponse) {
        utenteAPI.logInUtente(authRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<AuthenticationResponse>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {


                    }
                    @Override
                    public void onSuccess(@NonNull Response<AuthenticationResponse> apiTokenResponse) {
                        callbackResponse.onSuccess(apiTokenResponse);

                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        callbackResponse.onFailure(e);
                    }
                });
    }

    public void logOutUtente(CallbackResponse<Void> logoutCallback){
        utenteAPI.logOutUtente()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> voidResponse) {
                        logoutCallback.onSuccess(voidResponse);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        logoutCallback.onFailure(e);
                    }
                });

    }


}
