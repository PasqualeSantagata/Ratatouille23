package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;

public interface InserisciElementoContract {

    interface View extends BaseView{
        void elementoInseritoCorrettamente();
        void mostraErroreInserimentoElemento(String errore);


    }

    interface Presenter{
        void inserisciElementoMenu(ElementoMenu elementoMenu, String categoria);

    }

}
