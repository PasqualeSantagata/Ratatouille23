package com.example.springclient.presenter;

import com.example.springclient.entity.Ordinazione;
import com.example.springclient.view.nuovaOrdinazione.EsploraCategorieActivity;
import com.example.springclient.view.nuovaOrdinazione.StartNuovaOrdinazioneActivity;

public class OrdinazionePresenter {
    private StartNuovaOrdinazioneActivity startNuovaOrdinazioneActivity;
    private Ordinazione ordinazione;
    private EsploraCategorieActivity esploraCategorieActivity;

    public OrdinazionePresenter(StartNuovaOrdinazioneActivity startNuovaOrdinazioneActivity){
        this.startNuovaOrdinazioneActivity = startNuovaOrdinazioneActivity;
    }

    public OrdinazionePresenter(EsploraCategorieActivity esploraCategorieActivity){
        this.esploraCategorieActivity = esploraCategorieActivity;
    }

    public void preparaOrdinazione(Ordinazione ordinazione){
        this.ordinazione = ordinazione;

    }



}
