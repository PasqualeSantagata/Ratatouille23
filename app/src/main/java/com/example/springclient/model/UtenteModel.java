package com.example.springclient.model;

import android.util.Log;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.RetrofitService.UtenteAPI;
import com.example.springclient.authentication.ApiToken;
import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.contract.UtenteContract;
import com.example.springclient.entity.Utente;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;

public class UtenteModel implements UtenteContract.Model {
    private UtenteContract.Presenter presenter;
    private final UtenteAPI utenteAPI;
    private static UtenteModel ISTANCE;
    private UtenteModel(RetrofitService retrofitService){
        utenteAPI = retrofitService.getUtenteAPI();
    }
    public static UtenteModel getIstance(){
        if(ISTANCE == null){
            ISTANCE = new UtenteModel(RetrofitService.getIstance());
        }
        return ISTANCE;
    }

    @Override
    public void saveUtente(Utente utente, UtenteContract.UtenteCallback<Void> utenteCallback) {
        utenteAPI.registraUtente(utente)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<Void>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<Void> apiTokenResponse) {
                        if(apiTokenResponse.isSuccessful()){
                            utenteCallback.onSuccess(null);
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }
    @Override
    public void logInUtente(AuthRequest authRequest, UtenteContract.UtenteCallback<ApiToken> utenteCallback) {
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
                            presenter.avviaAggiornaPassword();

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

    @Override
    public void forgotPassword(String email, UtenteContract.UtenteCallback<Void> utenteCallback) {
        utenteAPI.forgotPassword(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<?>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull Response<?> response) {
                        if(response.isSuccessful()){
                            /* mostrare schermata "controlla la tua casella di posta" e ritorna al login */
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });
    }

    public void setPresenter(UtenteContract.Presenter presenter){
        this.presenter = presenter;
    }

}
