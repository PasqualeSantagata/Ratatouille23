package com.example.springclient.entity;

import androidx.dynamicanimation.animation.FlingAnimation;

import java.util.ArrayList;
import java.util.List;

public class ElementoMenu{

    private String nome;
    private Float prezzo;
    private String descrizione;
    private List<String> elencoAllergeni;

    public final String lingua;


    public ElementoMenu(String nome, Float prezzo, String descrizione, List<String> elencoAllergeni, String lingua){
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.elencoAllergeni = elencoAllergeni;
        this.lingua = lingua;

    }

    public void addAllergene(String allergene){
        if(!elencoAllergeni.contains(allergene))
            elencoAllergeni.add(allergene);
    }

    public void setElencoAllergeni(List<String> elencoAllergeni){this.elencoAllergeni = elencoAllergeni;}



}
