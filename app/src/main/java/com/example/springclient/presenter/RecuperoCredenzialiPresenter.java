package com.example.springclient.presenter;

import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.RecuperoCredenzialiContract;
import com.example.springclient.model.RecuperoCredenzialiModel;

import com.example.springclient.view.PasswordDimenticataActivity;

import retrofit2.Response;

public class RecuperoCredenzialiPresenter implements RecuperoCredenzialiContract.Presenter {

    private PasswordDimenticataActivity passwordDimenticataActivity;
    private RecuperoCredenzialiModel recuperoCredenzialiModel;

    public RecuperoCredenzialiPresenter(PasswordDimenticataActivity passwordDimenticataActivity){
        this.passwordDimenticataActivity = passwordDimenticataActivity;
        recuperoCredenzialiModel = new RecuperoCredenzialiModel();
    }

    @Override
    public void avviaRecuperoPassword(String email){
        recuperoCredenzialiModel.recuperaPassword(email, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {

            }

            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    //mostrare dialog di corretto invio della mail per il reset password
                }
                if(retData.code() == 401){
                    passwordDimenticataActivity.emailErrata();
                }
            }
        });
    }
}
