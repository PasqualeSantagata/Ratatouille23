package com.example.springclient.contract;

import com.example.springclient.entity.ElementoMenu;
import com.example.springclient.entity.Portata;

import java.util.List;

public interface VisualizzElementiContract {

    interface View extends BaseView{
        void mostraTraduzione(ElementoMenu elementoMenu);
        void traduzioneAssente();
        void setElementi(List<ElementoMenu> elementoMenuList);

        void onItemClickRecyclerViewPortata(int position);
        void onButtonDeleted(int position);

        void rimuoviElemento();
        void mostraRiepilogo();

        void mostraFiltraCategoria(List<ElementoMenu> portataList);

        void mostraModifica(ElementoMenu elementoMenu);
        void impossibileComunicareConServer(String messaggio);

        void impossibileRimuovereElemento(String s);
    }

    interface Presenter {

        void mostraModifica(ElementoMenu elementoMenu);
        void restituisciTraduzione(String idElemento);
        void restituisciLinguaBase(String idElemento);
        void aggiornaElementiCategoria(String nomeCategoria);
        void eliminaElementoDallaCategoria(Long idCategoria, ElementoMenu elementoMenu);
        void mostraRiepilogo();
        void tornaEsploraCategorieMenu();
        void mostrFiltraCategorie(String nomeCategoria);
    }
}
