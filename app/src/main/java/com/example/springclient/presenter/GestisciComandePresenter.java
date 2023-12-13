package com.example.springclient.presenter;

import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.GestisciComandeContract;
import com.example.springclient.model.entity.Portata;
import com.example.springclient.model.OrdinazioneModel;
import com.example.springclient.model.entity.Ordinazione;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class GestisciComandePresenter implements GestisciComandeContract.Presenter{
    private final OrdinazioneModel ordinazioneModel = new OrdinazioneModel(RetrofitService.getIstance());
    private GestisciComandeContract.HomeGestioneComande homeGestioneComande;
    private List<Ordinazione> ordinazioni = new ArrayList<>();

    public GestisciComandePresenter(GestisciComandeContract.HomeGestioneComande homeGestioneComande) {
        this.homeGestioneComande = homeGestioneComande;
    }

    @Override
    public void getOrdinazioniSospese() {
        ordinazioneModel.getOrdinazioniSospese(new CallbackResponse<List<Ordinazione>>() {
            @Override
            public void onFailure(Throwable t) {
                if(homeGestioneComande.isVisibile()) {
                    homeGestioneComande.impossibileComunicareConServer("Errore di comunicazione con il server, impossibile caricare le comande");
                }
            }
            @Override
            public void onSuccess(Response<List<Ordinazione>> retData) {
                if ((retData.isSuccessful()) && homeGestioneComande.isVisibile()){
                    ordinazioni = retData.body();
                    List<Portata> portateSospeseList = new ArrayList<>();
                    for(Ordinazione o: retData.body()){
                        for(Portata p: o.getElementiOrdinati()){
                            if(!p.isPrenotato()) {
                                p.setOrdinazione(o);
                                portateSospeseList.add(p);
                            }
                        }
                    }
                    homeGestioneComande.setPortateSospese(portateSospeseList);
                }
            }
        });
    }

    @Override
    public void evadiOrdinazione(long idPortata) {
        boolean completo = true;
        long idOrdinazione = -1;
        List<Portata> portataList = new ArrayList<>();
        for (Ordinazione o : ordinazioni) {
            for (Portata p : o.getElementiOrdinati()) {
                if (p.getId().equals(idPortata)) {
                    portataList = o.getElementiOrdinati();
                    idOrdinazione = o.getId();
                }
            }
        }
        for (Portata p : portataList) {
            if (!p.isPrenotato()) {
                completo = false;
                break;
            }
        }
        if (completo && idOrdinazione > 0) {
            ordinazioneModel.concludiOrdinazione(new CallbackResponse<Ordinazione>() {
                @Override
                public void onFailure(Throwable t) {
                    if(homeGestioneComande.isVisibile()) {
                        homeGestioneComande.ordinazioneConclusa("Errore di comunicazione con il server durante l'evasione dell'ordinazione");
                    }
                }
                @Override
                public void onSuccess(Response<Ordinazione> retData) {
                    if(retData.isSuccessful() && homeGestioneComande.isVisibile()){
                        Ordinazione ordinazione = retData.body();
                        homeGestioneComande.ordinazioneConclusa("ordinazione n° "+ ordinazione.getId()+ " conclusa");

                    }
                }
            },idOrdinazione);
        }
    }

}
