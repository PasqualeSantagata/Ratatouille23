package com.example.springclient.entity;

import java.io.Serializable;
import java.util.Comparator;

public class Portata implements Serializable {
    private Long id;
    private ElementoMenu elementoMenu;
    private boolean prenotato;
    private String breveNota;
    private Ordinazione ordinazione;

    public static final Comparator<Portata> compareNomeCrescente = Comparator.comparing(p -> p.elementoMenu.getNome());
    public static final Comparator<Portata> comparePrezzoCrescente = Comparator.comparing(p -> p.elementoMenu.getPrezzo());
    public static final Comparator<Portata> compareNomeDecrescente = (p1, p2) -> - compareNomeCrescente.compare(p1,p2);
    public static final Comparator<Portata> comparePrezzoDecrescente = (p1, p2) -> - comparePrezzoCrescente.compare(p1,p2);
    public Portata(Long id, ElementoMenu elementoMenu, boolean prenotato, String breveNota, Ordinazione ordinazione) {
        this.id = id;
        this.elementoMenu = elementoMenu;
        this.prenotato = prenotato;
        this.breveNota = breveNota;
        this.ordinazione = ordinazione;
    }

    public Portata(ElementoMenu elementoMenu, boolean prenotato) {
        this.elementoMenu = elementoMenu;
        this.prenotato = prenotato;
    }

    public Portata(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ElementoMenu getElementoMenu() {
        return elementoMenu;
    }

    public void setElementoMenu(ElementoMenu elementoMenu) {
        this.elementoMenu = elementoMenu;
    }

    public boolean isPrenotato() {
        return prenotato;
    }

    public void setPrenotato(boolean prenotato) {
        this.prenotato = prenotato;
    }

    public String getBreveNota() {
        return breveNota;
    }

    public void setBreveNota(String breveNota) {
        this.breveNota = breveNota;
    }

    public Ordinazione getOrdinazione() {
        return ordinazione;
    }

    public void setOrdinazione(Ordinazione ordinazione) {
        this.ordinazione = ordinazione;
    }
}

