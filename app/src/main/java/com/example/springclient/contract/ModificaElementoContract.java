package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;

import java.util.List;

public interface ModificaElementoContract {
    interface View extends BaseView{
        void initializeComponents();
        void setNomiCategoria(List<String> nomiCategoria);
        void disabilitaFabInserisciTraduzione();
        void elementoGiaPresenteNellaCategoria();
        void elementoAggiuntoAdUnaCategoria(String nomeCategoria);
        void elementoModificatoCorrettamente();
        void erroreModifica();
        void mostraErrori(String errore);

    }
    interface ViewAggiungiLingua extends BaseView{
        void linguaAggiunta();

    }
    interface Presenter{
        void getNomiCategoriaDisponibili(String id);
        void restituisciTraduzioneElemento(String id);
        void aggiungiElementoAllaCategoria(String nomeCategoria, ElementoMenu elementoMenu);
        void modificaElementoMenu(ElementoMenu elementoMenu);
        void aggiungiLingua(String nomeElemento, ElementoMenu elementoTradotto);


    }

}
