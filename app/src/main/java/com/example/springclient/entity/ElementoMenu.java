package com.example.springclient.entity;


import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ElementoMenu {
    private String nome;
    private Float prezzo;
    private String descrizione;
    private List<String> elencoAllergeni;
    public final String lingua;
    private String tempoPreparazione;  //Magari va cambiato in float o int se li interpretiamo sempre come minuti
    private String breveNota;
    private Float quantita;


    public ElementoMenu(String nome, Float prezzo, String descrizione, List<String> elencoAllergeni, String lingua) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.elencoAllergeni = elencoAllergeni;
        this.lingua = lingua;
    }

    public void addAllergene(String allergene) {
        if (!elencoAllergeni.contains(allergene))
            elencoAllergeni.add(allergene);
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

    public String getBreveNota() {
        return breveNota;
    }

    public void setBreveNota(String nota){
        this.breveNota = nota;
    }

    public Float getQuantita() {
        return quantita;
    }

    public void setQuantita(Float quantita) {
        this.quantita = quantita;
    }
}
