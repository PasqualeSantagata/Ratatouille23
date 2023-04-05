package com.example.springclient.model;

import com.example.springclient.RetrofitService.AllergeneAPI;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.AllergeneContract;
import com.example.springclient.entity.Allergene;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllergeneModel implements AllergeneContract.Model {

    private AllergeneAPI allergeneAPI;

    public AllergeneModel(RetrofitService retrofitService){
        allergeneAPI = retrofitService.getAllergeneAPI();
    }

    @Override
    public void saveAllergene(Allergene allergene, AllergeneCallback allergeneCallback) {
        allergeneAPI.saveAllergene(allergene).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                allergeneCallback.onFinished();
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });

    }
}
