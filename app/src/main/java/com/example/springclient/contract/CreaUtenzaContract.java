package com.example.springclient.contract;

import com.example.springclient.model.entity.Utente;

public interface CreaUtenzaContract {
    interface Presenter {
        void registraUtente(Utente utente);
        boolean validaForm(String email, String password);

    }

    interface ViewContract extends BaseViewContract {

        void registrazioneFallita();
        void registrazioneAvvenutaConSuccesso();
        void raccogliDati();
        void mostraErroreCampiVuoti();
        void mostraErrore(String messaggio);
        void disabilitaErrori();
        boolean campiValidi(String... valori);

    }
}
