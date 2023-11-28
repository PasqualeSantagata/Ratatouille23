package com.example.springclient.contract;

import com.example.springclient.entity.Categoria;
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
        void mostraSelezionaNuovaLingua();

    }

    interface ViewDefinisciOrdine extends BaseView{

    }
    interface Presenter {
        void getNomiCategoriaDisponibili(String id);
        void restituisciTraduzioneElemento(String id);
        void aggiungiElementoAllaCategoria(String nomeCategoria, ElementoMenu elementoMenu);
        void modificaElementoMenu(ElementoMenu elementoMenu);
        void modificaOrdineCategoria(Categoria categoria);
        void tornaIndietro();

        void mostraSelezionaNuovaLingua();
        void mostraVisualizzaElementiDellaCategoria();


    }

}
