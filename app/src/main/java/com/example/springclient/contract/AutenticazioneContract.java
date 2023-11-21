package com.example.springclient.contract;


import com.example.springclient.authentication.AuthenticationResponse;
import com.example.springclient.authentication.AuthRequest;

public interface AutenticazioneContract {

    interface Model{
        void logInUtente(AuthRequest authRequest, CallbackResponse<AuthenticationResponse> CallbackResponse);

    }

    interface View extends BaseView{

        void verificaCredenziali(String email, String password);

        void loginErrore();

        void dialgPrimoAccesso();
        void avviaDashboardAdmin();
        void avviaDashboardSupervisore();
        void avviaDashboardAddettoSala();
        void avviaDashboardAddettoCucina(String email);

        void avviaRecuperoPassword();
    }
    interface Presenter {
        void logInUtente(AuthRequest authRequest);

    }

}
