package com.example.springclient.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ordinazione implements Serializable {
    private Integer numeroPersone;
    private Integer tavolo;
    private Integer sala;
    private List<ElementoMenu> elementiOrdinati = new ArrayList<>();
    private String breveNota;

    private boolean evasa;
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

    public void aggiungiPiatto( ElementoMenu elementoMenu){
        elementiOrdinati.add(elementoMenu);
    }

    public void setElementiOrdinati(List<ElementoMenu> elementiOrdinati){
        this.elementiOrdinati = elementiOrdinati;
    }

    public boolean ordinazioneVuota(){
        return elementiOrdinati.isEmpty();
    }

    public List<ElementoMenu> getElementiOrdinati() {
        return elementiOrdinati;
    }

    public String getBreveNota() {
        return breveNota;
    }

    public void setBreveNota(String breveNota) {
        this.breveNota = breveNota;
    }
    public boolean isEvasa() {
        return evasa;
    }

    public void setEvasa(boolean evasa) {
        this.evasa = evasa;
    }




}
