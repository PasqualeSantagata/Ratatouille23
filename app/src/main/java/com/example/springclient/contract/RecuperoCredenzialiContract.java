package com.example.springclient.contract;

public interface RecuperoCredenzialiContract {
    interface Presenter {
        void avviaRecuperoPassword(String email);

        void avviaAggiornaPassword(String email);
    }

    interface ViewContract extends BaseViewContract {
        void verificaEmail(String email);

        void emailInviataCorrettamente();

        void emailErrata();

        void erroreConnessione(String impossiblieComunicareConIlServer);
    }

}
