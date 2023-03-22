package com.example.springclient.presenter;

import com.example.springclient.contract.RegisterUtenteContract;
import com.example.springclient.model.UtenteModel;
import com.example.springclient.view.MainActivity;
import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.entity.Utente;

public class RegisterPresenter implements RegisterUtenteContract.Presenter {
    private UtenteModel utenteModel;
    private RetrofitService retrofitService;
    private RegisterUtenteContract.View registerActivity;


    public RegisterPresenter(RegisterUtenteContract.View registerActivity){
        if(retrofitService == null)
            retrofitService = RetrofitService.getIstance();

        utenteModel = new UtenteModel(retrofitService);
        this.registerActivity = registerActivity;

    }

    @Override
    public void getAllUtenti(){


    }

    @Override
    public void saveUtente(Utente utente) {
        utenteModel.saveUtente(utente, new RegisterUtenteContract.Model.RegisterCallback() {
            @Override
            public void onFinished() {
                registerActivity.cleanFields();
            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }
}
