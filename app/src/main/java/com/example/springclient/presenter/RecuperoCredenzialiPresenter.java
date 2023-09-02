package com.example.springclient.presenter;

import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.RecuperoCredenzialiContract;
import com.example.springclient.model.RecuperoCredenzialiModel;

import com.example.springclient.view.MainActivity;
import com.example.springclient.view.PasswordDimenticataActivity;

import retrofit2.Response;

public class RecuperoCredenzialiPresenter implements RecuperoCredenzialiContract.Presenter {

    private PasswordDimenticataActivity passwordDimenticataActivity;
    private RecuperoCredenzialiModel recuperoCredenzialiModel;
    private MainActivity loginActivity;
    public RecuperoCredenzialiPresenter(PasswordDimenticataActivity passwordDimenticataActivity){
        this.passwordDimenticataActivity = passwordDimenticataActivity;
        recuperoCredenzialiModel = new RecuperoCredenzialiModel();
    }
    public RecuperoCredenzialiPresenter(MainActivity loginActivity){
        this.loginActivity = loginActivity;
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
                    if(passwordDimenticataActivity!= null)
                        passwordDimenticataActivity.emailInviataCorrettamente();
                }
                else if(retData.code() == 401){
                    passwordDimenticataActivity.emailErrata();
                }
            }
        });
    }
}
