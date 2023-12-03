package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;

import java.util.List;

public interface InserisciElementoContract {

    interface InserisciElementoView extends BaseView{
        void mostraSelezioneNuovaLingua();
        void elementoInseritoCorrettamente();
        void mostraErroreInserimentoElemento(String errore);
        void erroreInserimentoElemento();
        void generaNomi(List<String> nomi);
        void mostraHomeNuovoElemento();
        void autocompletamentoIrrangiungibile();
    }

    interface HomeNuovoElmentoView extends BaseView {
        void erroreComunicazioneServer(String messaggio);
        void setNomiCategorie(List<String> nomiCategoiraList);
        void mostraCreaCategoria();
        void mostraInserisciElemento();
    }

    interface Presenter {
        void inserisciElementoMenu(ElementoMenu elementoMenu, String categoria);
        void getNomiCategorie();
        void mostraCreaCategoria();
        void mostraInserisciElemento();
        void mostraStartGestioneMenu();
        void tornaStartGestioneMenu();
        void mostraHomeNuovoElemento();
        void mostraSelezioneNuovaLingua();

    }

}
