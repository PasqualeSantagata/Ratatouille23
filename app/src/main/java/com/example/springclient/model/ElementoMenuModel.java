package com.example.springclient.model;

import android.util.Log;

import com.example.springclient.RetrofitService.ElementoMenuAPI;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.errorUtils.ApiError;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElementoMenuModel implements ElementoMenuContract.Model {

    private ElementoMenuAPI elementoMenuAPI;

    public ElementoMenuModel(RetrofitService retrofitService) {
        this.elementoMenuAPI = retrofitService.getElementoMenuAPI();
    }

    @Override
    public void saveElementoMenu(ElementoMenu elementoMenu, ElementoMenuCallback elementoMenuCallback) {
        elementoMenuAPI.addElementoMenu(elementoMenu).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

                if(response.isSuccessful()){
                    elementoMenuCallback.onSuccess();
                }
                else{
                    assert response.errorBody() != null;
                    ApiError[] apiError = new Gson().fromJson(response.errorBody().charStream(), ApiError[].class);
                    for(ApiError a: apiError)
                        Log.e("errore: ", a.getMessage());
                   /* elementoMenuCallback.onFinished(apiError.);*/
                }

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                elementoMenuCallback.onFailure(t);

            }
        });
    }

    @Override
    public void getAllElementiMenu() {

    }
}
