package com.example.springclient.contract;

import com.example.springclient.apiUtils.ApiResponse;
import com.example.springclient.entity.Utente;

public interface AdminContract {
    interface Presenter{
        void registraUtente(Utente utente);

    }

    interface View extends BaseView{

        void registrazioneFallita();
        void registrazioneAvvenutaConSuccesso();
        void raccogliDati();

        void mostraErroreCampiVuoti();

        void mostraErrore(String messaggio);

        void disabilitaErrori();

        boolean campiValidi(String... valori);

    }
    interface Model{
        void registraUtente(Utente utente, CallbackResponse<ApiResponse> callbackResponse);

    }
}
