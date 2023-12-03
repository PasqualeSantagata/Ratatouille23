package com.example.springclient.contract;


import com.example.springclient.authentication.AuthRequest;

public interface AutenticazioneContract {

    interface View extends BaseView{

        void verificaCredenziali(String email, String password);

        void loginErrore();

        void dialgPrimoAccesso();
        void avviaDashboardAdmin();

        void impossibileContattareIlServer(String messaggio);
        void avviaDashboardSupervisore();
        void avviaDashboardAddettoSala();
        void avviaDashboardAddettoCucina(String email);

        void avviaRecuperoPassword();
    }
    interface Presenter {
        void logInUtente(AuthRequest authRequest);

        void logOutUtente();

        void avviaRecuperoPassword();
    }

}
