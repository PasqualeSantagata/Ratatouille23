package com.example.springclient.entity;

import java.util.ArrayList;
import java.util.List;

public class ElementoMenu{

    private String nome;
    private Float prezzo;
    private String descrizione;
    private List<Allergene> elencoAllergeni;

    public ElementoMenu(String nome, Float prezzo, String descrizione, List<Allergene> elencoAllergeni){
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.elencoAllergeni = elencoAllergeni;
    }

    public void addAllergene(Allergene allergene){
        if(!elencoAllergeni.contains(allergene))
            elencoAllergeni.add(allergene);
    }

    public void setElencoAllergeni(List<Allergene> elencoAllergeni){this.elencoAllergeni = elencoAllergeni;}



}
