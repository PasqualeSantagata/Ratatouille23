package com.example.springclient.contract;


import com.example.springclient.entity.Utente;

import retrofit2.Callback;

public interface RegisterUtenteContract {

    interface Model{

        void saveUtente(Utente utente, RegisterCallback registerCallback);
        void getUtente();

        interface RegisterCallback {

            void onFinished();
            void onFailure(Throwable t);

        }
    }

    interface View {
         void cleanFields();

    }

    interface Presenter {

        void getAllUtenti();
        void saveUtente(Utente utente);
    }

}
