package com.example.springclient.model;

import com.example.springclient.RetrofitService.ElementoMenuAPI;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.ElementoMenuContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.apiUtils.ApiResponse;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ElementoMenuModel implements ElementoMenuContract.Model {
    private ElementoMenuAPI elementoMenuAPI;
    public ElementoMenuModel(RetrofitService retrofitService) {
        this.elementoMenuAPI = retrofitService.getElementoMenuAPI();
    }
    @Override
    public void salvaElementoMenu(ElementoMenu elementoMenu, ElementoMenuContract.ElementoMenuCallback<Void> elementoMenuCallback) {
        elementoMenuAPI.salvaElementoMenu(elementoMenu).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    elementoMenuCallback.onSuccess(response.body());
                }
                else{
                    if(response.code() == 412) {
                        ApiResponse[] apiResponse = new Gson().fromJson(response.errorBody().charStream(), ApiResponse[].class);
                        List<String> listOfError = new ArrayList<>();
                        for (ApiResponse a : apiResponse)
                            listOfError.add(a.getMessage());
                        elementoMenuCallback.onFinished(listOfError);
                    }
                }
            }
            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                elementoMenuCallback.onFailure(t);
            }
        });
    }

    @Override
    public void getAllElementiMenu(ElementoMenuContract.ElementoMenuCallback elementoMenuCallback) {

    }
  /*  @Override
    public void getAllElementiMenu(ElementoMenuCallback elementoMenuCallback) {
        elementoMenuAPI.getAllElementoMenu()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<Response<List<ElementoMenu>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }
                    @Override
                    public void onSuccess(@NonNull Response<List<ElementoMenu>> elementoMenus) {
                        if(elementoMenus.isSuccessful()){
                            elementoMenuCallback.onSuccess(elementoMenus.body());
                        }
                        else{
                            assert elementoMenus.errorBody() != null;
                            ApiError[] apiError = new Gson().fromJson(elementoMenus.errorBody().charStream(), ApiError[].class);
                            List<String> listOfError = new ArrayList<>();
                            for(ApiError a: apiError)
                                listOfError.add(a.getMessage());
                            elementoMenuCallback.onFinished(listOfError);
                        }
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        elementoMenuCallback.onFailure(e);
                    }
                });
    }*/
}
