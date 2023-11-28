package com.example.springclient.contract;

import com.example.springclient.entity.Ordinazione;

import java.util.List;

public interface GestisciComandeContract {

    interface HomeGestioneComande extends BaseView{
        void setOrdinazioniSospese(List<Ordinazione> ordinazioniSospese);


    }

    interface Presenter{
        void getOrdinazioniSospese();


        void evadiOrdinazione(long idOrdinazione);
    }


}
