package com.example.springclient.model;

import com.example.springclient.RetrofitService.RecuperoCredenzialiAPI;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.RecuperoCredenzialiContract;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecuperoCredenzialiModel {
    private final RecuperoCredenzialiAPI recuperoCredenzialiAPI = RetrofitService.getIstance().getRecuperoCredenzialiAPI();

    public void recuperaPassword(String email, CallbackResponse<Void> callbackResponse) {
        recuperoCredenzialiAPI.passwordDimenticata(email)
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        callbackResponse.onSuccess(response);

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
    }

}