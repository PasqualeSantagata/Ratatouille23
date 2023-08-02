package com.example.springclient.contract;


import com.example.springclient.authentication.ApiToken;
import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.entity.Utente;
import com.example.springclient.presenter.UtentePresenter;

public interface UtenteContract {

    interface Model{
        void saveUtente(Utente utente, UtenteCallback registerCallback);//
        void logInUtente(AuthRequest authRequest, UtenteCallback<ApiToken> utenteCallback);
        void forgotPassword(String email, UtenteCallback<Void> utenteCallback);

    }

    interface View {
         void cleanFields();

    }

    interface Presenter {

        void getAllUtenti();
        void logInUtente(AuthRequest authRequest);
        void saveUtente(Utente utente);
        void reimpostaPassword(String email);
        void passwordDimenticata();
        void avviaAggiornaPassword();


    }

    interface UtenteCallback<T>{

        void onFinished();
        void onFailure(Throwable t);
        void onSuccess(T retData);

    }

}
