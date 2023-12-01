package com.example.springclient.contract;

import com.example.springclient.entity.Utente;

public interface CreaUtenzaContract {
    interface Presenter {
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
}
