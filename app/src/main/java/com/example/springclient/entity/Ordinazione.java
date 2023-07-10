package com.example.springclient.entity;

import java.io.Serializable;
import java.util.List;

public class Ordinazione implements Serializable {
    private Integer numeroPersone;
    private Integer tavolo;
    private Integer sala;

    private List<ElementoMenu> elementiOrdinati;

    public Ordinazione() {
    }

    public Ordinazione(Integer numeroPersone, Integer tavolo, Integer sala) {
        this.numeroPersone = numeroPersone;
        this.tavolo = tavolo;
        this.sala = sala;
    }

    public Integer getNumeroPersone() {
        return numeroPersone;
    }

    public void setNumeroPersone(Integer numeroPersone) {
        this.numeroPersone = numeroPersone;
    }

    public Integer getTavolo() {
        return tavolo;
    }

    public void setTavolo(Integer tavolo) {
        this.tavolo = tavolo;
    }

    public Integer getSala() {
        return sala;
    }

    public void setSala(Integer sala) {
        this.sala = sala;
    }

    public void aggiungiPiatto(List<ElementoMenu> piattiOrdinati, ElementoMenu elementoMenu){
        piattiOrdinati.add(elementoMenu);
    }

    public boolean ordinazioneVuota(){
        return elementiOrdinati.isEmpty();
    }

}
