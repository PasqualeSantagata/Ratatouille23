package com.example.springclient.presenter;

import android.util.Log;

import com.example.springclient.RetrofitService.AllergeneAPI;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.AllergeneContract;
import com.example.springclient.entity.Allergene;
import com.example.springclient.model.AllergeneModel;

public class AllergenePresenter implements AllergeneContract.Presenter {

    private AllergeneModel allergeneModel;
    private RetrofitService retrofitService;

    public AllergenePresenter(){
        if(retrofitService == null)
            retrofitService = RetrofitService.getIstance();

        allergeneModel = new AllergeneModel(retrofitService);
    }



    @Override
    public void saveAllergene(Allergene allergene) {
        allergeneModel.saveAllergene(allergene, new AllergeneContract.Model.AllergeneCallback() {
            @Override
            public void onFinished() {
                Log.e("allergene: ", "allergene salvato correttamente");
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}
