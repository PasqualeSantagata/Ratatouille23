package com.example.springclient.entity;


import java.io.Serializable;
import java.util.Comparator;
import java.util.List;


public class ElementoMenu implements Serializable {
    private Long id;
    private String nome;
    private Float prezzo;
    private String descrizione;
    private List<String> elencoAllergeni;
    private String lingua;
    public static final Comparator<ElementoMenu> compareNomeCrescente = Comparator.comparing(e -> e.nome);
    public static final Comparator<ElementoMenu> comparePrezzoCrescente = Comparator.comparing(e -> e.prezzo);
    public static final Comparator<ElementoMenu> compareNomeDecrescente = (e1, e2) -> -e1.nome.compareTo(e2.getNome());
    public static final Comparator<ElementoMenu> comparePrezzoDecrescente = (e1, e2) -> -e1.prezzo.compareTo(e2.prezzo);

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

    public String getLingua() {
        return lingua;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


}
