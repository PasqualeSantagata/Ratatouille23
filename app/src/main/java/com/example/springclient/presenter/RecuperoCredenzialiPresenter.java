package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.RecuperoCredenzialiContract;
import com.example.springclient.model.RecuperoCredenzialiModel;

import com.example.springclient.view.MainActivity;
import com.example.springclient.view.PasswordDimenticataActivity;

import retrofit2.Response;

public class RecuperoCredenzialiPresenter implements RecuperoCredenzialiContract.Presenter {

    private RecuperoCredenzialiContract.View recuperoCredenzialiView;
    private RecuperoCredenzialiModel recuperoCredenzialiModel = new RecuperoCredenzialiModel(RetrofitService.getIstance());
    private MainActivity loginActivity;
    public RecuperoCredenzialiPresenter(RecuperoCredenzialiContract.View recuperoCredenzialiView){
        this.recuperoCredenzialiView = recuperoCredenzialiView;
    }
    public RecuperoCredenzialiPresenter(MainActivity loginActivity){
        this.loginActivity = loginActivity;
    }

    @Override
    public void avviaRecuperoPassword(String email){
        recuperoCredenzialiModel.recuperaPassword(email, new CallbackResponse<Void>() {
            @Override
            public void onFailure(Throwable t) {
                recuperoCredenzialiView.erroreConnessione("Errore di connessione: impossiblie comunicare con il server");
            }
            @Override
            public void onSuccess(Response<Void> retData) {
                if(retData.isSuccessful()){
                    if(recuperoCredenzialiView!= null)
                        recuperoCredenzialiView.emailInviataCorrettamente();
                }
                else if(retData.code() == 401){
                    recuperoCredenzialiView.emailErrata();
                }
            }
        });
    }
}
