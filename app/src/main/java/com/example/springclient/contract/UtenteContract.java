package com.example.springclient.contract;


import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.entity.Utente;

public interface UtenteContract {

    interface Model{
        void saveUtente(Utente utente, UtenteCallback registerCallback);
        void logInUtente(AuthRequest authRequest, UtenteCallback utenteCallback);

    }

    interface View {
         void cleanFields();

    }

    interface Presenter {

        void getAllUtenti();
        void logInUtente(AuthRequest authRequest);
        void saveUtente(Utente utente);
        void reimpostaPassword(Utente utente);
        void passwordDimenticata(Utente utente);
    }

    interface UtenteCallback<T>{

        void onFinished();
        void onFailure(Throwable t);
        void onSuccess(T retData);


    }

}
