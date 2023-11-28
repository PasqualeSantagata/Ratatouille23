package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;

import java.util.List;

public interface VisualizzElementiContract {

    interface View extends BaseView{
        void mostraTraduzione(ElementoMenu elementoMenu);
        void traduzioneAssente();
        void setElementi(List<ElementoMenu> elementoMenuList);
        void rimuoviElemento();
        void mostraRiepilogo();
        void mostraModifica(ElementoMenu elementoMenu);

    }

    interface Presenter {

        void mostraModifica(ElementoMenu elementoMenu);
        void restituisciTraduzione(String idElemento);
        void restituisciLinguaBase(String idElemento);
        void aggiornaElementiCategoria(String nomeCategoria);
        void eliminaElementoDallaCategoria(Long idCategoria, ElementoMenu elementoMenu);
        void mostraRiepilogo();
    }
}
