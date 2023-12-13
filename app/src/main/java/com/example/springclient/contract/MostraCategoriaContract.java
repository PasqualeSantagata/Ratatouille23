package com.example.springclient.contract;

import com.example.springclient.model.entity.Categoria;

import java.util.List;

//comune a tutte le schermate che debbano mostrare l'elenco delle categorie
public interface MostraCategoriaContract {

    interface ViewContract extends BaseViewContract {
        void mostraVisualizzaElementiDellaCategoria();
        void setCategorie(List<Categoria> categoriaList);
        void mostraImmagineCategoria(int posizione);
        void caricamentoCategorieFallito();
        void apriRiepilogo();
    }

    interface Presenter {
        void tronaHomeModificaElementiMenu();
        void mostraElementiDellaCategoria();
        void getAllCategorie();
        void getFotoCategoriaById(Categoria categoria, int posizione);
        void apriRiepilogo();
        void mostraStartNuovaOrdinazione();
    }
}
