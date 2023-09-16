package com.example.springclient.contract;


import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.authentication.ApiToken;
import com.example.springclient.authentication.AuthRequest;
import com.example.springclient.entity.Utente;

public interface AutenticazioneContract {

    interface Model{

        void logInUtente(AuthRequest authRequest, CallbackResponse<ApiToken> CallbackResponse);

    }

    interface View {

        void verificaCredenziali(String email, String password);

        void loginErrore();

        void dialgPrimoAccesso();
        void avviaDashboardAdmin();
        void avviaDashboardSupervisore();
        void avviaDashboardAddettoSala();
        void avviaDashboardAddettoCucina();

        void avviaRecuperoPassword();
    }
    interface Presenter {
        void logInUtente(AuthRequest authRequest);

    }

}
