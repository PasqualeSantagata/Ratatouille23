package com.example.springclient.entity;

import java.io.Serializable;

public class Portata implements Serializable {
    private Long id;
    private ElementoMenu elementoMenu;
    private boolean prenotato;
    private String breveNota;


    public Portata(Long id, ElementoMenu elementoMenu, boolean prenotato, String breveNota) {
        this.id = id;
        this.elementoMenu = elementoMenu;
        this.prenotato = prenotato;
        this.breveNota = breveNota;
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
}

