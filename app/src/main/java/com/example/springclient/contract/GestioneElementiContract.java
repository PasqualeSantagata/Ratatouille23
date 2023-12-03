package com.example.springclient.contract;


import com.example.springclient.entity.ElementoMenu;

import java.util.List;

public interface GestioneElementiContract {

    interface StartGestioneMenuView extends BaseView {
        void mostraHomeModificaElementoMenu();
        void mostraHomeNuovoElemento();
    }

    interface CercaElementoView extends BaseView {
        void mostraTraduzione(ElementoMenu elementoMenu);

        void traduzioneAssente();

        void mostraModificaElemento(ElementoMenu elementoMenu);
        void elementoEliminato();

        void impossibileContattareIlServer(String messaggio);

        void rimuoviElementoDalMenu();
        void caricaElementi(List<ElementoMenu> elementoMenuList);
    }

    interface HomeModificaElementoMenuView extends BaseView {
        void mostraEsploraCategorie();

        void mostraCercaElemento();
    }


    interface Presenter{
        void mostraEsploraCategorie();

        void mostraCercaElemento();
        void tornaDashboardAdmin();
        void mostraHomeModificaElementoMenu();
        void mostraHomeNuovoElemento();
        void tornaStartGestioneMenu();
        void avviaRimuoviElemento();

        void mostraModificaElemento(ElementoMenu elementoMenu);

        void getElementiMenu();

        void rimuoviElementoMenu(ElementoMenu elementoMenu);

        void restituisciTraduzione(ElementoMenu elementoMenu);

        void mostraStartGestioneMenu();
    }




}
