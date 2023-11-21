package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;

import java.util.List;

public interface VisualizzElementiContract {

    interface View extends BaseView{
        void mostraTraduzione(ElementoMenu elementoMenu);
        void traduzioneAssente();
        void setElementi(List<ElementoMenu> elementoMenuList);
        void rimuoviElemento();

    }

    interface Presenter{

        void getElementiMenu();
        void rimuoviElementoMenu(String idElemento);
        void restituisciTraduzione(String idElemento);
        void restituisciLinguaBase(String idElemento);


    }
}
