package com.example.springclient.contract;


import com.example.springclient.model.entity.Ordinazione;
import com.example.springclient.model.entity.Portata;

import java.util.List;

public interface OrdinazioneContract {
    interface RiepilogoOrdinazioneViewContract extends BaseViewContract {

        void salvaPortate(Ordinazione ordinazione);

        void ordinazioneAvvenutaConSuccesso();
        void ordinazioneFallita();
    }


    interface StartNuovaOrdinazioneViewContract extends BaseViewContract {
        void mostraEsploraCategorie(Ordinazione ordinazione);

    }

    interface ElementiOrdinazioneViewContract extends BaseViewContract {
        void mostraFiltraCategoriaMenu(List<Portata> portataList);


        void impossibileFiltrareElementi(String s);
    }


    interface Presenter {
        void mostraEsploraCategorie(Ordinazione ordinazione);
        void salvaPortate(List<Portata> portataList);
        void salvaOrdinazione(Ordinazione ordinazione);
        void tornaEsploraCategorie();
        void mostraFiltraCategoria(String nomeCategoria);
    }
}
