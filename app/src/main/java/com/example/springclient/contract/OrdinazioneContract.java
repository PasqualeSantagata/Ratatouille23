package com.example.springclient.contract;


import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;

import java.util.List;

public interface OrdinazioneContract {
    interface ViewRiepilogoOrdinazione extends BaseView{
        void salvaOrdinazione(List<Portata> portateOrdinazione);
        void ordinazioneAvvvenutaConSuccesso();
        void ordinazioneFallita();
    }


    interface StartNuovaOrdinazioneView extends BaseView {
        void mostraEsploraCategorie(Ordinazione ordinazione);

    }

    interface ViewElementiOrdinazione extends BaseView {

    }


    interface Presenter {
        void mostraEsploraCategorie(Ordinazione ordinazione);
        void salvaPortate(List<Portata> portataList);
        void salvaOrdinazione(Ordinazione ordinazione);
        void tornaEsploraCategorie();

    }
}
