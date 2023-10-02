package com.example.springclient.contract;

import com.example.springclient.entity.Ordinazione;
import com.example.springclient.entity.Portata;

import java.util.List;

import okhttp3.ResponseBody;

public interface OrdinazioneContract {
    interface Model{
        void getOrdinazioniSospese(CallbackResponse<List<Ordinazione>> ordinazioneCallback);
        void savePortate(CallbackResponse<List<Portata>> portataCallback, List<Portata> portataList);
        void concludiOrdinazione(CallbackResponse<Ordinazione> ordinazioneCallback, Long id);
    }

    interface View{



    }

    interface Presenter{
        void getOrdinazioniSospese();
        void savePortate(List<Portata> portataList);
        void concludiOrdinazione(long idOrdinazione);
        void aggiungiOrdinazione(Ordinazione ordinazione);
    }
}
