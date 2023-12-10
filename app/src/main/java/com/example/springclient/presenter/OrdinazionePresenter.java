package com.example.springclient.presenter;


import com.example.springclient.RetrofitService.RetrofitService;
import com.example.springclient.contract.BaseViewContract;
import com.example.springclient.contract.CallbackResponse;
import com.example.springclient.contract.OrdinazioneContract;
import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;
import com.example.springclient.model.ElementoMenuModel;
import com.example.springclient.model.OrdinazioneModel;
import com.example.springclient.entity.Portata;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class OrdinazionePresenter implements OrdinazioneContract.Presenter {
    private OrdinazioneContract.RiepilogoOrdinazioneViewContract riepilogoOrdinazioneView;
    private OrdinazioneContract.ElementiOrdinazioneViewContract elementiOrdinazioneView;
    private OrdinazioneContract.StartNuovaOrdinazioneViewContract viewStartNuovaOrdinazione;
    private BaseViewContract baseViewContract;
    private final StompClient stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "ws://10.0.2.2:8080/ordinazione-endpoint/websocket");
    private final OrdinazioneModel ordinazioneModel = new OrdinazioneModel(RetrofitService.getIstance());
    private final ElementoMenuModel elementoMenuModel = new ElementoMenuModel(RetrofitService.getIstance());

    public OrdinazionePresenter(OrdinazioneContract.RiepilogoOrdinazioneViewContract riepilogoOrdinazioneView){
       this.riepilogoOrdinazioneView = riepilogoOrdinazioneView;
       baseViewContract = riepilogoOrdinazioneView;
       stompClient.connect();
    }

    public OrdinazionePresenter(OrdinazioneContract.StartNuovaOrdinazioneViewContract viewStartNuovaOrdinazione){
        this.viewStartNuovaOrdinazione = viewStartNuovaOrdinazione;
    }

    public OrdinazionePresenter(OrdinazioneContract.ElementiOrdinazioneViewContract elementiOrdinazioneView) {
        this.elementiOrdinazioneView = elementiOrdinazioneView;
        baseViewContract = elementiOrdinazioneView;
    }

    @Override
    public void salvaPortate(List<Portata> portataList){
        riepilogoOrdinazioneView.mostraPorgressBar();
        ordinazioneModel.savePortate(new CallbackResponse<List<Portata>>() {
            @Override
            public void onFailure(Throwable t) {
                riepilogoOrdinazioneView.nascondiProgressBar();
                riepilogoOrdinazioneView.ordinazioneFallita();
            }

            @Override
            public void onSuccess(Response<List<Portata>> retData) {
                riepilogoOrdinazioneView.nascondiProgressBar();
                if(retData.isSuccessful()){
                    Ordinazione ordinazione = portataList.get(0).getOrdinazione();
                    riepilogoOrdinazioneView.ordinazioneAvvenutaConSuccesso();
                    stompClient.send("/app/invia-ordinazione", ordinazione.getId().toString()).subscribe();
                }
            }
        }, portataList);

    }
    @Override
    public void salvaOrdinazione(Ordinazione ordinazione){
        ZonedDateTime zdt = ZonedDateTime.ofInstant (Instant.now () , ZoneId.of ( "Europe/Paris" ));
        ordinazione.setOrarioOrdinazione(LocalTime.of(
                zdt.getHour(),
               zdt.getMinute()
            ).toString());
        ordinazione.setElementiOrdinati(null);
        riepilogoOrdinazioneView.mostraPorgressBar();
        ordinazioneModel.aggiungiOrdinazione(new CallbackResponse<Ordinazione>() {
            @Override
            public void onFailure(Throwable t) {
                riepilogoOrdinazioneView.nascondiProgressBar();
                if(riepilogoOrdinazioneView.isVisibile()) {
                    riepilogoOrdinazioneView.ordinazioneFallita();
                }
            }

            @Override
            public void onSuccess(Response<Ordinazione> retData) {
                riepilogoOrdinazioneView.nascondiProgressBar();
                if(retData.isSuccessful() && riepilogoOrdinazioneView.isVisibile()){
                    riepilogoOrdinazioneView.salvaPortate(retData.body());
                }
            }
        }, ordinazione);

    }

    @Override
    public void tornaEsploraCategorie() {
        baseViewContract.tornaIndietro();
    }
    @Override
    public void mostraEsploraCategorie(Ordinazione ordinazione) {
        viewStartNuovaOrdinazione.mostraEsploraCategorie(ordinazione);
    }
    @Override
    public void mostraFiltraCategoria(String nomeCategoria){
        elementiOrdinazioneView.mostraPorgressBar();
        elementoMenuModel.trovaElementiPerNomeCategoria(nomeCategoria, new CallbackResponse<List<ElementoMenu>>() {
            @Override
            public void onFailure(Throwable t) {
                elementiOrdinazioneView.nascondiProgressBar();
                if(elementiOrdinazioneView.isVisibile()) {
                    elementiOrdinazioneView.impossibileFiltrareElementi("Errore di connessione, impossibile caricare gli elementi da filtrare");
                }
            }
            @Override
            public void onSuccess(Response<List<ElementoMenu>> retData) {
                elementiOrdinazioneView.nascondiProgressBar();
                if(retData.isSuccessful() && riepilogoOrdinazioneView.isVisibile()) {
                    List<Portata> portataList = new ArrayList<>();
                    for(ElementoMenu e: retData.body()){
                        portataList.add(new Portata(e, false));
                    }
                    elementiOrdinazioneView.mostraFiltraCategoriaMenu(portataList);
                }

            }
        });


    }

    public StompClient getStompClient() {
        return stompClient;
    }
}
