package com.example.springclient.entity;


import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ElementoMenu implements Serializable {
    private Long id;
    private String nome;
    private Float prezzo;
    private String descrizione;
    private List<String> elencoAllergeni;
    public String lingua;
    private String tempoPreparazione;  //Magari va cambiato in float o int se li interpretiamo sempre come minuti
    private Float quantita;
    private String categoria;


    public ElementoMenu(Long id, String nome, Float prezzo, String descrizione, List<String> elencoAllergeni, String lingua) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.elencoAllergeni = elencoAllergeni;
        this.lingua = lingua;
    }
    public ElementoMenu(String nome, Float prezzo, String descrizione, List<String> elencoAllergeni, String lingua) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.elencoAllergeni = elencoAllergeni;
        this.lingua = lingua;
    }

    public ElementoMenu(String nome, String descrizione, String lingua) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.lingua = lingua;
    }

    public void addAllergene(String allergene) {
        if (!elencoAllergeni.contains(allergene))
            elencoAllergeni.add(allergene);
    }

    public ElementoMenu(String nome, Float prezzo, String descrizione, List<String> elencoAllergeni) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.elencoAllergeni = elencoAllergeni;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public List<String> getElencoAllergeni() {
        return elencoAllergeni;
    }

    public void setElencoAllergeni(List<String> elencoAllergeni) {
        this.elencoAllergeni = elencoAllergeni;
    }

    public String getLingua() {
        return lingua;
    }

    public String getTempoPreparazione() {
        return tempoPreparazione;
    }

    public void setTempoPreparazione(String tempoPreparazione) {
        this.tempoPreparazione = tempoPreparazione;
    }

    public Float getQuantita() {
        return quantita;
    }

    public void setQuantita(Float quantita) {
        this.quantita = quantita;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }
}
