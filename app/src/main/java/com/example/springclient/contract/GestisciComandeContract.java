package com.example.springclient.contract;

import com.example.springclient.model.entity.Portata;

import java.util.List;

public interface GestisciComandeContract {

    interface HomeGestioneComande extends BaseViewContract {
        void setPortateSospese(List<Portata> ordinazioniSospese);
        void impossibileComunicareConServer(String messaggio);

        void ordinazioneConclusa(String s);
    }

    interface Presenter{
        void getOrdinazioniSospese();

        void evadiOrdinazione(long idOrdinazione);
    }


}
