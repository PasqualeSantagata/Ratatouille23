package com.example.springclient.contract;

import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;

import java.util.List;

public interface OrdinazioneContract {
    interface Model{
        void getOrdinazioniSospese(CallbackResponse<List<Ordinazione>> ordinazioneCallback);
        void savePortate(CallbackResponse<List<Portata>> portataCallback, List<Portata> portataList);
        void concludiOrdinazione(CallbackResponse<Ordinazione> ordinazioneCallback, Long id);
    }

    interface ViewOrdinazione extends BaseView{
        void salvaOrdinazione(List<Portata> portateOrdinazione);
        void ordinazioneAvvvenutaConSuccesso();
    }
    interface ViewPrenotazionePortate{
        void setOrdinazioniSospese(List<Ordinazione> ordinazioniSospese);
    }


    interface Presenter{
        void getOrdinazioniSospese();
        void salvaPortate(List<Portata> portataList);
        void concludiOrdinazione(long idOrdinazione);
        void salvaOrdinazione(Ordinazione ordinazione);
    }
}
