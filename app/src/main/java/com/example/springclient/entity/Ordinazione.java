package com.example.springclient.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ordinazione implements Serializable {
    private Integer numeroPersone;
    private Integer tavolo;
    private Integer sala;
    private List<Portata> elementiOrdinati = new ArrayList<>();
    private boolean evasa;
    private Long id;
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

    public void aggiungiPiatto(Portata elementoMenu){
        elementiOrdinati.add(elementoMenu);
    }

    public void setElementiOrdinati(List<Portata> elementiOrdinati){
        this.elementiOrdinati = elementiOrdinati;
    }

    public boolean ordinazioneVuota(){
        return elementiOrdinati.isEmpty();
    }

    public List<Portata> getElementiOrdinati() {
        return elementiOrdinati;
    }

    public boolean isEvasa() {
        return evasa;
    }

    public void setEvasa(boolean evasa) {
        this.evasa = evasa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
