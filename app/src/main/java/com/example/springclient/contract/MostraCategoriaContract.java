package com.example.springclient.contract;

import com.example.springclient.entity.Categoria;

import java.util.List;

import okhttp3.ResponseBody;
//comune a tutte le schermate che debbano mostrare l'elenco delle categorie
public interface MostraCategoriaContract {

    interface View extends BaseView{
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
