package com.example.springclient.contract;


import com.example.springclient.model.entity.ElementoMenu;

import java.util.List;

public interface GestioneElementiContract {

    interface StartGestioneMenuViewContract extends BaseViewContract {
        void mostraHomeModificaElementoMenu();
        void mostraHomeNuovoElemento();
    }

    interface CercaElementoViewContract extends BaseViewContract {
        void mostraTraduzione(ElementoMenu elementoMenu);

        void traduzioneAssente();

        void mostraModificaElemento(ElementoMenu elementoMenu);
        void elementoEliminato();

        void impossibileContattareIlServer(String messaggio);

        void rimuoviElementoDalMenu();
        void caricaElementi(List<ElementoMenu> elementoMenuList);
    }

    interface HomeModificaElementoMenuView extends BaseViewContract {
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

        boolean filtraElementi(String elementoCercato, List<ElementoMenu> elementoMenuList, List<ElementoMenu> elementoMenuListApp);

        void getElementiMenu();

        void rimuoviElementoMenu(ElementoMenu elementoMenu);

        void restituisciTraduzione(ElementoMenu elementoMenu);

        void mostraStartGestioneMenu();
    }


}
