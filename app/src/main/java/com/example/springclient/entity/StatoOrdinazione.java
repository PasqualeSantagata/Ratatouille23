package com.example.springclient.entity;

public class StatoOrdinazione {
    private Ordinazione ordinazione;
    private Portata portata;

    public StatoOrdinazione(Ordinazione ordinazione, Portata portata) {
        this.ordinazione = ordinazione;
        this.portata = portata;
    }

    public Ordinazione getOrdinazione() {
        return ordinazione;
    }

    public void setOrdinazione(Ordinazione ordinazione) {
        this.ordinazione = ordinazione;
    }

    public Portata getPortata() {
        return portata;
    }

    public void setPortata(Portata portata) {
        this.portata = portata;
    }
}
