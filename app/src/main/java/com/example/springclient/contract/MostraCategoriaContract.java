package com.example.springclient.contract;

import com.example.springclient.entity.Categoria;

import java.util.List;

import okhttp3.ResponseBody;

public interface MostraCategoriaContract {
    interface Model{
        void saveCategoria(Categoria categoria, CallbackResponse<Categoria> callbackResponse);

        void getAllCategorie(CallbackResponse<List<Categoria>> categoriaCallback);

        void getFotoCategoriaById(String id, CallbackResponse<ResponseBody> categoriaCallback);


    }

    interface View extends BaseView{
        void setCategorie(List<Categoria> categoriaList);
        void mostraImmagineCategoria(int posizione);

    }

    interface Presenter{
        void getAllCategorie();
        //comune a tutte le schermate che debbano mostrare l'elenco delle categorie
        void getFotoCategoriaById(Categoria categoria, int posizione);
    }
}
