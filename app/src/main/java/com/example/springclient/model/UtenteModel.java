package com.example.springclient.model;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.RetrofitService.UtenteAPI;
import com.example.springclient.contract.RegisterUtenteContract;
import com.example.springclient.entity.Utente;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UtenteModel implements RegisterUtenteContract.Model {

    private UtenteAPI utenteAPI;

    public UtenteModel(RetrofitService retrofitService){
        utenteAPI = retrofitService.getUtenteAPI();

    }

    @Override
    public void saveUtente(Utente utente, RegisterCallback registerCallback) {
        utenteAPI.registerNewUtente(utente).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                registerCallback.onFinished();
;
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }

    @Override
    public void getUtente() {

    }
}
