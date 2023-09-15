package com.example.springclient.utils;

import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Ordinazione;

public class StatoDellaOrdinazione {
    private Ordinazione ordinazione;
    private ElementoMenu elementoMenu;
    private boolean evaso;


    public StatoDellaOrdinazione(Ordinazione ordinazione, ElementoMenu elementoMenu, boolean evaso) {
        this.ordinazione = ordinazione;
        this.elementoMenu = elementoMenu;
        this.evaso = evaso;
    }

    public StatoDellaOrdinazione(Ordinazione ordinazione, ElementoMenu elementoMenu) {
        this.ordinazione = ordinazione;
        this.elementoMenu = elementoMenu;
    }

    public Ordinazione getOrdinazione() {
        return ordinazione;
    }

    public void setOrdinazione(Ordinazione ordinazione) {
        this.ordinazione = ordinazione;
    }

    public ElementoMenu getElementoMenu() {
        return elementoMenu;
    }

    public void setElementoMenu(ElementoMenu elementoMenu) {
        this.elementoMenu = elementoMenu;
    }

    public boolean isEvaso() {
        return evaso;
    }

    public void setEvaso(boolean evaso) {
        this.evaso = evaso;
    }
}

