package com.example.springclient.contract;

import com.example.springclient.model.entity.ElementoMenu;
import java.util.List;

public interface InserisciElementoContract {

    interface InserisciElementoViewContract extends BaseViewContract {
        void mostraSelezioneNuovaLingua();
        void elementoInseritoCorrettamente();
        void mostraErroreInserimentoElemento(String errore);
        void erroreInserimentoElemento();
        void generaNomi(List<String> nomi);
        void mostraHomeNuovoElemento();
        void autocompletamentoIrrangiungibile();
    }

    interface HomeNuovoElmentoViewContract extends BaseViewContract {
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

        boolean validaInserimentoElemento(String nome, String prezzo, String descrizione);
    }

}
