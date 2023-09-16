package com.example.springclient.utils;

import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;

public class Portata {
    private Long id;
    private ElementoMenu elementoMenu;
    private boolean prenotato;


    public Portata(Long id, ElementoMenu elementoMenu, boolean prenotato) {
        this.id = id;
        this.elementoMenu = elementoMenu;
        this.prenotato = prenotato;
    }

    public Portata(ElementoMenu elementoMenu, boolean prenotato) {
        this.elementoMenu = elementoMenu;
        this.prenotato = prenotato;
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
}

